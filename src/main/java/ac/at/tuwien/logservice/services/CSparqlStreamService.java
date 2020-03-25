package ac.at.tuwien.logservice.services;

import ac.at.tuwien.logparser.entities.QueriesConstruct;
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
    private CopyDiffDirAccessEventsFormatter copyDiffDirAccessEventsFormatter;


    public void initCSparqlEngine() throws ParseException {
        StreamProcessingService fsStream = new StreamProcessingService("ws://localhost:8124/tw/stream");

        //Create csparql engine instance
        CsparqlEngineImpl engine = new CsparqlEngineImpl();

        //Initialize the engine instance
        //The initialization creates the static engine (SPARQL) and the stream engine (CEP)
        engine.initialize(true);

        //Register new stream in the engine
        engine.registerStream(fsStream);
        //  engine.registerStream(observerStream);

        //Register new query in the engine
        CsparqlQueryResultProxy createdProxy = engine.registerQuery(QueriesConstruct.createdConstruct, false);
        CsparqlQueryResultProxy modifiedProxy = engine.registerQuery(QueriesConstruct.modifiedConstruct, false);
        CsparqlQueryResultProxy renamedProxy = engine.registerQuery(QueriesConstruct.renamedConstruct, false);
        CsparqlQueryResultProxy movedProxy = engine.registerQuery(QueriesConstruct.movedConstruct, false);
        CsparqlQueryResultProxy movedRecycleBinProxy = engine.registerQuery(QueriesConstruct.movedToRecycleBinConstruct, false);
        CsparqlQueryResultProxy copySameDirProxy = engine.registerQuery(QueriesConstruct.copySameDirConstruct, false);
        CsparqlQueryResultProxy copyDiffDirProxy = engine.registerQuery(QueriesConstruct.copyDiffDirConstruct, false);
        CsparqlQueryResultProxy allEventsProxy = engine.registerQuery(QueriesConstruct.logEntryConstruct, false);

        //Attach a result consumer to the query result proxy to print the results on the console
        createdProxy.addObserver(createFileAccessEventsFormatter);
        modifiedProxy.addObserver(modifyFileAccessEventsFormatter);
        renamedProxy.addObserver(renameFileAccessEventsFormatter);
        movedProxy.addObserver(moveFileAccessEventsFormatter);
        movedRecycleBinProxy.addObserver(moveToRecycleBinFileAccessEventsFormatter);
        copySameDirProxy.addObserver(copySameDirAccessEventsFormatter);
       copyDiffDirProxy.addObserver(copyDiffDirAccessEventsFormatter);

        //Start the thread that put the triples in the engine
        try {
            fsStream.initService();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
