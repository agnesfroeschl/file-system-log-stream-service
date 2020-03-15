package ac.at.tuwien.logservice.tdb;

import ac.at.tuwien.logservice.entities.*;
import ac.at.tuwien.logservice.entities.schema.Background_schema;
import ac.at.tuwien.logservice.entities.schema.File_access_events;
import ac.at.tuwien.logservice.services.util.ServiceUtil;
import com.hp.hpl.jena.datatypes.xsd.XSDDateTime;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.rdf.model.impl.PropertyImpl;
import com.hp.hpl.jena.rdf.model.impl.ResourceImpl;
import com.hp.hpl.jena.tdb.TDBException;
import com.hp.hpl.jena.tdb.TDBFactory;
import eu.larkc.csparql.cep.api.RdfQuadruple;
import eu.larkc.csparql.common.RDFTuple;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.util.DateParseException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class TDBConnection {

    private Dataset ds;

    public TDBConnection(String path) {
        ds = TDBFactory.createDataset(path);
    }

    public TDBConnection() {
    }

    public void getAccessEvents(String query){

    }

    public void saveAccessEventTuple(Tuple tuple) {
        Dataset ds = TDBFactory.createDataset("tdb/DB_FileAccessEvent");
        Model model;
        ds.begin(ReadWrite.WRITE);
        try {
            model = ds.getDefaultModel();
            List<String> fields = tuple.getFields();
            String pS = fields.get(1);
            Property p = new PropertyImpl(fields.get(1));
            Resource r = new ResourceImpl(fields.get(0));
            String o = fields.get(2);
            if (pS.contains("hasSourceHost") ||
                    pS.contains("hasTargetHost") ||
                    pS.contains("hasProgram") ||
                    pS.contains("hasSourceFile") ||
                    pS.contains("hasTargetFile") ||
                    pS.contains("hasUser") ||
                    pS.contains("hasAction")) {
                Resource oResource = new ResourceImpl(o);
                model.add(r, p, oResource);
            } else {
                model.add(r, p, o);
            }

            ds.commit();
        } finally {
            ds.end();
        }
    }

    public void saveLogEntryTuple(RDFTuple tuple) {
        Dataset ds = TDBFactory.createDataset("tdb/DB_LogEntryFormatter");
        Model model;
        ds.begin(ReadWrite.WRITE);
        try {
            model = ds.getDefaultModel();
            String pS = tuple.get(1);
            Property p = new PropertyImpl(tuple.get(1));
            Resource r = new ResourceImpl(tuple.get(0));
            String o = tuple.get(2);
            if (pS.contains("hasFile") ||
                    pS.contains("hasProcess") ||
                    pS.contains("hasUser") ||
                    pS.contains("originatesFrom")) {
                Resource oResource = new ResourceImpl(o);
                model.add(r, p, oResource);
            } else {
                model.add(r, p, o);
            }

            ds.commit();
        } finally {
            ds.end();
        }
    }

    public void saveLogEntryTuple(RdfQuadruple tuple) {
        Dataset ds = TDBFactory.createDataset("tdb/DB_LogEntry");
        Model model;
        ds.begin(ReadWrite.WRITE);
        try {
            model = ds.getDefaultModel();
            String pS = tuple.getPredicate();
            Property p = new PropertyImpl(tuple.getPredicate());
            Resource r = new ResourceImpl(tuple.getSubject());
            String o = tuple.getObject();
            if (pS.contains("hasFile") ||
                    pS.contains("hasProcess") ||
                    pS.contains("hasUser") ||
                    pS.contains("originatesFrom")) {
                Resource oResource = new ResourceImpl(o);
                model.add(r, p, oResource);
            } else {
                model.add(r, p, o);
            }

            ds.commit();
        } finally {
            ds.end();
        }
    }

    public void saveRDFTupleProcessInfo(RDFTuple t) {
        Dataset ds = TDBFactory.createDataset("tdb/DB_ProcessInfo");
        Model model;
        ds.begin(ReadWrite.WRITE);
        try {
            model = ds.getDefaultModel();

       //    List<String> fields = tuple.getFields();
            Resource r = new ResourceImpl(t.get(0));
            Property p = new PropertyImpl(t.get(1));
            String o = t .get(2);

            model.add(r, p, o);

            ds.commit();
        } finally {
            ds.end();
        }
    }


    public String getProcessNameByPid(String processID, String timestamp) {
        Dataset ds = TDBFactory.createDataset("tdb/DB_ProcessInfo");
        String processName = null;
        Model model;
        ds.begin(ReadWrite.READ);
        try {
            model = ds.getDefaultModel();
            String sparqlQueryString = "PREFIX process: " +
                    "<http://w3id.org/sepses" +
                    "/vocab/processInfo#> " +
                    "SELECT * WHERE { " +
                    "?s process:pid ?pid . " +
                    "FILTER( str(?pid) = \"" + processID + "\") . " +
                    "?s process:operation ?operation . " +
                    "FILTER( str(?operation) = \"start\") . " +
                    "?s process:processName ?name . " +
                    "?s process:timestamp ?timestamp . " +
                    "FILTER ( ?timestamp <= \"" + timestamp + "\"^^<http://www.w3.org/2001/XMLSchema#dateTime> ) . " +
                    "} ORDER BY DESC(?timestamp) Limit 1";
            Query query = QueryFactory.create(sparqlQueryString);
            QueryExecution qexec = QueryExecutionFactory.create(query, model);
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                processName = results.next().getLiteral("?name").getString();
            }
            qexec.close();
        } finally {
            ds.end();
        }
        return processName;
    }

    public String getProcessNameByPidAndHost(String processID, String timestamp, String hostname) {
        Dataset ds = TDBFactory.createDataset("tdb/DB_ProcessInfo");
        String processName = null;
        Model model;
        ds.begin(ReadWrite.READ);
        try {
            model = ds.getDefaultModel();
            String sparqlQueryString = "PREFIX process: " +
                    "<http://w3id.org/sepses" +
                    "/vocab/processInfo#> " +
                    "SELECT * WHERE { " +
                    "?s process:pid ?pid . " +
                    "FILTER( str(?pid) = \"" + processID + "\") . " +
                    "?s process:hostName ?hostName . " +
                    "FILTER( str(?hostName) = \"" + hostname + "\") . " +
                    "?s process:operation ?operation . " +
                    "FILTER( str(?operation) = \"start\") . " +
                    "?s process:processName ?name . " +
                    "?s process:timestamp ?timestamp . " +
                    "FILTER ( ?timestamp <= \"" + timestamp + "\"^^<http://www.w3.org/2001/XMLSchema#dateTime> ) . " +
                    "} ORDER BY DESC(?timestamp) Limit 1";
            Query query = QueryFactory.create(sparqlQueryString);
            QueryExecution qexec = QueryExecutionFactory.create(query, model);
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                processName = results.next().getLiteral("?name").getString();
            }
            qexec.close();
        } finally {
            ds.end();
        }
        return processName;
    }


    private void addTimestampToResource(Model model, Resource resource, String timestamp, DatatypeProperty property) {
        try {
            XSDDateTime literal = ServiceUtil.parseStringToXSDDateTime(timestamp, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            resource.addProperty(property, model.createTypedLiteral(literal));
        } catch (DateParseException e) {
            throw new TDBException("Cannot create Resource!");
        }
    }


    public void createChannelResource(Channel c) {
        Dataset ds = TDBFactory.createDataset("tdb/DB_Background");
        ds.begin(ReadWrite.WRITE);
        try {
            Model model = ds.getDefaultModel();
            Resource personResource = model.createResource(Background_schema.getURI() + c.getId(), Background_schema.Channel);
            personResource.addProperty(Background_schema.name, c.getName());
            personResource.addProperty(Background_schema.type, c.getType());
            personResource.addProperty(Background_schema.path, c.getPath());
            personResource.addProperty(Background_schema.program, c.getProgram() != null ? c.getProgram() : "");
            ds.commit();
        } finally {
            ds.end();
        }
    }

    public void createAccountResource(Account a) {
        Dataset ds = TDBFactory.createDataset("tdb/DB_Background");
        ds.begin(ReadWrite.WRITE);
        try {
            Model model = ds.getDefaultModel();
            Resource personResource = model.createResource(Background_schema.getURI() + a.getUid(), Background_schema.Account);
            personResource.addProperty(Background_schema.uid, a.getUid());
            personResource.addProperty(Background_schema.userName, a.getUsername());
            personResource.addProperty(Background_schema.firstName, a.getFirstname());
            personResource.addProperty(Background_schema.lastName, a.getLastname());
            personResource.addProperty(Background_schema.email, a.getEmail());
            ds.commit();
        } finally {
            ds.end();
        }
    }

    public void createCheckSumPropertyOfFileResource(String resourceURI, String checkSumOfFile) {
        Dataset ds = TDBFactory.createDataset("tdb/DB_FileAccessEvent");
        Model model;
        ds.begin(ReadWrite.WRITE);
        try {
            model = ds.getDefaultModel();
            Resource fae = model.getResource(resourceURI);
            fae.addProperty(File_access_events.md5CheckSum, checkSumOfFile);
            ds.commit();
        } finally {
            ds.end();
        }
    }

    public void createRelatedToProperty(String previousEventURI, String currentEventURI) {
        Dataset ds = TDBFactory.createDataset("tdb/DB_FileAccessEvent");
        ds.begin(ReadWrite.WRITE);
        try {
            Model model = ds.getDefaultModel();
            Resource prevFae = model.getResource(previousEventURI);
            Resource currFae = model.getResource(currentEventURI);
            prevFae.addProperty(File_access_events.relatedTo, currFae);
            ds.commit();
        } finally {
            ds.end();
        }
    }

    public boolean updateProgramNamOfFileAccessEventResource(String resourceURI, String programName) {
        Dataset ds = TDBFactory.createDataset("tdb/DB_FileAccessEvent");
        Model model;
        ds.begin(ReadWrite.WRITE);
        try {
            model = ds.getDefaultModel();
            Resource fae = model.getResource(resourceURI);
            Statement faeStatement = fae.getProperty(File_access_events.programName);
            if (faeStatement != null) {
                RDFNode program = faeStatement.getObject();
                if (program != null && program.isLiteral()) {// removes programName property and adds new property
                    fae.removeAll(File_access_events.programName);
                    fae.addProperty(File_access_events.programName, programName);
                }
            }
            ds.commit();
        } finally {
            ds.end();
        }
        return true;
    }

    public List<String> getFileNames() {
        String queryString = "PREFIX fae: <http://w3id.org/sepses/vocab/event/fileAccess#> " +
                "SELECT DISTINCT ?pathname WHERE { " +
                "?s fae:timestamp ?timestamp . " +
                "?s fae:hasSourceFile/fae:fileName ?fileName  . " +
                "FILTER REGEX (?fileName , \"[a-zA-Z0-9_ :~$]+[.][a-zA-Z0-9]{2,5}\") ." +
                "?s fae:hasSourceFile/fae:pathName ?pathname . " +
                "}";
        return this.queryNames(queryString);
    }

    public List<String> getFileNames(String range) {
        String queryString = "PREFIX fae: <http://w3id.org/sepses/vocab/event/fileAccess#> " +
                "SELECT DISTINCT ?pathname WHERE { " +
                "?s fae:timestamp ?timestamp . " +
                "FILTER (?timestamp > \"" + range + "\"^^<http://www.w3.org/2001/XMLSchema#dateTime>) . " +
                "?s fae:hasSourceFile/fae:fileName ?fileName . " +
                "FILTER REGEX (?fileName , \"[a-zA-Z0-9_ :~$]+[.][a-zA-Z0-9]{2,5}\") ." +
                "?s fae:hasSourceFile/fae:pathName ?pathname . " +
                "}";
        return this.queryNames(queryString);
    }

    private List<String> queryNames(String queryString) {
        List<String> names = new ArrayList<>();
        Model model;
        ds.begin(ReadWrite.READ);
        try {
            model = ds.getDefaultModel();
            Query query = QueryFactory.create(queryString);
            QueryExecution qexec = QueryExecutionFactory.create(query, model);
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution binding = results.nextSolution();
                names.add(binding.get("pathname").toString());
            }
            qexec.close();
        } finally {
            ds.end();
        }
        return names;
    }

    public void saveRelatedTo(String query) {
        Dataset ds = TDBFactory.createDataset("tdb/DB_FileAccessEvent");
        ds.begin(ReadWrite.WRITE);
        try {
            Model model = ds.getDefaultModel();
            QueryExecution qExec = QueryExecutionFactory.create(query, model);
            Iterator<Triple> triples = qExec.execConstructTriples();
            while (triples.hasNext()) {
                Triple t = triples.next();
                //System.out.println(t);
                Node subject = t.getSubject();
                Property p = new PropertyImpl(t.getPredicate().getURI());
                Node object = t.getObject();

                model.createResource(subject.getURI()).addProperty(p, object.getURI());
            }

            ds.commit();
        } finally {
            ds.end();
        }

    }

    public List<FileAccessEvent> execQueryAndGetFileAccessEvents(String sparqlQueryString) {
        List<FileAccessEvent> faeList = new ArrayList<>();
        Model model;
        ds.begin(ReadWrite.READ);
        try {
            model = ds.getDefaultModel();
            Query query = QueryFactory.create(sparqlQueryString);
            QueryExecution qexec = QueryExecutionFactory.create(query, model);
            ResultSet results = qexec.execSelect();

            while (results.hasNext()) {
                QuerySolution binding = results.nextSolution();
                FileAccessEvent event = new FileAccessEvent(
                        binding.get("id").toString(),
                        binding.get("timestamp").toString(), null, //eventID is null
                        new Action(binding.get("actionName").toString()),
                        new Host(binding.get("hostnameSource").toString()),
                        new Host(binding.get("hostnameTarget").toString()),
                        new File(binding.get("fileNameSource").toString(), binding.get("pathNameSource").toString()),
                        new File(binding.get("fileNameTarget").toString(), binding.get("pathnameTarget").toString()),
                        new User(binding.get("username").toString()),
                        new Program(("programName"), binding.get("pid").toString())
                        // new Program(binding.get("programName").toString(), binding.get("pid").toString())
                        //TODO add program name
                );
                if (!faeList.contains(event)) {
                    ServiceUtil.setDateTimeOfEvent(event);
                    faeList.add(event);
                }
            }
            qexec.close();
        } finally {
            ds.end();
        }
        return faeList;
    }

    /*?id ?timestamp ?detected ?action ?actionName ?sourceFile ?fileNameSource ?pathNameSource " +
            " ?targetFile ?fileNameTarget ?pathNameTarget ?user ?username ?sourceHost ?sourceHostName " +
            " ?targetHost ?targetHostName ?program ?pid */

    public List<FileAccessEvent> getFileAccessEventsByQuery(String sparqlQueryString) {
        List<FileAccessEvent> faeList = new ArrayList<>();
        Model model;
        ds.begin(ReadWrite.READ);
        try {
            model = ds.getDefaultModel();
            Query query = QueryFactory.create(sparqlQueryString);
            QueryExecution qexec = QueryExecutionFactory.create(query, model);
            ResultSet results = qexec.execSelect();

            while (results.hasNext()) {
                QuerySolution binding = results.nextSolution();
                FileAccessEvent event = new FileAccessEvent(
                        binding.get("id").toString(),
                        binding.get("timestamp").toString(),
                        binding.get("detected").toString(), null, //eventID is null
                        new Action(binding.get("actionName").toString()),
                        new Host(binding.get("sourceHostName").toString()),
                        new Host(binding.get("targetHostName").toString()),
                        new File(binding.get("fileNameSource").toString(), binding.get("pathNameSource").toString()),
                        new File(binding.get("fileNameTarget").toString(), binding.get("pathNameTarget").toString()),
                        new User(binding.get("username").toString()),
                        new Program(binding.get("pid").toString())
                );
                if (!faeList.contains(event)) {
                   // ServiceUtil.setDateTimeOfEvent(event);
                    faeList.add(event);
                }
            }
            qexec.close();
        } finally {
            ds.end();
        }
        return faeList;
    }



    public void execQueryAndPrint(String sparqlQueryString) {
        print(sparqlQueryString, ds);
    }

    public void execQueryAndPrint(String sparqlQueryString, String tdbPath) {
        Dataset ds = TDBFactory.createDataset(tdbPath);
        print(sparqlQueryString, ds);
    }

    private void print(String sparqlQueryString, Dataset ds) {
        Model model;
        ds.begin(ReadWrite.READ);
        try {
            model = ds.getDefaultModel();
            Query query = QueryFactory.create(sparqlQueryString);
            QueryExecution qexec = QueryExecutionFactory.create(query, model);
            ResultSet results = qexec.execSelect();
            ResultSetFormatter.out(results);
            qexec.close();
        } finally {
            ds.end();
        }
    }

}
