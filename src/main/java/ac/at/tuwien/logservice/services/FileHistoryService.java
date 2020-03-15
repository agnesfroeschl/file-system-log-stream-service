package ac.at.tuwien.logservice.services;

import ac.at.tuwien.logservice.controller.transfer.Element;
import ac.at.tuwien.logservice.controller.transfer.Node;
import ac.at.tuwien.logservice.entities.FileAccessEvent;
import ac.at.tuwien.logservice.entities.Queries;
import ac.at.tuwien.logservice.entities.schema.File_access_events;
import ac.at.tuwien.logservice.services.util.ServiceUtil;
import ac.at.tuwien.logservice.tdb.TDBConnection;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class FileHistoryService {

    private static String time;
    private static TDBConnection tdb;
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    @PostConstruct
    public void init() {
        tdb = new TDBConnection();
        time = ServiceUtil.getCurrentTimeStamp();
    }

    @Scheduled(fixedDelay = 60000)
    public void processCache() {
        System.out.println("################################### SCHEDULED TASK - " + new Date(System.currentTimeMillis()) + " #########################################");

        String tempTime = time;
        try {
            Date tempDate = SIMPLE_DATE_FORMAT.parse(tempTime);
            Date tempCopyTime = new Date(tempDate.getTime() - 5 * 60 * 1000); //substract 2 min
            String tempCopyDateString = SIMPLE_DATE_FORMAT.format(tempCopyTime);
        } catch (ParseException e) {
            log.error(e.getMessage());
        }

        System.out.println("Construct relatedTo property");
        tdb.saveRelatedTo(Queries.constructRelatedTo());

        //TODO use this method in live streaming examples to get only filenames from last time range, since last scheduling task
        //List<String> pathNames = this.getAllFilenamesOfFileAccessEvents(tempTime); // get pathnames in last timestamp range since last scheduling task
        List<String> pathNames = this.getAllFilenamesOfFileAccessEvents();
        for (String name : pathNames) {
            if (name.contains(".tmp")) continue; //skip temp files
            System.out.println("--------- Result of file with name: \"" + name + "\""); ///Users/Agnes/Desktop/test/excel.xlsx
            tdb.execQueryAndPrint(Queries.getRelatedEvents(name), "tdb/DB_FileAccessEvent");
        }

        // set time again
        Date now = new Date();
        // Date temp = new Date(now.getTime() - 2 * 60 * 60 * 1000); //substract 2 h
        //Date temp = new Date(now.getTime() - 5 * 60 * 1000); //substract 2 min
        time = SIMPLE_DATE_FORMAT.format(now);
    }


    /**
     * Creates JSON file which is used to visualise a files history.
     * The JSON contains all nodes from the history graph containing the given pathname.
     * The graph contains all related events, which can be past and "future" events.
     *
     * @param pathname for which a history graph should be created
     * @return JSON containing nodes of a file history of given pathname
     */
    public String getHistoryJson(String pathname) {
        Set<String> excludedPathnameSet = new HashSet<>();
        Set<String> pathnameSet = new HashSet<>();
        pathnameSet.add(pathname);
        List<FileAccessEvent> events = new ArrayList<>();
        events.addAll(getEvents(excludedPathnameSet, pathnameSet, events));

        List<Node> nodes = new ArrayList<>();
        for (FileAccessEvent eV : events) {
            /* -- updates event by adding program name -- */
            String resourceURI = File_access_events.getURI() + eV.getId() + "-Program";
            String processName = tdb.getProcessNameByPidAndHost(eV.getHasProgram().getPid(),
                    ServiceUtil.getTimestampFromXSDDate(eV.getTimestamp()), eV.getHasSourceHost().getHostname());
            tdb.updateProgramNamOfFileAccessEventResource(resourceURI, processName);
            if (eV.getHasProgram().getProgramName() == null || (eV.getHasProgram().getProgramName() != null && !eV.getHasProgram().getProgramName().isEmpty())) {
                eV.getHasProgram().setProgramName(processName);
            }
            /* ---- */

            nodes.add(this.createNode(ServiceUtil.getTimestampFromXSDDate(eV.getTimestamp()),
                    eV.getHasSourceFile().getPathname(),
                    eV.getHasAction().getActionName(),
                    eV.getHasTargetFile().getPathname(),
                    eV.getHasProgram().getProgramName(),
                    eV.getHasUser().getUsername(),
                    eV.getHasSourceHost().getHostname(),
                    eV.getHasTargetHost().getHostname()
            ));
        }
        Collections.sort(nodes);
        Gson gson = new Gson();
        String json = gson.toJson(nodes);
        System.out.println(json);
        return json;
    }

    /**
     * Function to receive all related events of a given set of pathnames.
     *
     * @param excludedPathnameSet set of pathnames which should not be considered
     * @param pathnameSet         set of pathnames for which related events should be found
     * @param events              list of related events of given pathnames
     * @return list of related file access events
     */
    private List<FileAccessEvent> getEvents(Set<String> excludedPathnameSet, Set<String> pathnameSet, List<FileAccessEvent> events) {
        Iterator<String> itr = pathnameSet.iterator(); // traversing over HashSet System.out.println("Traversing over Set using Iterator"); while(itr.hasNext()){ System.out.println(itr.next()); }

        while (itr.hasNext()) {
            String path = itr.next();
            excludedPathnameSet.add(path);
            pathnameSet.remove(path);
            List<FileAccessEvent> events2 = this.extractFileAccessEventsFromQueryResult(Queries.getRelatedEvents(path));
            for (FileAccessEvent e : events2) {
                if (!excludedPathnameSet.contains(e.getHasSourceFile().getPathname()))
                    pathnameSet.add(e.getHasSourceFile().getPathname());
                if (!excludedPathnameSet.contains(e.getHasTargetFile().getPathname()))
                    pathnameSet.add(e.getHasTargetFile().getPathname());
            }
            events.addAll(events2);

            if (pathnameSet.size() == 0) {
                return events;
            } else {
                this.getEvents(excludedPathnameSet, pathnameSet, events);
            }
            return events;
        }
        return events;
    }

    private Node createNode(String t, String f, String fa, String tf, String p, String u, String sh, String th) {
        Element time = new Element("literal", t);
        Element filename = new Element("literal", f);
        Element filAccess = new Element("literal", fa);
        Element tfilename = new Element("literal", tf);
        Element program = new Element("literal", p);
        Element user = new Element("literal", u);
        Element sourceHost = new Element("literal", sh);
        Element targetHost = new Element("literal", th);
        Node node = new Node(time, filename, filAccess, tfilename, program, user, sourceHost, targetHost);
        return node;
    }


    public List<String> getAllFilenamesOfFileAccessEvents() {
        tdb = new TDBConnection("tdb/DB_FileAccessEvent");
        return tdb.getFileNames();
    }

    public List<String> getAllFilenamesOfFileAccessEvents(String timestamp) {
        tdb = new TDBConnection("tdb/DB_FileAccessEvent");
        return tdb.getFileNames(timestamp);
    }

    public List<FileAccessEvent> extractFileAccessEventsFromQueryResult(String sparqlQueryString) {
        tdb = new TDBConnection("tdb/DB_FileAccessEvent");
        return tdb.execQueryAndGetFileAccessEvents(sparqlQueryString);
    }

}
