package ac.at.tuwien.logservice.services;


import ac.at.tuwien.logservice.entities.Tuple;
import ac.at.tuwien.logservice.tdb.TDBConnection;
import eu.larkc.csparql.common.RDFTuple;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class LogConverterService {

    private static TDBConnection tdb;

    @PostConstruct
    public void init() {
        tdb = new TDBConnection();
    }

    public void printRecords(String sparqlQueryString, String tdbPath) {
        tdb = new TDBConnection(tdbPath);
        tdb.execQueryAndPrint(sparqlQueryString);
    }

    public void saveRDFTuple(Tuple t){
        tdb.saveAccessEventTuple(t);
    }

    public void saveLogEntryRDFTuple(RDFTuple t){
        tdb.saveLogEntryTuple(t);
    }

}
