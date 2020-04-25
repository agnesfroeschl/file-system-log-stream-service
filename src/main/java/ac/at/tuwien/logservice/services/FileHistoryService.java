package ac.at.tuwien.logservice.services;

import ac.at.tuwien.logservice.controller.transfer.Element;
import ac.at.tuwien.logservice.controller.transfer.Node;
import ac.at.tuwien.logservice.entities.FileAccessEvent;
import ac.at.tuwien.logservice.entities.Queries;
import ac.at.tuwien.logservice.services.util.ServiceUtil;
import ac.at.tuwien.logservice.tdb.TDBConnection;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
@Slf4j
public class FileHistoryService {

    private static TDBConnection tdb;

    @PostConstruct
    public void init() {
        tdb = new TDBConnection();
    }

    @Scheduled(fixedDelay = 60000)
    public void processCache() {
        tdb.saveRelatedTo(Queries.constructRelatedTo());
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

            nodes.add(this.createNode(ServiceUtil.getTimestampFromXSDDate(eV.getTimestamp()),
                    eV.getHasSourceFile().getPathname(),
                    ServiceUtil.getActionNameFromXSDString(eV.getHasAction().getActionName()),
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


    public List<FileAccessEvent> extractFileAccessEventsFromQueryResult(String sparqlQueryString) {
        tdb = new TDBConnection("tdb/DB_FileAccessEvent");
        return tdb.execQueryAndGetFileAccessEvents(sparqlQueryString);
    }

}
