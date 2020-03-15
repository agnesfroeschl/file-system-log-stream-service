package ac.at.tuwien.logservice.services;

import ac.at.tuwien.logservice.entities.QueriesConstruct;
import ac.at.tuwien.logservice.services.formatter.*;
import eu.larkc.csparql.core.engine.CsparqlEngineImpl;
import eu.larkc.csparql.core.engine.CsparqlQueryResultProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.text.ParseException;

@Service
public class CSparqlStreamService {

    @Autowired
    private CreateFileAccessEventsFormatter createFileAccessEventsFormatter;
    @Autowired
    private ModifyFileAccessEventsFormatter modifyFileAccessEventsFormatter;
    @Autowired
    private RenameFileAccessEventsFormatter renameFileAccessEventsFormatter;
    @Autowired
    private MoveFileAccessEventsFormatter moveFileAccessEventsFormatter;
    @Autowired
    private MoveToRecycleBinFileAccessEventsFormatter moveToRecycleBinFileAccessEventsFormatter;
    @Autowired
    private CopySameDirAccessEventsFormatter copySameDirAccessEventsFormatter;
    @Autowired
    private CopySameDirAccessEventsFormatter2 copySameDirAccessEventsFormatter2;
    @Autowired
    private CopyDiffDirAccessEventsFormatter copyDiffDirAccessEventsFormatter;
    @Autowired
    private CopyDiffDirAccessEventsFormatter copyDiffDirAccessEventsFormatter2;
    @Autowired
    private LogEntriesFormatter logEntriesFormatter;


    public CSparqlStreamService() {
    }

    public void initCSparqlEngine() throws ParseException, URISyntaxException {
        StreamProcessingService fsStream = new StreamProcessingService("ws://localhost:8124/tw/stream");
        StreamProcessingService psStream = new StreamProcessingService("ws://localhost:8125/tw/stream");

        //Create csparql engine instance
        CsparqlEngineImpl engine = new CsparqlEngineImpl();

        //Initialize the engine instance
        //The initialization creates the static engine (SPARQL) and the stream engine (CEP)
        engine.initialize(true);

        //Register new stream in the engine
        engine.registerStream(fsStream);
        engine.registerStream(psStream);
        //  engine.registerStream(observerStream);

        //Register new query in the engine
        CsparqlQueryResultProxy createdProxy = engine.registerQuery(QueriesConstruct.createdConstruct, false);
        CsparqlQueryResultProxy modifiedProxy = engine.registerQuery(QueriesConstruct.modifiedConstruct, false);
        CsparqlQueryResultProxy renamedProxy = engine.registerQuery(QueriesConstruct.renamedConstruct, false);
        CsparqlQueryResultProxy movedProxy = engine.registerQuery(QueriesConstruct.movedConstruct, false);
        CsparqlQueryResultProxy movedRecycleBinProxy = engine.registerQuery(QueriesConstruct.movedToRecycleBinConstruct, false);
        CsparqlQueryResultProxy copySameDirProxy = engine.registerQuery(QueriesConstruct.copySameDirConstruct, false);
       // CsparqlQueryResultProxy copySameDirFinderProxy = engine.registerQuery(QueriesConstruct.copySameDirFinderConstruct, false);
        CsparqlQueryResultProxy copyDiffDirProxy = engine.registerQuery(QueriesConstruct.copyDiffDirConstruct, false);
    //    CsparqlQueryResultProxy copyDiffDirFinderProxy = engine.registerQuery(QueriesConstruct.copyDiffDirFinderConstruct, false);
        CsparqlQueryResultProxy allEventsProxy = engine.registerQuery(QueriesConstruct.logEntryConstruct, false);

        //Attach a result consumer to the query result proxy to print the results on the console
        createdProxy.addObserver(createFileAccessEventsFormatter);
        modifiedProxy.addObserver(modifyFileAccessEventsFormatter);
        renamedProxy.addObserver(renameFileAccessEventsFormatter);
        movedProxy.addObserver(moveFileAccessEventsFormatter);
        movedRecycleBinProxy.addObserver(moveToRecycleBinFileAccessEventsFormatter);
        copySameDirProxy.addObserver(copySameDirAccessEventsFormatter);
      //  copySameDirFinderProxy.addObserver(copySameDirAccessEventsFormatter2);
       copyDiffDirProxy.addObserver(copyDiffDirAccessEventsFormatter);
      //  copyDiffDirFinderProxy.addObserver(copyDiffDirAccessEventsFormatter2);
        allEventsProxy.addObserver(logEntriesFormatter);

        //Start the thread that put the triples in the engine
        try {
            fsStream.initService();
            psStream.initService();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
