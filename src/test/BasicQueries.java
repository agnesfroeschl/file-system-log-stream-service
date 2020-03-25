import ac.at.tuwien.logservice.services.LogConverterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@ComponentScan(basePackages = {"at.ac.tuwien.logparser.service"})
@ContextConfiguration(classes = {LogConverterService.class})
public class BasicQueries {


    @Autowired
    private LogConverterService logConverterService;

    @Test
    public void query_test_get_all_log_entries() {
        String query = "SELECT ?s ?p ?o WHERE{ ?s ?p ?o }";
        logConverterService.printRecords(query, "tdb/DB_LogEntry");
    }

    @Test
    public void query_test_count_raw_logs() {
        String query = "SELECT (COUNT(*) as ?Triples)  WHERE{ ?s ?p ?o }";
        logConverterService.printRecords(query, "tdb/DB_LogEntry");
    }

    @Test
    public void query_test_get_all_file_access_events() {
        String query = "SELECT ?s ?p ?o WHERE{ ?s ?p ?o } ";
        logConverterService.printRecords(query, "tdb/DB_FileAccessEvent");
    }

    @Test
    public void query_test_get_all_file_access_events2() {

        String query = "" +
                "PREFIX fae: <http://w3id.org/sepses/vocab/event/fileAccess#> " +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
                "SELECT ?id ?timestamp ?detected ?periodUntilDetected ?action ?actionName ?sourceFile ?fileNameSource ?pathNameSource " +
                " ?targetFile ?fileNameTarget ?pathNameTarget ?user ?username ?sourceHost ?sourceHostName " +
                " ?targetHost ?targetHostName ?program ?pid " +

                " WHERE{ " +
                "   ?event      fae:id              ?id . " +
                "   ?event      fae:timestamp       ?timestamp . " +
                "   ?event      fae:detected        ?detected . " +
                "   ?event      fae:periodUntilDetected   ?periodUntilDetected . " +
                "   ?event      fae:hasAction       ?action . " +
                "   ?action     fae:actionName      ?actionName . " +
                   "   ?action     fae:actionName    \"\\\"Moved\\\"^^http://www.w3.org/2001/XMLSchema#string\" . " +
                "   ?event      fae:hasSourceFile   ?sourceFile . " +
                "   ?sourceFile fae:fileName        ?fileNameSource . " +
                "   ?sourceFile fae:pathName        ?pathNameSource . " +
                "   ?event      fae:hasTargetFile   ?targetFile . " +
                "   ?targetFile fae:fileName        ?fileNameTarget . " +
                "   ?targetFile fae:pathName        ?pathNameTarget . " +
                "   ?event      fae:hasUser         ?user . " +
                "   ?user       fae:userName        ?username . " +
                "   ?event      fae:hasSourceHost   ?sourceHost . " +
                "   ?sourceHost fae:hostName        ?sourceHostName . " +
                "   ?event      fae:hasTargetHost   ?targetHost . " +
                "   ?targetHost fae:hostName        ?targetHostName . " +
                "   ?event      fae:hasProgram      ?program . " +
                "   ?program    fae:pid             ?pid . " +

                "} GROUP BY ?id ?timestamp ?detected ?periodUntilDetected ?action ?actionName ?sourceFile ?fileNameSource ?pathNameSource " +
                " ?targetFile ?fileNameTarget ?pathNameTarget ?user ?username ?sourceHost ?sourceHostName ?targetHost " +
                " ?targetHostName ?program ?pid " +
                "order by (?timestamp) ";

        logConverterService.printRecords(query, "tdb/move/60/DB_FileAccessEvent");
    }
}
