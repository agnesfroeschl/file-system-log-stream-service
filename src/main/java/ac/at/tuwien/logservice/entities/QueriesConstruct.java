package ac.at.tuwien.logparser.entities;


public final class QueriesConstruct {

    public static final String logEntryConstruct = "REGISTER STREAM logEntry AS " +
            "PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  "+
            "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
            "PREFIX file: <http://w3id.org/sepses/vocab/fileSystemLog#> " +
            "PREFIX fae: <http://w3id.org/sepses/vocab/event/fileAccess#> " +
            "CONSTRUCT { " +
            "   ?logEntry file:id              ?id . " +
            "   ?logEntry file:accessCall      ?accessCall . " +
            "   ?logEntry file:hasFile         ?file . " +
            "   ?file     file:pathnameSource  ?pathnameSource .  " +
            "   ?file     file:pathnameTarget  ?pathnameTarget .  " +
            "   ?file     file:fileSource      ?fileSource .  " +
            "   ?file     file:fileTarget      ?fileTarget .  " +
            "   ?file     file:dirSource       ?dirSource .  " +
            "   ?file     file:dirTarget       ?dirTarget .  " +
            "   ?file     file:fileType        ?fileType .  " +
            "   ?logEntry file:hasProcess      ?process ." +
            "   ?process  file:processID       ?pid . " +
            "   ?logEntry file:hasUser         ?user . " +
            "   ?user     file:username        ?username . " +
            "   ?logEntry file:originatesFrom  ?host . " +
            "   ?host     file:hostName        ?hostName . " +
            "   ?host     file:ipAddress       ?ipAddress . " +
            "   ?logEntry file:timestamp       ?timestamp . " +
            "   ?logEntry file:logMessage      ?logMessage ." +
            "} " +
            "FROM STREAM <ws://localhost:8124/tw/stream> [RANGE 10s STEP 1s] " +
            "WHERE { " +
            " SELECT ?id ?accessCall ?file ?pathnameSource ?pathnameTarget ?fileSource ?fileTarget" +
            " ?dirSource ?dirTarget ?fileType ?process ?pid ?user ?username ?host ?hostName " +
            " ?ipAddress ?timestamp ?logMessage { " +
            "   ?logEntry file:id              ?id . " +
            "   ?logEntry file:accessCall      ?accessCall . " +
            "   ?logEntry file:hasFile         ?file . " +
            "   ?file     file:pathnameSource  ?pathnameSource .  " +
            "   ?file     file:pathnameTarget  ?pathnameTarget .  " +
            "   ?file     file:fileSource      ?fileSource .  " +
            "   ?file     file:fileTarget      ?fileTarget .  " +
            "   ?file     file:dirSource       ?dirSource .  " +
            "   ?file     file:dirTarget       ?dirTarget .  " +
            "   ?file     file:fileType        ?fileType .  " +
            "   ?logEntry file:hasProcess      ?process ." +
            "   ?process  file:processID       ?pid . " +
            "   ?logEntry file:hasUser         ?user . " +
            "   ?user     file:username        ?username . " +
            "   ?logEntry file:originatesFrom  ?host . " +
            "   ?host     file:hostName        ?hostName . " +
            "   ?host     file:ipAddress       ?ipAddress . " +
            "   ?logEntry file:timestamp       ?timestamp . " +
            "   ?logEntry file:logMessage      ?logMessage . " +
            " } GROUP BY  ?id ?accessCall ?file ?pathnameSource ?pathnameTarget ?fileSource ?fileTarget" +
            "               ?dirSource ?dirTarget ?fileType ?process ?pid ?user ?username ?host ?hostName " +
            "               ?ipAddress ?timestamp ?logMessage " +
            "}";

    public static final String createdConstruct = "REGISTER STREAM created2 AS " +
            "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
            "PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  "+
            "PREFIX file: <http://w3id.org/sepses/vocab/fileSystemLog#> " +
            "PREFIX fae: <http://w3id.org/sepses/vocab/event/fileAccess#> " +
            "CONSTRUCT { " +
            "   ?iri             rdf:type          fae:FileAccessEvent; " +
            "                    fae:id            ?id; " +
            "                    fae:timestamp     ?timestamp2; " +
            "                    fae:hasAction     ?action;" +
            "                    fae:hasUser       ?userEvent;" +
            "                    fae:hasProgram    ?program;" +
            "                    fae:hasSourceFile ?sourceFile;" +
            "                    fae:hasTargetFile ?targetFile;" +
            "                    fae:hasSourceHost ?sourceHost;" +
            "                    fae:hasTargetHost ?targetHost." +
            "  ?action     a fae:CreatedAction;" +
            "                fae:actionName ?actionName." +
            "  ?userEvent  a fae:User;" +
            "                fae:userName   ?username. " +
            "  ?program    a fae:Program;" +
            "                fae:pid      ?pid.  " +
            "  ?sourceFile a fae:File; " +
            "                fae:pathName   ?pathnameSource; " +
            "                fae:fileName ?fileSource; " +
            "                fae:directory ?dirSource. "+
            "  ?targetFile a fae:File; " +
            "                fae:pathName   ?pathnameSource; " +
            "                fae:fileName ?fileSource; " +
            "                fae:directory ?dirSource. "+
            "  ?sourceHost a fae:Host; " +
            "                fae:hostName   ?hostName; " +
            "                fae:hostName   ?ipAddress . " +
            "  ?targetHost a fae:Host; " +
            "                fae:hostName   ?hostName; " +
            "                fae:hostName   ?ipAddress . " +
            "} " +
            "FROM STREAM <ws://localhost:8124/tw/stream> [RANGE 5s STEP 1s] " +
            "WHERE { " +
            " SELECT ?iri ?id ?accessCall ?file ?pathnameSource ?pathnameTarget ?fileSource ?fileTarget " +
            " ?dirSource ?dirTarget ?fileType ?process ?pid ?user ?username ?host ?hostName " +
            " ?ipAddress ?timestamp ?logMessage ?actionName ?timestamp2" +
            " ?action ?userEvent ?program ?sourceFile ?targetFile ?sourceHost ?targetHost { "+

            "   ?logEntry file:id              ?id . " +
            "   ?logEntry file:accessCall      ?accessCall . " +
            "   ?logEntry file:hasFile         ?file . " +
            "   ?file     file:pathnameSource  ?pathnameSource .  " +
            "   ?file     file:pathnameTarget  ?pathnameTarget .  " +
            "   ?file     file:fileSource      ?fileSource .  " +
            "   ?file     file:fileTarget      ?fileTarget .  " +
            "   ?file     file:dirSource       ?dirSource .  " +
            "   ?file     file:dirTarget       ?dirTarget .  " +
            "   ?file     file:fileType        ?fileType .  " +
            "   ?logEntry file:hasProcess      ?process ." +
            "   ?process  file:processID       ?pid . " +
            "   ?logEntry file:hasUser         ?user . " +
            "   ?user     file:username        ?username . " +
            "   ?logEntry file:originatesFrom  ?host . " +
            "   ?host     file:hostName        ?hostName . " +
            "   ?host     file:ipAddress       ?ipAddress . " +
            "   ?logEntry file:timestamp       ?timestamp . " +
            "   ?logEntry file:logMessage      ?logMessage ." +

            "   BIND(STRDT(\"Created\", xsd:string) AS ?actionName) . " +
            "   FILTER ( str(?accessCall) = \"open(2) - read,creat\" || str(?accessCall) = \"open(2) - write,creat\" ) . " +
            "   BIND(STRDT(STR(?timestamp), xsd:dateTime) AS ?timestamp2) . " +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id))) AS ?iri )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-Action\")) AS ?action )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-User\")) AS ?userEvent )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-Program\")) AS ?program )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-SourceFile\")) AS ?sourceFile )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-TargetFile\")) AS ?targetFile )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-SourceHost\")) AS ?sourceHost )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-TargetHost\")) AS ?targetHost )." +

            " } GROUP BY ?iri ?id ?accessCall ?file ?pathnameSource ?pathnameTarget ?fileSource ?fileTarget " +
            "               ?dirSource ?dirTarget ?fileType ?process ?pid ?user ?username ?host ?hostName " +
            "               ?ipAddress ?timestamp ?logMessage ?actionName ?timestamp2" +
            "               ?action ?userEvent ?program ?sourceFile ?targetFile ?sourceHost ?targetHost " +
            "}";


    public static final String modifiedConstruct =  "REGISTER STREAM created2 AS " +
            "PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  " +
            "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
            "PREFIX file: <http://w3id.org/sepses/vocab/fileSystemLog#> " +
            "PREFIX fae: <http://w3id.org/sepses/vocab/event/fileAccess#> " +
            "CONSTRUCT { " +
            "   ?iri           rdf:type          fae:FileAccessEvent; " +
            "                    fae:id            ?id; " +
            "                    fae:timestamp     ?timestamp2; "+
            "                    fae:hasAction     ?action;" +
            "                    fae:hasUser       ?userEvent;" +
            "                    fae:hasProgram    ?program;" +
            "                    fae:hasSourceFile ?sourceFile;" +
            "                    fae:hasTargetFile ?targetFile;" +
            "                    fae:hasSourceHost ?sourceHost;" +
            "                    fae:hasTargetHost ?targetHost." +
            "  ?action     a fae:CreatedAction;" +
            "                fae:actionName ?actionName." +
            "  ?userEvent  a fae:User;" +
            "                fae:userName   ?username. " +
            "  ?program    a fae:Program;" +
            "                fae:pid        ?pid.  " +
            "  ?sourceFile a fae:File; " +
            "                fae:pathName   ?pathnameSource; " +
            "                fae:fileName   ?fileSource; " +
            "                fae:directory  ?dirSource. "+
            "  ?targetFile a fae:File; " +
            "                fae:pathName   ?pathnameSource; " +
            "                fae:fileName   ?fileSource; " +
            "                fae:directory  ?dirSource. "+
            "  ?sourceHost a fae:Host; " +
            "                fae:hostName   ?hostName . " +
            "  ?targetHost a fae:Host; " +
            "                fae:hostName   ?hostName . " +
            "} " +
            "FROM STREAM <ws://localhost:8124/tw/stream> [RANGE 10s STEP 3s] " +
            "WHERE { " +
            " SELECT " +
            " ?iri ?id ?accessCall ?file ?pathnameSource ?fileSource " +
            " ?dirSource ?fileType ?process ?pid ?user ?username ?host ?hostName " +
            " ?ipAddress ?timestamp ?logMessage ?actionName ?timestamp2" +
            " ?action ?userEvent ?program ?sourceFile ?targetFile ?sourceHost ?targetHost " +
            " WHERE { "+

            "   ?logEntry file:id              ?id . " +
            "   ?logEntry file:accessCall      ?accessCall . " +
            "   BIND(STRDT(\"Created_Modified\", xsd:string) AS ?actionName) . " +
            "   ?logEntry file:hasFile         ?file . " +
            "   ?file     file:pathnameSource  ?pathnameSource .  " +
            "   ?file     file:fileSource      ?fileSource .  " +
            "   ?file     file:dirSource       ?dirSource .  " +
            "   ?file     file:fileType        ?fileType .  " +
            "   FILTER ( str(?accessCall) = \"open(2) - read,write\" " +
            "   || str(?accessCall) = \"open(2) - read,write,creat,trunc\" " + // create should be excluded .. but also contains write operations
            "   || str(?accessCall) = \"openat(2) - read\" " +
            "   || str(?accessCall) = \"open(2) - write,creat,trunc\" " +
            "   || str(?accessCall) = \"open(2) - read,write\" " +
            "   || str(?accessCall) = \"open(2) - write,creat\" " +
            "   || str(?accessCall) = \"open(2) - write,trunc\" " +
            "   || str(?accessCall) = \"open(2) - write\" ) . " +
            "   ?logEntry file:hasProcess      ?process . " +
            "   ?process  file:processID       ?pid . " +
            "   ?logEntry file:hasUser         ?user . " +
            "   ?user     file:username        ?username . " +
            "   ?logEntry file:originatesFrom  ?host . " +
            "   ?host     file:hostName        ?hostName . " +
            "   ?host     file:ipAddress       ?ipAddress . " +
            "   ?logEntry file:timestamp       ?timestamp . " +
            "   ?logEntry file:logMessage      ?logMessage . " +

            // "   filter contains(?pathnameSource, ?fileSource) ."+

            "   BIND(STRDT(STR(?timestamp), xsd:dateTime) AS ?timestamp2) . " +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id))) AS ?iri )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-Action\")) AS ?action )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-User\")) AS ?userEvent )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-Program\")) AS ?program )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-SourceFile\")) AS ?sourceFile )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-TargetFile\")) AS ?targetFile )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-SourceHost\")) AS ?sourceHost )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-TargetHost\")) AS ?targetHost )." +

            "} GROUP BY " +
            " ?iri ?id ?accessCall ?file ?pathnameSource ?fileSource " +
            " ?dirSource ?fileType ?process ?pid ?user ?username ?host ?hostName " +
            " ?ipAddress ?timestamp ?logMessage ?actionName ?timestamp2" +
            " ?action ?userEvent ?program ?sourceFile ?targetFile ?sourceHost ?targetHost " +

            "}";


    public static final String renamedConstruct = "REGISTER STREAM rename2 AS " +
            "PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  "+
            "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
            "PREFIX file: <http://w3id.org/sepses/vocab/fileSystemLog#> " +
            "PREFIX fae: <http://w3id.org/sepses/vocab/event/fileAccess#> " +
            "CONSTRUCT { " +
            "   ?iri             rdf:type          fae:FileAccessEvent; " +
            "                    fae:id            ?id; " +
            "                    fae:timestamp     ?timestamp2; " +
            "                    fae:hasAction     ?action;" +
            "                    fae:hasUser       ?userEvent;" +
            "                    fae:hasProgram    ?program;" +
            "                    fae:hasSourceFile ?sourceFile;" +
            "                    fae:hasTargetFile ?targetFile;" +
            "                    fae:hasSourceHost ?sourceHost;" +
            "                    fae:hasTargetHost ?targetHost." +
            "  ?action     a fae:CreatedAction;" +
            "                fae:actionName ?actionName." +
            "  ?userEvent  a fae:User;" +
            "                fae:userName   ?username. " +
            "  ?program    a fae:Program;" +
            "                fae:pid      ?pid.  " +
            "  ?sourceFile a fae:File; " +
            "                fae:pathName   ?pathnameSource; " +
            "                fae:fileName ?fileSource; " +
            "                fae:directory ?dirSource. "+
            "  ?targetFile a fae:File; " +
            "                fae:pathName   ?pathnameTarget; " +
            "                fae:fileName ?fileTarget; " +
            "                fae:directory ?dirTarget. "+
            "  ?sourceHost a fae:Host; " +
            "                fae:hostName   ?hostName . " +
            "  ?targetHost a fae:Host; " +
            "                fae:hostName   ?hostName . " +
            "} " +
            "FROM STREAM <ws://localhost:8124/tw/stream> [RANGE 10s STEP 3s] " +
            "WHERE { " +
            " SELECT ?iri ?id ?accessCall ?file ?pathnameSource ?pathnameTarget ?fileSource ?fileTarget " +
            " ?dirSource ?dirTarget ?fileType ?process ?pid ?user ?username ?host ?hostName " +
            " ?ipAddress ?timestamp ?logMessage ?actionName ?timestamp2" +
            " ?action ?userEvent ?program ?sourceFile ?targetFile ?sourceHost ?targetHost { "+

            "   ?logEntry file:id              ?id . " +
            "   ?logEntry file:accessCall      ?accessCall . " +
            "   BIND(STRDT(\"Renamed\", xsd:string) AS ?actionName) . " +
            "   FILTER ( str(?accessCall) = \"rename(2)\" ) .  " +
            "   ?logEntry file:hasFile         ?file . " +
            "   ?file     file:pathnameSource  ?pathnameSource .  " +
            "   ?file     file:pathnameTarget  ?pathnameTarget .  " +
            "   ?file     file:fileSource      ?fileSource .  " +
            "   ?file     file:fileTarget      ?fileTarget .  " +
            "   ?file     file:dirSource       ?dirSource .  " +
            "   ?file     file:dirTarget       ?dirTarget .  " +
            "   ?file     file:fileType        ?fileType .  " +
            "   FILTER ( ?dirSource = ?dirTarget ) . " +
            "   FILTER ( ?fileSource != ?fileTarget ) . " +
            "   ?logEntry file:hasProcess      ?process . " +
            "   ?process  file:processID       ?pid . " +
            "   ?logEntry file:hasUser         ?user . " +
            "   ?user     file:username        ?username . " +
            "   ?logEntry file:originatesFrom  ?host . " +
            "   ?host     file:hostName        ?hostName . " +
            "   ?host     file:ipAddress       ?ipAddress . " +
            "   ?logEntry file:timestamp ?timestamp . " +
            "   BIND(STRDT(STR(?timestamp), xsd:dateTime) AS ?timestamp2) . " +
            "   ?logEntry file:logMessage ?logMessage ." +

            "   BIND(IRI(CONCAT(STR(fae:), STR(?id))) AS ?iri )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-Action\")) AS ?action )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-User\")) AS ?userEvent )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-Program\")) AS ?program )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-SourceFile\")) AS ?sourceFile )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-TargetFile\")) AS ?targetFile )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-SourceHost\")) AS ?sourceHost )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-TargetHost\")) AS ?targetHost )." +

            "} GROUP BY ?iri ?id ?accessCall ?file ?pathnameSource ?pathnameTarget ?fileSource ?fileTarget " +
            "                 ?dirSource ?dirTarget ?fileType ?process ?pid ?user ?username ?host ?hostName " +
            "                 ?ipAddress ?timestamp ?logMessage ?actionName ?timestamp2 " +
            "                 ?action ?userEvent ?program ?sourceFile ?targetFile ?sourceHost ?targetHost" +

            "}";

    public static final String movedConstruct = "REGISTER STREAM moved2 AS " +
            "PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  "+
            "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
            "PREFIX file: <http://w3id.org/sepses/vocab/fileSystemLog#> " +
            "PREFIX fae: <http://w3id.org/sepses/vocab/event/fileAccess#> " +
            "CONSTRUCT { " +
            "   ?iri           rdf:type          fae:FileAccessEvent; " +
            "                    fae:id            ?id; " +
            "                    fae:timestamp     ?timestamp2; " +
            "                    fae:hasAction     ?action;" +
            "                    fae:hasUser       ?userEvent;" +
            "                    fae:hasProgram    ?program;" +
            "                    fae:hasSourceFile ?sourceFile;" +
            "                    fae:hasTargetFile ?targetFile;" +
            "                    fae:hasSourceHost ?sourceHost;" +
            "                    fae:hasTargetHost ?targetHost." +
            "  ?action     a fae:CreatedAction;" +
            "                fae:actionName ?actionName." +
            "  ?userEvent  a fae:User;" +
            "                fae:userName   ?username. " +
            "  ?program    a fae:Program;" +
            "                fae:pid      ?pid.  " +
            "  ?sourceFile a fae:File; " +
            "                fae:pathName   ?pathnameSource; " +
            "                fae:fileName ?fileSource; " +
            "                fae:directory ?dirSource. "+
            "  ?targetFile a fae:File; " +
            "                fae:pathName   ?pathnameTarget; " +
            "                fae:fileName ?fileTarget; " +
            "                fae:directory ?dirTarget. "+
            "  ?sourceHost a fae:Host; " +
            "                fae:hostName   ?hostName . " +
            "  ?targetHost a fae:Host; " +
            "                fae:hostName   ?hostName . " +
            "} " +
            "FROM STREAM <ws://localhost:8124/tw/stream> [RANGE 10s STEP 3s] " +
            "WHERE { " +
            " SELECT ?iri ?id ?accessCall ?file ?pathnameSource ?pathnameTarget ?fileSource ?fileTarget " +
            " ?dirSource ?dirTarget ?fileType ?process ?pid ?user ?username ?host ?hostName " +
            " ?ipAddress ?timestamp ?logMessage ?actionName ?timestamp2" +
            " ?action ?userEvent ?program ?sourceFile ?targetFile ?sourceHost ?targetHost { "+

            "   ?logEntry file:id              ?id . " +
            "   ?logEntry file:accessCall      ?accessCall . " +
            "   BIND(STRDT(\"Moved\", xsd:string) AS ?actionName) . " +
            "   FILTER ( str(?accessCall) = \"rename(2)\" ) .  " +
            "   ?logEntry file:hasFile         ?file . " +
            "   ?file     file:pathnameSource  ?pathnameSource .  " +
            "   ?file     file:pathnameTarget  ?pathnameTarget .  " +
            "   ?file     file:lastDirTarget   ?lastDirTarget .  " +
            "   ?file     file:fileSource      ?fileSource .  " +
            "   ?file     file:fileTarget      ?fileTarget .  " +
            "   ?file     file:dirSource       ?dirSource .  " +
            "   ?file     file:dirTarget       ?dirTarget .  " +
            "   ?file     file:fileType        ?fileType .  " +
            "   FILTER ( ?dirSource != ?dirTarget ) . " +
            "   FILTER ( ?fileSource = ?fileTarget ) . " +
            "   FILTER ( str (?lastDirTarget) != \"/.Trash/\" ) " +
            "   ?logEntry file:hasProcess      ?process . " +
            "   ?process  file:processID       ?pid . " +
            "   ?logEntry file:hasUser         ?user . " +
            "   ?user     file:username        ?username . " +
            "   ?logEntry file:originatesFrom  ?host . " +
            "   ?host     file:hostName        ?hostName . " +
            "   ?host     file:ipAddress       ?ipAddress . " +
            "   ?logEntry file:timestamp ?timestamp . " +
            "   BIND(STRDT(STR(?timestamp), xsd:dateTime) AS ?timestamp2) . " +
            "   ?logEntry file:logMessage ?logMessage ." +

            "   BIND(IRI(CONCAT(STR(fae:), STR(?id))) AS ?iri )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-Action\")) AS ?action )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-User\")) AS ?userEvent )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-Program\")) AS ?program )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-SourceFile\")) AS ?sourceFile )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-TargetFile\")) AS ?targetFile )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-SourceHost\")) AS ?sourceHost )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-TargetHost\")) AS ?targetHost )." +

            "} GROUP BY ?iri ?id ?accessCall ?file ?pathnameSource ?pathnameTarget ?fileSource ?fileTarget " +
            "                 ?dirSource ?dirTarget ?fileType ?process ?pid ?user ?username ?host ?hostName " +
            "                 ?ipAddress ?timestamp ?logMessage ?actionName ?timestamp2 " +
            "                 ?action ?userEvent ?program ?sourceFile ?targetFile ?sourceHost ?targetHost" +

            "}";


    public static final String movedToRecycleBinConstruct = "REGISTER STREAM movedToRecycleBin AS " +
            "PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  "+
            "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
            "PREFIX file: <http://w3id.org/sepses/vocab/fileSystemLog#> " +
            "PREFIX fae: <http://w3id.org/sepses/vocab/event/fileAccess#> " +
            "CONSTRUCT {" +
            "   ?iri           rdf:type          fae:FileAccessEvent; " +
            "                    fae:id            ?id; " +
            "                    fae:timestamp     ?timestamp2; " +
            "                    fae:hasAction     ?action;" +
            "                    fae:hasUser       ?userEvent;" +
            "                    fae:hasProgram    ?program;" +
            "                    fae:hasSourceFile ?sourceFile;" +
            "                    fae:hasTargetFile ?targetFile;" +
            "                    fae:hasSourceHost ?sourceHost;" +
            "                    fae:hasTargetHost ?targetHost." +
            "  ?action     a fae:CreatedAction;" +
            "                fae:actionName ?actionName." +
            "  ?userEvent  a fae:User;" +
            "                fae:userName   ?username. " +
            "  ?program    a fae:Program;" +
            "                fae:pid      ?pid.  " +
            "  ?sourceFile a fae:File; " +
            "                fae:pathName   ?pathnameSource; " +
            "                fae:fileName ?fileSource; " +
            "                fae:directory ?dirSource. "+
            "  ?targetFile a fae:File; " +
            "                fae:pathName   ?pathnameTarget; " +
            "                fae:fileName ?fileTarget; " +
            "                fae:directory ?dirTarget. "+
            "  ?sourceHost a fae:Host; " +
            "                fae:hostName   ?hostName . " +
            "  ?targetHost a fae:Host; " +
            "                fae:hostName   ?hostName . " +
            "}" +
            "FROM STREAM <ws://localhost:8124/tw/stream> [RANGE 10s STEP 3s] " +
            "WHERE { " +
            " SELECT ?iri ?id ?accessCall ?file ?pathnameSource ?pathnameTarget ?fileSource ?fileTarget " +
            " ?dirSource ?dirTarget ?fileType ?process ?pid ?user ?username ?host ?hostName " +
            " ?ipAddress ?timestamp ?logMessage ?actionName ?timestamp2" +
            " ?action ?userEvent ?program ?sourceFile ?targetFile ?sourceHost ?targetHost { "+

            "   ?logEntry file:id              ?id . " +
            "   ?logEntry file:accessCall      ?accessCall . " +
            "   BIND(STRDT(\"MovedToRecycleBin\", xsd:string) AS ?actionName) . " +
            "   FILTER ( str(?accessCall) = \"rename(2)\" ) .  " +
            "   ?logEntry file:hasFile         ?file . " +
            "   ?file     file:pathnameSource  ?pathnameSource .  " +
            "   ?file     file:pathnameTarget  ?pathnameTarget .  " +
            "   ?file     file:lastDirTarget   ?lastDirTarget .  " +
            "   ?file     file:fileSource      ?fileSource .  " +
            "   ?file     file:fileTarget      ?fileTarget .  " +
            "   ?file     file:dirSource       ?dirSource .  " +
            "   ?file     file:dirTarget       ?dirTarget .  " +
            "   ?file     file:fileType        ?fileType .  " +
            "   FILTER ( ?dirSource != ?dirTarget ) . " +
            "   FILTER ( ?fileSource = ?fileTarget ) . " +
            "   FILTER ( str (?lastDirTarget) = \"/.Trash/\" ) " +
            "   ?logEntry file:hasProcess      ?process . " +
            "   ?process  file:processID       ?pid . " +
            "   ?logEntry file:hasUser         ?user . " +
            "   ?user     file:username        ?username . " +
            "   ?logEntry file:originatesFrom  ?host . " +
            "   ?host     file:hostName        ?hostName . " +
            "   ?host     file:ipAddress       ?ipAddress . " +
            "   ?logEntry file:timestamp ?timestamp . " +
            "   BIND(STRDT(STR(?timestamp), xsd:dateTime) AS ?timestamp2) . " +
            "   ?logEntry file:logMessage ?logMessage ." +

            "   BIND(IRI(CONCAT(STR(fae:), STR(?id))) AS ?iri )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-Action\")) AS ?action )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-User\")) AS ?userEvent )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-Program\")) AS ?program )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-SourceFile\")) AS ?sourceFile )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-TargetFile\")) AS ?targetFile )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-SourceHost\")) AS ?sourceHost )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-TargetHost\")) AS ?targetHost )." +

            "} GROUP BY ?iri ?id ?accessCall ?file ?pathnameSource ?pathnameTarget ?fileSource ?fileTarget " +
            "                 ?dirSource ?dirTarget ?fileType ?process ?pid ?user ?username ?host ?hostName " +
            "                 ?ipAddress ?timestamp ?logMessage ?actionName ?timestamp2 " +
            "                 ?action ?userEvent ?program ?sourceFile ?targetFile ?sourceHost ?targetHost" +

            "} ";

    public static String copySameDirConstruct2 = "REGISTER STREAM copySameDir AS " +
            "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  "+
            "PREFIX file: <http://w3id.org/sepses/vocab/fileSystemLog#> " +
            "PREFIX fae: <http://w3id.org/sepses/vocab/event/fileAccess#> " +
            "CONSTRUCT { " +
            "   ?iri             rdf:type          fae:FileAccessEvent; " +
            "                    fae:id            ?id1; " +
            "                    fae:timestamp     ?timestampWithType; " +
            "                    fae:hasAction     ?action;" +
            "                    fae:hasUser       ?userEvent;" +
            "                    fae:hasProgram    ?program;" +
            "                    fae:hasSourceFile ?sourceFile;" +
            "                    fae:hasTargetFile ?targetFile;" +
            "                    fae:hasSourceHost ?sourceHost;" +
            "                    fae:hasTargetHost ?targetHost." +
            "  ?action     a fae:CreatedAction;" +
            "                fae:actionName ?actionName." +
            "  ?userEvent  a fae:User;" +
            "                fae:userName   ?username. " +
            "  ?program    a fae:Program;" +
            "                fae:pid        ?pid.  " +
            "  ?sourceFile a fae:File; " +
            "                fae:pathName   ?pathnameSource1; " +
            "                fae:fileName   ?fileSource1; " +
            "                fae:directory  ?dirSource1. "+
            "  ?targetFile a fae:File; " +
            "                fae:pathName   ?pathnameSource2; " +
            "                fae:fileName   ?fileSource2; " +
            "                fae:directory  ?dirSource2. "+
            "  ?sourceHost a fae:Host; " +
            "                fae:hostName   ?hostName . " +
            "  ?targetHost a fae:Host; " +
            "                fae:hostName   ?hostName . " +
            " } " +
            "FROM STREAM <ws://localhost:8124/tw/stream> [RANGE 10s STEP 3s] " +

            "WHERE { " +
            "   ?logEntry1 file:id        ?id1 . " +
            "   ?logEntry1 file:timestamp        ?timestamp1 . " +
            "   ?logEntry1 file:accessCall       ?accessCall1 . " +
            "   FILTER ( str(?accessCall1) = \"fstatat(2)\" ) . " +
            "   ?logEntry1 file:hasFile          ?file1 . " +
            "   ?file1     file:pathnameSource   ?pathnameSource1 .  " +
            "   ?file1     file:fileSource       ?fileSource1 .  " +
            "   ?file1     file:dirSource        ?dirSource1 .  " +

            "   ?logEntry2 file:id               ?id . " +
            "   ?logEntry2 file:timestamp        ?timestamp2 . " +
            "   ?logEntry2 file:accessCall       ?accessCall2 . " +
            "   BIND(STRDT(\"Created_Copied\", xsd:string) AS ?actionName) . " +
            "   FILTER ( str(?accessCall2) = \"open(2) - write,creat,trunc\" ||" +
            "            str(?accessCall2) = \"open(2) - write,trunc\" ) . " +


            //  "   FILTER ( str(?accessCall2) = \"open(2) - write,creat,trunc\" ) . " +
            "   ?logEntry2 file:hasFile          ?file2 . " +
            "   ?file2     file:pathnameSource   ?pathnameSource2 .  " +
            "   ?file2     file:fileSource       ?fileSource2 .  " +
            "   ?file2     file:dirSource        ?dirSource2 .  " +
            "   ?file2     file:fileType         ?fileType2 .  " +

            "   FILTER (CONTAINS( STR(?fileSource2), STR(\"copy\") )) ."+
            "   BIND(STRBEFORE(STR(?fileSource2), STR(\" copy\")) AS ?originalFilenameWithoutEnding) ."+
            "   BIND(CONCAT(STR(?originalFilenameWithoutEnding), STR(?fileType2)) AS ?originalFilename) ."+
            "   BIND(CONCAT(STR(?dirSource2), STR(?originalFilename)) AS ?originalPathname) ."+

            "   BIND(STRDT(STR(?originalFilename), xsd:string) AS ?originalFilenameWithType) . " +
            "   BIND(STRDT(STR(?originalPathname), xsd:string) AS ?originalPathnameWithType) . " +
            "   BIND(STRDT(STR(?pathnameSource1), xsd:string) AS ?pathnameSource1WithType) . " +
            "   BIND(STRDT(STR(?fileSource1), xsd:string) AS ?fileSource1WithType) . " +

            "   FILTER (?timestamp1  = ?timestamp2) . " +
            "   FILTER ( xsd:string(?pathnameSource1WithType) = ?originalPathnameWithType ) . " +
            "   FILTER ( xsd:string(?fileSource1WithType) = ?originalFilenameWithType ) . " +
            "   FILTER ( ?fileSource1 != ?fileSource2 ) . " +
            "   FILTER ( ?dirSource1 = ?dirSource2 ) . " +
            "   FILTER (CONTAINS( STR(?originalPathname), STR(?originalFilename) )) ."+

            "   BIND(STRDT(STR(?timestamp2), xsd:dateTime) AS ?timestampWithType) . " +

            "   ?logEntry2 file:hasProcess/file:processID ?pid . " +
            "   ?logEntry2 file:hasUser/file:username ?username . " +
            "   ?logEntry file:originatesFrom  ?host . " +
            "   ?host     file:hostName        ?hostName . " +
            "   ?host     file:ipAddress       ?ipAddress . " +
            "   ?logEntry2 file:logMessage ?logMessage . " +

            "   BIND(IRI(CONCAT(STR(fae:), STR(?id))) AS ?iri )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-Action\")) AS ?action )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-User\")) AS ?userEvent )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-Program\")) AS ?program )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-SourceFile\")) AS ?sourceFile )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-TargetFile\")) AS ?targetFile )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-SourceHost\")) AS ?sourceHost )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?id), \"-TargetHost\")) AS ?targetHost )." +

            "} ";

    public static String copySameDirConstruct = "REGISTER STREAM copySameDir AS " +
            "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  "+
            "PREFIX file: <http://w3id.org/sepses/vocab/fileSystemLog#> " +
            "PREFIX fae: <http://w3id.org/sepses/vocab/event/fileAccess#> " +
            "CONSTRUCT { " +
            "   ?iri           rdf:type          fae:FileAccessEvent; " +
            "                    fae:id            ?idAccess; " +
            "                    fae:timestamp     ?timestampWithType; "+
            "                    fae:hasAction     ?action;" +
            "                    fae:hasUser       ?userEvent;" +
            "                    fae:hasProgram    ?program;" +
            "                    fae:hasSourceFile ?sourceFile;" +
            "                    fae:hasTargetFile ?targetFile;" +
            "                    fae:hasSourceHost ?sourceHost;" +
            "                    fae:hasTargetHost ?targetHost." +
            "  ?action     a fae:CreatedAction;" +
            "                fae:actionName ?actionName." +
            "  ?userEvent  a fae:User;" +
            "                fae:userName   ?username. " +
            "  ?program    a fae:Program;" +
            "                fae:pid        ?pid.  " +
            "  ?sourceFile a fae:File; " +
            "                fae:pathName   ?originalPathname; " +
            "                fae:fileName   ?originalFilename; " +
            "                fae:directory  ?dirSourceCreate. "+
            "  ?targetFile a fae:File; " +
            "                fae:pathName   ?pathnameSourceCreate; " +
            "                fae:fileName   ?fileSourceCreate; " +
            "                fae:directory  ?dirSourceCreate. "+
            "  ?sourceHost a fae:Host; " +
            "                fae:hostName   ?hostName . " +
            "  ?targetHost a fae:Host; " +
            "                fae:hostName   ?hostName . " +
            " } " +
            "FROM STREAM <ws://localhost:8124/tw/stream> [RANGE 10s STEP 3s] " +

            "WHERE {" +
            " SELECT " +
            "  ?iri ?idAccess ?timestampWithType ?action ?userEvent ?program " +
            "  ?sourceFile ?targetFile ?sourceHost ?targetHost ?hostName" +
            "  ?actionName ?username ?pid ?originalPathname ?originalFilename " +
            "  ?dirSourceCreate ?pathnameSourceCreate ?fileSourceCreate " +

            "  WHERE {  " +

            " { " +
            "   SELECT * " +
            "   WHERE { " +
            "   ?logEntryAccess file:id               ?idAccess . " +
            "   ?logEntryAccess file:timestamp        ?timestampAccess . " +
            "   ?logEntryAccess file:accessCall       ?accessCallAccess . " +
            "   FILTER ( str(?accessCallAccess) = \"fstatat(2)\" ) . " +
            "   ?logEntryAccess file:hasFile          ?fileAccess . " +
            "   ?fileAccess     file:pathnameSource   ?pathnameSourceAccess .  " +
            "   ?fileAccess     file:fileSource       ?fileSourceAccess .  " +
            "   ?fileAccess     file:dirSource        ?dirSourceAccess .  " +
            "} " +
            " } "+
            " { " +
            "   SELECT  * "  +
            "   WHERE { " +
            "   ?logEntryCreate file:id               ?idCreate . " +
            "   ?logEntryCreate file:timestamp        ?timestampCreate . " +
            "   ?logEntryCreate file:accessCall       ?accessCallCreate . " +

            "   FILTER ( str(?accessCallCreate) = \"open(2) - write,creat,trunc\" ||" +
            "            str(?accessCallCreate) = \"open(2) - write,trunc\" ) . " +
            "   ?logEntryCreate file:hasFile          ?fileCreate . " +
            "   ?fileCreate     file:pathnameSource   ?pathnameSourceCreate .  " +
            "   ?fileCreate     file:fileSource       ?fileSourceCreate .  " +
            "   ?fileCreate     file:dirSource        ?dirSourceCreate .  " +
            "   ?fileCreate     file:fileType         ?fileTypeCreate .  " +
            "   ?fileCreate     file:originalPathname         ?originalPathname .  " +
            "   ?fileCreate     file:originalFilename         ?originalFilename .  " +

            "   ?logEntryCreate file:hasProcess/file:processID ?pid . " +
            "   ?logEntryCreate file:hasUser/file:username ?username . " +
            "   ?logEntry file:originatesFrom  ?host . " +
            "   ?host     file:hostName        ?hostName . " +
            "   ?host     file:ipAddress       ?ipAddress . " +
            "   ?logEntryCreate file:logMessage ?logMessage . " +
            "} " +
            " } " +

            "   FILTER (?timestampAccess  = ?timestampCreate) . " +
            "   FILTER ( ?pathnameSourceAccess = ?originalPathname ) . " +
            "   FILTER ( ?fileSourceAccess = ?originalFilename ) . " +
            "   FILTER ( ?fileSourceAccess != ?fileSourceCreate ) . " +
            "   FILTER ( ?dirSourceAccess = ?dirSourceCreate ) . " +

            "   BIND(STRDT(STR(?timestampCreate), xsd:dateTime) AS ?timestampWithType) . " +
            "   BIND(STRDT(\"Created_Copied\", xsd:string) AS ?actionName) . " +

            "   BIND(IRI(CONCAT(STR(fae:), STR(?idAccess))) AS ?iri )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?idAccess), \"-Action\")) AS ?action )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?idAccess), \"-User\")) AS ?userEvent )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?idAccess), \"-Program\")) AS ?program )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?idAccess), \"-SourceFile\")) AS ?sourceFile )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?idAccess), \"-TargetFile\")) AS ?targetFile )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?idAccess), \"-SourceHost\")) AS ?sourceHost )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?idAccess), \"-TargetHost\")) AS ?targetHost )." +

            "} " +
            "GROUP BY " +
            "  ?iri ?idAccess ?timestampWithType ?action ?userEvent ?program " +
            "  ?sourceFile ?targetFile ?sourceHost ?targetHost ?hostName" +
            "  ?actionName ?username ?pid ?originalPathname ?originalFilename " +
            "  ?dirSourceCreate ?pathnameSourceCreate ?fileSourceCreate " +
            "}";

    public static String copySameDirFinderConstruct = "REGISTER STREAM copySameDirFinder AS " +
            "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  "+
            "PREFIX file: <http://w3id.org/sepses/vocab/fileSystemLog#> " +
            "PREFIX fae: <http://w3id.org/sepses/vocab/event/fileAccess#> " +
            "CONSTRUCT { " +
            "   fae:           rdf:type          fae:FileAccessEvent; " +
            "                    fae:id            ?id; " +
            "                    fae:timestamp     ?timestampWithType; " +
            "                    fae:hasAction     [a fae:CreatedCopiedAction; fae:actionName ?actionName]; " +
            "                    fae:hasUser       [a fae:User; fae:userName   ?username]; " +
            "                    fae:hasProgram    [a fae:Program; fae:pid      ?pid]; " +
            "                    fae:hasSourceFile ?sourceFile;" +
            "                    fae:hasTargetFile ?targetFile;" +
            "                    fae:hasSourceHost ?sourceHost;" +
            "                    fae:hasTargetHost ?targetHost." +
            "  ?sourceFile a fae:File; " +
            "                fae:pathName   ?originalPathname; " +
            "                fae:fileName ?originalFilename; " +
            "                fae:directory ?dirSource2. "+
            "  ?targetFile a fae:File; " +
            "                fae:pathName   ?pathnameSource2; " +
            "                fae:fileName ?fileSource2; " +
            "                fae:directory ?dirSource2. "+
            "  ?sourceHost a fae:Host; " +
            "                fae:hostName   ?hostName . " +
            "  ?targetHost a fae:Host; " +
            "                fae:hostName   ?hostName . " +
            " } " +
            "FROM STREAM <ws://localhost:8124/tw/stream> [RANGE 10s STEP 3s] " +
            "WHERE { " +
            "SELECT " +
            "    ?iri ?id ?timestamp2 ?action ?actionName ?userEvent ?username ?program ?pid " +
            "    ?sourceFile ?originalPathname ?originalFilename ?dirSource2 " +
            "    ?targetFile ?pathnameSource2 ?fileSource2 ?dirSource2 " +
            "    ?sourceHost ?targetHost ?hostName " +
            " {" +


            "   ?logEntry1 file:timestamp        ?timestamp1 . " +
            "   ?logEntry1 file:accessCall       ?accessCall1 . " +
            "   FILTER ( str(?accessCall1) = \"open(2) - read\" ) . " +
            "   ?logEntry1 file:hasFile          ?file1 . " +
            "   ?file1     file:pathnameSource   ?pathnameSource1 .  " +
            "   ?file1     file:fileSource       ?fileSource1 .  " +
            "   ?file1     file:dirSource        ?dirSource1 .  " +

            "   ?logEntry2 file:id               ?id . " +
            "   ?logEntry2 file:timestamp        ?timestamp2 . " +
            "   ?logEntry2 file:accessCall       ?accessCall2 . " +
            "   BIND(STRDT(\"Created_Copied\", xsd:string) AS ?actionName) . " +
            "   FILTER ( str(?accessCall2) = \"setattrlist()\" ) . " +
            "   ?logEntry2 file:hasFile          ?file2 . " +
            "   ?file2     file:pathnameSource   ?pathnameSource2 .  " +
            "   ?file2     file:fileSource       ?fileSource2 .  " +
            "   ?file2     file:dirSource        ?dirSource2 .  " +
            "   ?file2     file:fileType         ?fileType2 .  " +
            "   ?file2     file:originalFilename ?originalFilename .  " +
            "   ?file2     file:originalPathname ?originalPathname .  " +
            "   BIND(CONCAT( ?originalFilename, ?fileType2 ) AS ?originalFileNameCopy ) . " +
            "   BIND(CONCAT( ?dirSource2, ?originalFilename, ?fileType2 ) AS ?originalPathNameCopy ) . " +

            "   BIND ((xsd:dateTime(?timestamp1)-\"PT4S\"^^xsd:duration) AS ?timeTmp) . " +
            "   FILTER ( xsd:dateTime(?timeTmp) <= xsd:dateTime(?timestamp1) ) . " +
            "   FILTER ( xsd:dateTime(?timestamp1)  < xsd:dateTime(?timestamp2) ) . " +

            "   BIND(STRDT(STR(?timestamp2), xsd:dateTime) AS ?timestampWithType) . " +

            "   FILTER ( ?pathnameSource1 = ?originalPathname ) . " +
            "   FILTER ( ?fileSource1 != ?fileSource2 ) . " +
            "   FILTER ( ?dirSource1 = ?dirSource2 ) . " +

            "   ?logEntry2 file:hasProcess/file:processID ?pid . " +
            "   ?logEntry2 file:hasUser/file:username ?username . " +
            "   ?logEntry file:originatesFrom  ?host . " +
            "   ?host     file:hostName        ?hostName . " +
            "   ?host     file:ipAddress       ?ipAddress . " +
            "   ?logEntry2 file:logMessage ?logMessage ." +

            "   BIND( fae:SourceFile AS ?sourceFile) . " +
            "   BIND( fae:TargetFile AS ?targetFile) . " +
            "   BIND( fae:SourceHost AS ?sourceHost) . " +
            "   BIND( fae:TargetHost AS ?targetHost) . " +
            "} GROUP BY " +
            "    ?iri ?id ?timestamp2 ?action ?actionName ?userEvent ?username ?program ?pid " +
            "    ?sourceFile ?originalPathname ?originalFilename ?dirSource2 " +
            "    ?targetFile ?pathnameSource2 ?fileSource2 ?dirSource2 " +
            "    ?sourceHost ?targetHost ?hostName " +
            "}";

    public static String copyDiffDirConstruct = "REGISTER STREAM copyDiffDir AS " +
            "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  "+
            "PREFIX file: <http://w3id.org/sepses/vocab/fileSystemLog#> " +
            "PREFIX fae: <http://w3id.org/sepses/vocab/event/fileAccess#> " +
            "CONSTRUCT { " +
            "   ?iri           rdf:type          fae:FileAccessEvent; " +
            "                    fae:id            ?idAccess; " +
            "                    fae:timestamp     ?timestampWithType; "+
            "                    fae:hasAction     ?action;" +
            "                    fae:hasUser       ?userEvent;" +
            "                    fae:hasProgram    ?program;" +
            "                    fae:hasSourceFile ?sourceFile;" +
            "                    fae:hasTargetFile ?targetFile;" +
            "                    fae:hasSourceHost ?sourceHost;" +
            "                    fae:hasTargetHost ?targetHost." +
            "  ?action     a fae:CreatedAction;" +
            "                fae:actionName ?actionName." +
            "  ?userEvent  a fae:User;" +
            "                fae:userName   ?username. " +
            "  ?program    a fae:Program;" +
            "                fae:pid        ?pid.  " +
            "  ?sourceFile a fae:File; " +
            "                fae:pathName   ?pathnameSourceAccess; " +
            "                fae:fileName   ?fileSourceAccess; " +
            "                fae:directory  ?dirSourceAccess. "+
            "  ?targetFile a fae:File; " +
            "                fae:pathName   ?pathnameSourceCreate; " +
            "                fae:fileName   ?fileSourceCreate; " +
            "                fae:directory  ?dirSourceCreate. "+
            "  ?sourceHost a fae:Host; " +
            "                fae:hostName   ?hostName . " +
            "  ?targetHost a fae:Host; " +
            "                fae:hostName   ?hostName . " +
            " } " +
            "FROM STREAM <ws://localhost:8124/tw/stream> [RANGE 10s STEP 3s] " +

            "WHERE {" +
            " SELECT " +
            "  ?iri ?idAccess ?timestampWithType ?action ?userEvent ?program " +
            "  ?sourceFile ?targetFile ?sourceHost ?targetHost ?hostName" +
            "  ?actionName ?username ?pid " +
            "  ?pathnameSourceAccess ?fileSourceAccess ?dirSourceAccess" +
            "  ?pathnameSourceCreate ?fileSourceCreate ?dirSourceCreate" +

            "  WHERE {  " +

            " { " +
            "   SELECT * " +
            "   WHERE { " +
            "   ?logEntryAccess file:id               ?idAccess . " +
            "   ?logEntryAccess file:timestamp        ?timestampAccess . " +
            "   ?logEntryAccess file:accessCall       ?accessCallAccess . " +
            "   FILTER ( str(?accessCallAccess) = \"fstatat(2)\" ) . " +
            "   ?logEntryAccess file:hasFile          ?fileAccess . " +
            "   ?fileAccess     file:pathnameSource   ?pathnameSourceAccess .  " +
            "   ?fileAccess     file:fileSource       ?fileSourceAccess .  " +
            "   ?fileAccess     file:dirSource        ?dirSourceAccess .  " +
            "   } " +
            " } "+

            " { " +
            "   SELECT  * "  +
            "   WHERE { " +
            "   ?logEntryCreate file:id               ?idCreate . " +
            "   ?logEntryCreate file:timestamp        ?timestampCreate . " +
            "   ?logEntryCreate file:accessCall       ?accessCallCreate . " +

            "   FILTER ( str(?accessCallCreate) = \"open(2) - write,creat,trunc\" ) . " +
            "   ?logEntryCreate file:hasFile          ?fileCreate . " +
            "   ?fileCreate     file:pathnameSource   ?pathnameSourceCreate .  " +
            "   ?fileCreate     file:fileSource       ?fileSourceCreate .  " +
            "   ?fileCreate     file:dirSource        ?dirSourceCreate .  " +
            "   ?fileCreate     file:fileType         ?fileTypeCreate .  " +

            "   ?logEntryCreate file:hasProcess/file:processID ?pid . " +
            "   ?logEntryCreate file:hasUser/file:username ?username . " +
            "   ?logEntry file:originatesFrom  ?host . " +
            "   ?host     file:hostName        ?hostName . " +
            "   ?host     file:ipAddress       ?ipAddress . " +
            "   ?logEntryCreate file:logMessage ?logMessage . " +
            "   } " +
            " } " +

            "   FILTER ( ?timestampAccess  = ?timestampCreate ) . " +
            "   FILTER ( ?fileSourceAccess = ?fileSourceCreate ) . " +
            "   FILTER ( ?dirSourceAccess != ?dirSourceCreate ) . " +

            "   BIND(STRDT(STR(?timestampCreate), xsd:dateTime) AS ?timestampWithType) . " +
            "   BIND(STRDT(\"Created_Copied\", xsd:string) AS ?actionName) . " +

            "   BIND(IRI(CONCAT(STR(fae:), STR(?idAccess))) AS ?iri )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?idAccess), \"-Action\")) AS ?action )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?idAccess), \"-User\")) AS ?userEvent )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?idAccess), \"-Program\")) AS ?program )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?idAccess), \"-SourceFile\")) AS ?sourceFile )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?idAccess), \"-TargetFile\")) AS ?targetFile )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?idAccess), \"-SourceHost\")) AS ?sourceHost )." +
            "   BIND(IRI(CONCAT(STR(fae:), STR(?idAccess), \"-TargetHost\")) AS ?targetHost )." +

            "} " +
            "GROUP BY " +
            "  ?iri ?idAccess ?timestampWithType ?action ?userEvent ?program " +
            "  ?sourceFile ?targetFile ?sourceHost ?targetHost ?hostName" +
            "  ?actionName ?username ?pid " +
            "  ?pathnameSourceAccess ?fileSourceAccess ?dirSourceAccess " +
            "  ?pathnameSourceCreate ?fileSourceCreate ?dirSourceCreate" +
            "}";




    public static String copyDiffDirFinderConstruct = "REGISTER STREAM copyDiffDirFinder AS " +
            "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
            "PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  "+
            "PREFIX file: <http://w3id.org/sepses/vocab/fileSystemLog#> " +
            "PREFIX fae: <http://w3id.org/sepses/vocab/event/fileAccess#> " +
            "CONSTRUCT { " +
            "   fae:           rdf:type          fae:FileAccessEvent; " +
            "                    fae:id            ?id; " +
            "                    fae:timestamp     ?timestampWithType; " +
            "                    fae:hasAction     [a fae:CreatedCopiedAction; fae:actionName ?actionName]; " +
            "                    fae:hasUser       [a fae:User; fae:userName   ?username]; " +
            "                    fae:hasProgram    [a fae:Program; fae:pid      ?pid]; " +
            "                    fae:hasSourceFile ?sourceFile;" +
            "                    fae:hasTargetFile ?targetFile;" +
            "                    fae:hasSourceHost ?sourceHost;" +
            "                    fae:hasTargetHost ?targetHost." +
            "  ?sourceFile a fae:File; " +
            "                fae:pathName   ?pathnameSource1; " +
            "                fae:fileName ?fileSource1; " +
            "                fae:directory ?dirSource1. "+
            "  ?targetFile a fae:File; " +
            "                fae:pathName   ?pathnameSource2; " +
            "                fae:fileName ?fileSource2; " +
            "                fae:directory ?dirSource2. "+
            "  ?sourceHost a fae:Host; " +
            "                fae:hostName   ?hostName . " +
            "  ?targetHost a fae:Host; " +
            "                fae:hostName   ?hostName . " +
            " } " +
            "FROM STREAM <ws://localhost:8124/tw/stream> [RANGE 10s STEP 3s] " +
            "WHERE { " +
            "SELECT " +
            "    ?iri ?id ?timestamp2 ?action ?actionName ?userEvent ?username ?program ?pid " +
            "    ?sourceFile ?originalPathname ?originalFilename ?dirSource2 " +
            "    ?targetFile ?pathnameSource2 ?fileSource2 ?dirSource2 " +
            "    ?sourceHost ?targetHost ?hostName " +
            " { "+

            "   ?logEntry1 file:timestamp        ?timestamp1 . " +
            "   ?logEntry1 file:accessCall       ?accessCall1 . " +
            "   FILTER ( str(?accessCall1) = \"open(2) - read\" ) . " +
            "   ?logEntry1 file:hasFile          ?file1 . " +
            "   ?file1     file:pathnameSource   ?pathnameSource1 .  " +
            "   ?file1     file:fileSource       ?fileSource1 .  " +
            "   ?file1     file:dirSource        ?dirSource1 .  " +
            "   ?file1     file:fileType         ?fileType1 .  " +

            "   ?logEntry2 file:id               ?id . " +
            "   ?logEntry2 file:timestamp        ?timestamp2 . " +
            "   ?logEntry2 file:accessCall       ?accessCall2 . " +
            "   BIND(STRDT(\"Created_Copied\", xsd:string) AS ?actionName) . " +
            "   FILTER ( str(?accessCall2) = \"setattrlist()\" ) . " +
            "   ?logEntry2 file:hasFile          ?file2 . " +
            "   ?file2     file:pathnameSource   ?pathnameSource2 .  " +
            "   ?file2     file:fileSource       ?fileSource2 .  " +
            "   ?file2     file:dirSource        ?dirSource2 .  " +

            "   BIND ((xsd:dateTime(?timestamp1)-\"PT4S\"^^xsd:duration) AS ?timeTmp) . " +
            "   FILTER ( xsd:dateTime(?timeTmp) <= xsd:dateTime(?timestamp1) ) . " +
            "   FILTER ( xsd:dateTime(?timestamp1)  < xsd:dateTime(?timestamp2) ) . " +

            "   BIND(STRDT(STR(?timestamp1), xsd:dateTime) AS ?timestampWithType) . " +

            "   FILTER ( ?fileSource1 = ?fileSource2 ) . " +
            "   FILTER ( ?dirSource1 != ?dirSource2 ) . " +

            "   ?logEntry2 file:hasProcess/file:processID ?pid . " +
            "   ?logEntry2 file:hasUser/file:username ?username . " +
            "   ?logEntry file:originatesFrom  ?host . " +
            "   ?host     file:hostName        ?hostName . " +
            "   ?host     file:ipAddress       ?ipAddress . " +
            "   ?logEntry2 file:logMessage ?logMessage ." +

            "OPTIONAL {       " +
            "   ?eventMoved           fae:hasAction     ?eventMovedAction . " +
            "   ?eventMovedAction     fae:actionName    \"Moved\" . " +
            "   ?eventMoved           fae:hasSourceFile ?eventMovedSourceFile . " +
            "   ?eventMovedSourceFile fae:pathName      ?eventMovedPathNameSource . " +
            "   ?eventMoved           fae:hasTargetFile ?eventMovedTargetFile . " +
            "   ?eventMovedTargetFile fae:pathName      ?eventMovedPathNameTarget . " +
            "   FILTER ( ?pathnameSource1 != ?eventMovedPathNameSource ) . " + //path of copy event must not be part of source or target path of a Moved event
            "   FILTER ( ?pathnameSource2 != ?eventMovedPathNameTarget ) . " +
            "}" +

            "   BIND( fae:SourceFile AS ?sourceFile) . " +
            "   BIND( fae:TargetFile AS ?targetFile) . " +
            "   BIND( fae:SourceHost AS ?sourceHost) . " +
            "   BIND( fae:TargetHost AS ?targetHost) . " +
            "} GROUP BY " +
            "    ?iri ?id ?timestamp2 ?action ?actionName ?userEvent ?username ?program ?pid " +
            "    ?sourceFile ?originalPathname ?originalFilename ?dirSource2 " +
            "    ?targetFile ?pathnameSource2 ?fileSource2 ?dirSource2 " +
            "    ?sourceHost ?targetHost ?hostName " +
            "}";

}