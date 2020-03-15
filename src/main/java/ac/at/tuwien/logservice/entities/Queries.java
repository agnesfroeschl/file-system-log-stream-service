package ac.at.tuwien.logservice.entities;


public final class Queries {

    public static String getRelatedEvents(String pathname){
        return "PREFIX fae: <http://w3id.org/sepses/vocab/event/fileAccess#> " +
                "SELECT distinct ?id ?timestamp ?actionName ?fileNameSource ?pathNameSource ?fileNameTarget ?pathnameTarget ?username ?programName ?pid ?hostnameSource ?hostnameTarget " +
                "WHERE {" +
                "  ?y fae:id ?id . " +
                "  ?y fae:timestamp ?timestamp . " +
                "  ?y fae:hasAction/fae:actionName ?actionName . " +
                "  ?y fae:hasUser/fae:userName ?username." +
               // "  ?y fae:hasProgram/fae:programName ?programName . " +
                "  ?y fae:hasProgram/fae:pid ?pid . " +
                "  ?y fae:hasSourceFile/fae:fileName ?fileNameSource . " +
                "  ?y fae:hasSourceFile/fae:pathName ?pathNameSource . " +
                "  ?y fae:hasTargetFile/fae:fileName ?fileNameTarget . " +
                "  ?y fae:hasTargetFile/fae:pathName ?pathnameTarget . " +
                "  ?y fae:hasSourceHost/fae:hostName ?hostnameSource . " +
                "  ?y fae:hasTargetHost/fae:hostName ?hostnameTarget . " +
                "  ?x fae:hasSourceFile/fae:pathName ?xfilename . " +
                "  ?x fae:hasSourceFile/fae:pathName ?xpathname . " +
                "  ?x fae:hasTargetFile/fae:pathName ?xpathnameTarget . " +
                "  ?x fae:relatedTo* ?y . " +
                "  FILTER ((str(?xpathname) = \""+pathname+"\") " +
                "               || (str(?xpathnameTarget) = \""+pathname+"\") ) ." +
                "} ORDER BY ASC(?timestamp)";
    }

    public static String constructRelatedTo(){
        return "PREFIX fae: <http://w3id.org/sepses/vocab/event/fileAccess#> " +
                "   CONSTRUCT {" +
                "       ?event1  fae:relatedTo ?event2 ." +
                "       ?event1  fae:relatedTo ?event3 ." +
                "       ?event1  fae:relatedTo ?event4 ." +

                "       ?event2  fae:relatedTo ?event1 ." +
                "       ?event2  fae:relatedTo ?event3 ." +
                "       ?event2  fae:relatedTo ?event4 ." +

                "       ?event3  fae:relatedTo ?event1 ." +
                "       ?event3  fae:relatedTo ?event2 ." +
                "       ?event3  fae:relatedTo ?event4 ." +

                "       ?event4  fae:relatedTo ?event1 ." +
                "       ?event4  fae:relatedTo ?event2 ." +
                "       ?event4  fae:relatedTo ?event3 ." +
                "   }" +
                "   WHERE { " +
                "       ?event1      fae:timestamp      ?timestamp1 . " +
                "       ?event1      fae:hasAction      ?action1 . " +
                "       ?action1     fae:actionName     ?actionName1 . " +
                "       ?event1      fae:hasTargetFile  ?targetFile1 . " +
                "       ?targetFile1 fae:pathName       ?pathNameTarget1 . " +

                "   OPTIONAL{  " +
                "       ?event2      fae:timestamp      ?timestamp2 . " +
                "       FILTER ( ?timestamp1 < ?timestamp2 ) . " +
                "       ?event2      fae:hasAction      ?action2 . " +
                "       ?action2     fae:actionName     ?actionName2 . " +
                "       ?event2      fae:hasSourceFile  ?sourceFile2 . " +
                "       ?sourceFile2 fae:pathName       ?pathNameSource2 . " +
                "       ?event2      fae:hasTargetFile  ?targetFile2 . " +
                "       ?targetFile2 fae:pathName       ?pathNameTarget2 . " +

                "       FILTER ( str(?pathNameSource1) = str(?pathNameSource2) ) ." +
                "       FILTER ( str(?pathNameTarget1) = str(?pathNameTarget2) ) . }" +

                "   OPTIONAL{  " +
                "       ?event3      fae:timestamp      ?timestamp3 . " +
                "       FILTER ( ?timestamp1 <= ?timestamp3 ) . " +
                "       ?event3      fae:hasAction      ?action3 . " +
                "       ?action3     fae:actionName     ?actionName3 . " +
                "       ?event3      fae:hasSourceFile  ?sourceFile3 . " +
                "       ?sourceFile3 fae:pathName       ?pathNameSource3 . " +
                "       ?event3      fae:hasTargetFile  ?targetFile3 . " +
                "       ?targetFile3 fae:pathName       ?pathNameTarget3 . " +

                "       FILTER ( str(?pathNameTarget1) = str(?pathNameSource3) ) ." +
                "       FILTER ( str(?pathNameTarget1) != str(?pathNameTarget3) ) . }" +

                "   OPTIONAL{   " +
                "       ?event4      fae:timestamp      ?timestamp4 . " +
                "       FILTER ( ?timestamp3 <= ?timestamp4 ) ." +
                "       ?event4      fae:hasAction      ?action4 . " +
                "       ?action4     fae:actionName     ?actionName4 . " +
                "       ?event4      fae:hasSourceFile  ?sourceFile4 . " +
                "       ?sourceFile4 fae:pathName       ?pathNameSource4 . " +

                "       FILTER ( str(?pathNameTarget3) = str(?pathNameSource4) )  . }" +

                "   } ORDER BY ASC(?timestamp1)";
    }

    public static String constructRelatedTo(String timeThreshold){
        return "PREFIX fae: <http://w3id.org/sepses/vocab/event/fileAccess#> " +
                "   CONSTRUCT {" +
                "       ?event1 fae:relatedTo ?event2 ." +
                "       ?event2 fae:relatedTo ?event1 ." +
                "       ?event2 fae:relatedTo ?event3 ." +
                "       ?event3 fae:relatedTo ?event2 ." +
                "       ?event1 fae:relatedTo ?event3 ." +
                "   }" +
                "   WHERE { " +
                "       ?event1 fae:timestamp ?timestamp1 . " +
                "       FILTER ( ?timestamp >= \"" + timeThreshold + "\"^^<http://www.w3.org/2001/XMLSchema#dateTime>) . " +
                "       ?event1 fae:hasAction ?action1 . " +
                "       ?action1 fae:actionName ?actionName1 . " +
                "       ?event1 fae:hasTargetFile ?targetFile1 . " +
                "       ?targetFile1 fae:pathName ?pathNameTarget1 . " +

                "   OPTIONAL{  " +
                "       ?event1a fae:timestamp ?timestamp1a . " +
                "       FILTER ( ?timestamp1 < ?timestamp1a ) . " +
                "       ?event1a fae:hasAction ?action1a . " +
                "       ?action1a fae:actionName ?actionName1a . " +
                "       ?event1a fae:hasSourceFile ?sourceFile1a . " +
                "       ?sourceFile1a fae:pathName ?pathNameSource1a . " +
                "       ?event1a fae:hasTargetFile ?targetFile1a . " +
                "       ?targetFile1a fae:pathName ?pathNameTarget1a . " +

                "       FILTER ( str(?pathNameSource1) = str(?pathNameSource1a) ) ." +
                "       FILTER ( str(?pathNameTarget1) = str(?pathNameTarget1a) ) . }" +

                "   OPTIONAL{  " +
                "       ?event2 fae:timestamp ?timestamp2 . " +
                "       FILTER ( ?timestamp1 <= ?timestamp2 ) . " +
                "       ?event2 fae:hasAction ?action2 . " +
                "       ?action2 fae:actionName ?actionName2 . " +
                "       ?event2 fae:hasSourceFile ?sourceFile2 . " +
                "       ?sourceFile2 fae:pathName ?pathNameSource2 . " +
                "       ?event2 fae:hasTargetFile ?targetFile2 . " +
                "       ?targetFile2 fae:pathName ?pathNameTarget2 . " +

                "       FILTER ( str(?pathNameTarget1) = str(?pathNameSource2) ) ." +
                "       FILTER ( str(?pathNameTarget1) != str(?pathNameTarget2) ) . }" +

                "   OPTIONAL{   " +
                "       ?event3 fae:timestamp ?timestamp3 . " +
                "       FILTER ( ?timestamp2 <= ?timestamp3 ) ." +
                "       ?event3 fae:hasAction ?action3 . " +
                "       ?action3 fae:actionName ?actionName3 . " +
                "       ?event3 fae:hasSourceFile ?sourceFile3 . " +
                "       ?sourceFile3 fae:pathName ?pathNameSource3 . " +

                "       FILTER ( str(?pathNameTarget2) = str(?pathNameSource3) )  . }" +

                "   } ORDER BY ASC(?timestamp1)";
    }

    public static String constructCopySameDirEvent(){
        return "PREFIX fae: <http://w3id.org/sepses/vocab/event/fileAccess#> " +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                "CONSTRUCT { " +
                "            ?copyEvent              fae:id             ?id . " +
                "            ?copyEvent              fae:timestamp      ?timestamp . " +
                "            ?copyEvent              fae:hasAction      ?copyEventAction . " +
                "            ?copyEventAction        fae:actionName     \"Created_Copied\" . " +
                "            ?copyEvent              fae:hasUser        ?copyEventUser . " +
                "            ?copyEventUser          fae:userName       ?username . " +
                "            ?copyEvent              fae:hasProgram     ?copyEventProgram . " +
                "            ?copyEventProgram       fae:programName    ?programName . " +
                "            ?copyEventProgram       fae:pid            ?pid . " +
                "            ?copyEvent              fae:hasSourceFile  ?copyEventSourceFile . " +
                "            ?copyEventSourceFile    fae:fileName       ?originalFilename . " +
                "            ?copyEventSourceFile    fae:pathName       ?originalPathname . " +
                "            ?copyEvent              fae:hasTargetFile  ?copyEventTargetFile . " +
                "            ?copyEventTargetFile    fae:fileName       ?fileNameSource . " +
                "            ?copyEventTargetFile    fae:pathName       ?pathNameSource . " +
                "            ?copyEvent              fae:hasSourceHost  ?copyEventSourceHost . " +
                "            ?copyEventSourceHost    fae:hostName       ?sourceHostName . " +
                "            ?copyEvent              fae:hasTargetHost  ?copyEventTargetHost . " +
                "            ?copyEventTargetHost    fae:hostName       ?targetHostName . " +
                " } " +
                "WHERE { " +
                "SELECT distinct ?id ?timestamp ?copyEventTimestamp ?copyEventAction ?copyEventUser  ?username ?copyEventProgram " +
                " ?programName ?pid ?copyEventSourceFile ?originalFilename ?originalPathname ?copyEventTargetFile " +
                " ?fileNameSource ?pathNameSource ?copyEventSourceHost ?sourceHostName ?copyEventTargetHost " +
                " ?targetHostName ?copyEvent " +
                "WHERE { "+
                "       ?event      fae:timestamp     ?timestamp . " +
                "       ?event      fae:hasAction     ?action . " +
                "       ?action     fae:actionName    ?actionName . " +
                "       FILTER (str(?actionName) = \"Created_Modified\" ) . " +
                "       ?event      fae:hasSourceFile ?sourceFile . " +
                "       ?sourceFile fae:pathName      ?pathNameSource . " +
                "       ?sourceFile fae:fileName      ?fileNameSource . " +
                "       BIND ( STRDT(STRAFTER(REPLACE(str(?pathNameSource), \"(/[a-zA-Z0-9-_ :.~$]+)+\", \"$1\"), \"/\"), xsd:string) AS ?filename)" + //extract filename from path
                "       FILTER ( contains(str(?filename), \" copy\")) . " +
                "       BIND ( STRAFTER(?filename, \" copy.\") AS ?fileEnding) . " + //extract file ending
                "       BIND ( STRBEFORE(?filename, \" copy.\") AS ?originalName) . " + //extract filename from original file
                "       BIND ( STRBEFORE(?pathNameSource, ?fileNameSource) AS ?path) . " + //extract directory
                "       BIND ( CONCAT(?originalName, \".\", ?fileEnding) AS ?originalFilename) . " + //extract directory
                "       BIND ( CONCAT(?path, ?originalName, \".\", ?fileEnding) AS ?originalPathname) . " + //extract directory
                "       ?event      fae:hasTargetFile ?targetFile . " +
                "       ?targetFile fae:pathName      ?pathNameTarget . " +
                "       ?targetFile fae:fileName      ?fileNameTarget . " +
                "       ?event      fae:hasUser       ?user . " +
                "       ?user       fae:userName      ?username . " +
                "       ?event      fae:hasProgram    ?program . " +
                "       ?program    fae:programName   ?programName . " +
                "       ?program    fae:pid           ?pid . " +
                "       ?event      fae:hasSourceHost ?sourceHost . " +
                "       ?sourceHost fae:hostName      ?sourceHostName . " +
                "       ?event      fae:hasTargetHost ?targetHost . " +
                "       ?targetHost fae:hostName      ?targetHostName . " +
                "       BIND (StrUUID() AS ?strUuid) . " +
                "       BIND (?strUuid AS ?id) . " +
                "       BIND(URI(CONCAT(STR( fae: ), \"-\", ?strUuid)) AS ?copyEvent ) . " +
                "       BIND(URI(CONCAT(STR( fae:Action ), \"-\", ?strUuid)) AS ?copyEventAction ) . " +
                "       BIND(URI(CONCAT(STR( fae:User ), \"-\", ?strUuid)) AS ?copyEventUser ) . " +
                "       BIND(URI(CONCAT(STR( fae:Program ), \"-\", ?strUuid)) AS ?copyEventProgram ) . " +
                "       BIND(URI(CONCAT(STR( fae:SourceFile ), \"-\", ?strUuid)) AS ?copyEventSourceFile ) . " +
                "       BIND(URI(CONCAT(STR( fae:TargetFile ), \"-\", ?strUuid)) AS ?copyEventTargetFile ) . " +
                "       BIND(URI(CONCAT(STR( fae:SourceHost ), \"-\", ?strUuid)) AS ?copyEventSourceHost ) . " +
                "       BIND(URI(CONCAT(STR( fae:TargetHost ), \"-\", ?strUuid)) AS ?copyEventTargetHost ) . " +
                "} GROUP BY ?timestamp ?copyEventTimestamp ?id ?copyEventAction ?copyEventUser  ?username ?copyEventProgram " +
                "                ?programName ?pid ?copyEventSourceFile ?originalFilename ?originalPathname ?copyEventTargetFile " +
                "                 ?fileNameSource ?pathNameSource ?copyEventSourceHost ?sourceHostName ?copyEventTargetHost " +
                "                 ?targetHostName ?copyEvent "+
                "}";
    }

    public static String constructCopySameDirEvent(String timeThreshold){
        return "PREFIX fae: <http://w3id.org/sepses/vocab/event/fileAccess#> " +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                "CONSTRUCT { " +
                "            ?copyEvent              fae:id             ?id . " +
                "            ?copyEvent              fae:timestamp      ?timestamp . " +
                "            ?copyEvent              fae:hasAction      ?copyEventAction . " +
                "            ?copyEventAction        fae:actionName     \"Created_Copied\" . " +
                "            ?copyEvent              fae:hasUser        ?copyEventUser . " +
                "            ?copyEventUser          fae:userName       ?username . " +
                "            ?copyEvent              fae:hasProgram     ?copyEventProgram . " +
                "            ?copyEventProgram       fae:programName    ?programName . " +
                "            ?copyEventProgram       fae:pid            ?pid . " +
                "            ?copyEvent              fae:hasSourceFile  ?copyEventSourceFile . " +
                "            ?copyEventSourceFile    fae:fileName       ?originalFilename . " +
                "            ?copyEventSourceFile    fae:pathName       ?originalPathname . " +
                "            ?copyEvent              fae:hasTargetFile  ?copyEventTargetFile . " +
                "            ?copyEventTargetFile    fae:fileName       ?fileNameSource . " +
                "            ?copyEventTargetFile    fae:pathName       ?pathNameSource . " +
                "            ?copyEvent              fae:hasSourceHost  ?copyEventSourceHost . " +
                "            ?copyEventSourceHost    fae:hostName       ?sourceHostName . " +
                "            ?copyEvent              fae:hasTargetHost  ?copyEventTargetHost . " +
                "            ?copyEventTargetHost    fae:hostName       ?targetHostName . " +
                " } " +
                "WHERE { " +
                "SELECT distinct ?id ?timestamp ?copyEventTimestamp ?copyEventAction ?copyEventUser  ?username ?copyEventProgram " +
                " ?programName ?pid ?copyEventSourceFile ?originalFilename ?originalPathname ?copyEventTargetFile " +
                " ?fileNameSource ?pathNameSource ?copyEventSourceHost ?sourceHostName ?copyEventTargetHost " +
                " ?targetHostName ?copyEvent " +
                "WHERE { "+
                "       ?event      fae:timestamp     ?timestamp . " +
                "       FILTER ( ?timestamp >= \"" + timeThreshold + "\"^^<http://www.w3.org/2001/XMLSchema#dateTime>) . " +
                "       ?event      fae:hasAction     ?action . " +
                "       ?action     fae:actionName    ?actionName . " +
                "       FILTER (str(?actionName) = \"Created_Modified\" ) . " +
                "       ?event      fae:hasSourceFile ?sourceFile . " +
                "       ?sourceFile fae:pathName      ?pathNameSource . " +
                "       ?sourceFile fae:fileName      ?fileNameSource . " +
                "       BIND ( STRDT(STRAFTER(REPLACE(str(?pathNameSource), \"(/[a-zA-Z0-9-_ :.~$]+)+\", \"$1\"), \"/\"), xsd:string) AS ?filename)" + //extract filename from path
                "       FILTER ( contains(str(?filename), \" copy\")) . " +
                "       BIND ( STRAFTER(?filename, \" copy.\") AS ?fileEnding) . " + //extract file ending
                "       BIND ( STRBEFORE(?filename, \" copy.\") AS ?originalName) . " + //extract filename from original file
                "       BIND ( STRBEFORE(?pathNameSource, ?fileNameSource) AS ?path) . " + //extract directory
                "       BIND ( CONCAT(?originalName, \".\", ?fileEnding) AS ?originalFilename) . " + //extract directory
                "       BIND ( CONCAT(?path, ?originalName, \".\", ?fileEnding) AS ?originalPathname) . " + //extract directory
                "       ?event      fae:hasTargetFile ?targetFile . " +
                "       ?targetFile fae:pathName      ?pathNameTarget . " +
                "       ?targetFile fae:fileName      ?fileNameTarget . " +
                "       ?event      fae:hasUser       ?user . " +
                "       ?user       fae:userName      ?username . " +
                "       ?event      fae:hasProgram    ?program . " +
                "       ?program    fae:programName   ?programName . " +
                "       ?program    fae:pid           ?pid . " +
                "       ?event      fae:hasSourceHost ?sourceHost . " +
                "       ?sourceHost fae:hostName      ?sourceHostName . " +
                "       ?event      fae:hasTargetHost ?targetHost . " +
                "       ?targetHost fae:hostName      ?targetHostName . " +
                "       BIND (StrUUID() AS ?strUuid) . " +
                "       BIND (?strUuid AS ?id) . " +
                "       BIND(URI(CONCAT(STR( fae: ), \"-\", ?strUuid)) AS ?copyEvent ) . " +
                "       BIND(URI(CONCAT(STR( fae:Action ), \"-\", ?strUuid)) AS ?copyEventAction ) . " +
                "       BIND(URI(CONCAT(STR( fae:User ), \"-\", ?strUuid)) AS ?copyEventUser ) . " +
                "       BIND(URI(CONCAT(STR( fae:Program ), \"-\", ?strUuid)) AS ?copyEventProgram ) . " +
                "       BIND(URI(CONCAT(STR( fae:SourceFile ), \"-\", ?strUuid)) AS ?copyEventSourceFile ) . " +
                "       BIND(URI(CONCAT(STR( fae:TargetFile ), \"-\", ?strUuid)) AS ?copyEventTargetFile ) . " +
                "       BIND(URI(CONCAT(STR( fae:SourceHost ), \"-\", ?strUuid)) AS ?copyEventSourceHost ) . " +
                "       BIND(URI(CONCAT(STR( fae:TargetHost ), \"-\", ?strUuid)) AS ?copyEventTargetHost ) . " +
                "} GROUP BY ?timestamp ?copyEventTimestamp ?id ?copyEventAction ?copyEventUser  ?username ?copyEventProgram " +
                "                ?programName ?pid ?copyEventSourceFile ?originalFilename ?originalPathname ?copyEventTargetFile " +
                "                 ?fileNameSource ?pathNameSource ?copyEventSourceHost ?sourceHostName ?copyEventTargetHost " +
                "                 ?targetHostName ?copyEvent "+
                "}";
    }

    public static String constructCopyDiffDir() {
            return "PREFIX fae: <http://w3id.org/sepses/vocab/event/fileAccess#> " +
                    "PREFIX file: <http://w3id.org/sepses/vocab/fileSystemLog#> " +
                    "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                    "CONSTRUCT { " +
                    "            ?copyEventRes fae:id             ?Id . " +
                    "            ?copyEventRes fae:timestamp      ?timestamp . " +
                    "            ?copyEventRes fae:hasAction      ?action . " +
                    "            ?action       fae:actionName     \"Created_Copied\" . " +
                    "            ?copyEventRes fae:hasUser        ?user . " +
                    "            ?user         fae:userName       ?usernameLog . " +
                    "            ?copyEventRes fae:hasProgram     ?program . " +
                    "            ?program      fae:programName    ?programName . " +
                    "            ?program      fae:pid            ?pid . " +
                    "            ?copyEventRes fae:hasSourceFile  ?sourceFile . " +
                    "            ?sourceFile   fae:fileName       ?filenameLog . " +
                    "            ?sourceFile   fae:pathName       ?temp . " +
                    "            ?copyEventRes fae:hasTargetFile  ?targetFile . " +
                    "            ?targetFile   fae:fileName       ?filenameLog . " +
                    "            ?targetFile   fae:pathName       ?pathNameSource . " +
                    "            ?copyEventRes fae:hasSourceHost  ?sourceHost . " +
                    "            ?sourceHost   fae:hostName       ?sourceHostName . " +
                    "            ?copyEventRes fae:hasTargetHost  ?targetHost . " +
                    "            ?targetHost   fae:hostName       ?targetHostName . " +
                    " } " +
                    "WHERE { " +
                    "SELECT distinct (sample(?id) as ?Id) (sample(?copyEvent) as ?copyEventRes) (sample(?copyEventAction) as ?action) " +
                    "                (sample(?copyEventUser) as ?user) (sample(?copyEventProgram) as ?program) " +
                    "                (sample(?copyEventSourceFile) as ?sourceFile) (sample(?copyEventTargetFile) as ?targetFile) " +
                    "                (sample(?copyEventSourceHost) as ?sourceHost) (sample(?copyEventTargetHost) as ?targetHost)" +
                    "                ?timestamp ?usernameLog ?programName ?pid ?filenameLog ?temp ?pathNameSource ?sourceHostName ?targetHostName " +
                    "WHERE {" +
                    "       ?logEntry      file:timestamp     ?timestampLog . " +
                    "       BIND (year(?timestampLog) AS ?yearLog) . " + // bind year, month, day, hour, minute and seconds to variables of log timestamp
                    "       BIND (month(?timestampLog) AS ?monthLog) . " +
                    "       BIND (day(?timestampLog) AS ?dayLog) . " +
                    "       BIND (hours(?timestampLog) AS ?hoursLog) . " +
                    "       BIND (minutes(?timestampLog) AS ?minutesLog) . " +
                    "       BIND (seconds(?timestampLog) AS ?secondsLog) . " +
                    "       ?logEntry      file:accessCall   \"getattrlist()\" . " +
                    "       ?logEntry      file:hasFile      ?fileLog . " +
                    "       ?fileLog       file:pathname     ?pathnameLog . " +
                    "       ?fileLog       file:md5CheckSum  ?md5CheckSumLog . " +
                    "       ?logEntry      file:hasUser      ?userLog . " +
                    "       ?userLog      file:username      ?usernameLog . " +
                    "       BIND ( STRAFTER(str(?pathnameLog),\",\") AS ?temp ) . " +
                    "       BIND ( STRAFTER(str(?md5CheckSumLog),\",\") AS ?tempChecksumLog ) . " +
                    "       BIND ( STRAFTER(REPLACE(?temp, \"(/[a-zA-Z0-9-_ :.~$]+)+\", \"$1\"), \"/\") AS ?filenameLog)" + //extract filename from path logs
                    "       BIND ( STRBEFORE(str(?temp), ?filenameLog) AS ?directoryLog)" +

                    "       ?event      fae:timestamp     ?timestamp . " +
                    "       BIND (year(?timestamp) AS ?yearEvent) . " + // bind year, month, day, hour, minute and seconds to variables of event timestamp
                    "       BIND (month(?timestamp) AS ?monthEvent) . " +
                    "       BIND (day(?timestamp) AS ?dayEvent) . " +
                    "       BIND (hours(?timestamp) AS ?hoursEvent) . " +
                    "       BIND (minutes(?timestamp) AS ?minutesEvent) . " +
                    "       BIND (seconds(?timestamp) AS ?secondsEvent) . " +
                    "       BIND (IF((seconds(?timestamp) >= 10), (seconds(?timestamp) - 10), 0)  AS ?secondsEventMinus10) . " +
                    "       BIND (IF((seconds(?timestamp) < 10) && (minutes(?timestamp) >= 1), (minutes(?timestamp) - 1), ?minutesEvent)  AS ?minutesEventMinus1) . " +
                    "       ?event           fae:hasAction     ?eventAction . " +
                    "       ?eventAction     fae:actionName    ?actionName . " +
                    "       FILTER ( (str(?actionName) = \"Created\") || (str(?actionName) = \"Created_Modified\")) . "+
                    "       ?event           fae:hasSourceFile ?EventSourceFile . " +
                    "       ?EventSourceFile fae:md5CheckSum   ?md5CheckSumSourceFile . " +
                    "       ?EventSourceFile fae:pathName      ?pathNameSource . " +
                    "       ?EventSourceFile fae:fileName      ?fileNameSource . " +
                    "       BIND ( STRBEFORE(str(?pathNameSource), ?fileNameSource) AS ?directoryEvent)" +

                    "       FILTER REGEX( ?pathNameSource , \"([a-zA-Z0-9-_ :.~$]+[.][a-zA-Z0-9-_ :.~$]+)+\" ) . " + //only valid filenames should be considered and filtered for
                    //"       FILTER ( ?timestampLog <= ?timestamp ) . " +
                    "       FILTER ( ?timestampLog < ?timestamp ) . " +
                    "       FILTER ( (?yearLog=?yearEvent) && (?monthLog=?monthEvent) && (?dayLog=?dayEvent) " +
                    "            && (?hoursLog=?hoursEvent) && (?minutesLog >= ?minutesEventMinus1) && (?secondsLog >= ?secondsEventMinus10) ) . " +
                    "       FILTER ( ?filenameLog = ?fileNameSource ) . " +   // filename has to be the same
                    "       FILTER ( ?directoryLog != ?directoryEvent ) . " + // directory has to be different

                    // Filter for same MD5 Checksum of source (=log entry) and target (=modify event sourcefile) file of copy event
                    "       FILTER ( ?tempChecksumLog = ?md5CheckSumSourceFile ) . " +

                    "       ?event           fae:hasUser       ?EventUser . " +
                    "       ?EventUser       fae:userName      ?username . " +
                    "       FILTER ( ?usernameLog = ?username ) . " + //make sure access and create was preformed by same user
                    "       ?event           fae:hasProgram    ?EventProgram . " +
                    "       ?EventProgram    fae:programName   ?programName . " +
                    "       ?EventProgram    fae:pid           ?pid . " +
                    "       ?event           fae:hasSourceHost ?EventSourceHost . " +
                    "       ?EventSourceHost fae:hostName      ?sourceHostName . " +
                    "       ?event           fae:hasTargetHost ?EventTargetHost . " +
                    "       ?EventTargetHost fae:hostName      ?targetHostName . " +

                    // filter out move events which have the same target and source file
                 /*   " OPTIONAL{      ?eventMoved           fae:hasAction     ?eventMovedAction . " +
                    "       ?eventMovedAction     fae:actionName    \"Moved\" . " +
                    "       ?eventMoved           fae:hasSourceFile ?eventMovedSourceFile . " +
                    "       ?eventMovedSourceFile fae:pathName      ?eventMovedPathNameSource . " +
                    "       ?eventMoved           fae:hasTargetFile ?eventMovedTargetFile . " +
                    "       ?eventMovedTargetFile fae:pathName      ?eventMovedPathNameTarget . " +
                    "       FILTER ( !CONTAINS(?eventMovedPathNameSource, ?temp) && !CONTAINS(?eventMovedPathNameTarget, ?temp)) . " + //path of copy event must not be part of source or target path of a Moved event
                    "       FILTER ( !CONTAINS(?eventMovedPathNameSource, ?pathNameSource) && !CONTAINS(?eventMovedPathNameTarget, ?pathNameSource)) . " +
                    "}" +*/

                    "       BIND (StrUUID() AS ?id) . " +
                    "       BIND(URI(CONCAT(STR( fae: ), \"-\", ?id)) AS ?copyEvent ) . " +
                    "       BIND(URI(CONCAT(STR( fae:Action ), \"-\", ?id)) AS ?copyEventAction ) . " +
                    "       BIND(URI(CONCAT(STR( fae:User ), \"-\", ?id)) AS ?copyEventUser ) . " +
                    "       BIND(URI(CONCAT(STR( fae:Program ), \"-\", ?id)) AS ?copyEventProgram ) . " +
                    "       BIND(URI(CONCAT(STR( fae:SourceFile ), \"-\", ?id)) AS ?copyEventSourceFile ) . " +
                    "       BIND(URI(CONCAT(STR( fae:TargetFile ), \"-\", ?id)) AS ?copyEventTargetFile ) . " +
                    "       BIND(URI(CONCAT(STR( fae:SourceHost ), \"-\", ?id)) AS ?copyEventSourceHost ) . " +
                    "       BIND(URI(CONCAT(STR( fae:TargetHost ), \"-\", ?id)) AS ?copyEventTargetHost ) . " +

                    "      } GROUP BY ?timestamp ?usernameLog ?programName ?pid ?filenameLog ?temp ?pathNameSource ?sourceHostName ?targetHostName " +
                    "}";
    }

    public static String constructCopyDiffDir(String timeThreshold) {
        return " " +
                "PREFIX file: <http://w3id.org/sepses/vocab/fileSystemLog#> " +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                "CONSTRUCT { " +
                "            ?copyEventRes fae:id             ?Id . " +
                "            ?copyEventRes fae:timestamp      ?timestamp . " +
                "            ?copyEventRes fae:hasAction      ?action . " +
                "            ?action       fae:actionName     \"Created_Copied\" . " +
                "            ?copyEventRes fae:hasUser        ?user . " +
                "            ?user         fae:userName       ?usernameLog . " +
                "            ?copyEventRes fae:hasProgram     ?program . " +
                "            ?program      fae:programName    ?programName . " +
                "            ?program      fae:pid            ?pid . " +
                "            ?copyEventRes fae:hasSourceFile  ?sourceFile . " +
                "            ?sourceFile   fae:fileName       ?filenameLog . " +
                "            ?sourceFile   fae:pathName       ?temp . " +
                "            ?copyEventRes fae:hasTargetFile  ?targetFile . " +
                "            ?targetFile   fae:fileName       ?filenameLog . " +
                "            ?targetFile   fae:pathName       ?pathNameSource . " +
                "            ?copyEventRes fae:hasSourceHost  ?sourceHost . " +
                "            ?sourceHost   fae:hostName       ?sourceHostName . " +
                "            ?copyEventRes fae:hasTargetHost  ?targetHost . " +
                "            ?targetHost   fae:hostName       ?targetHostName . " +
                " } " +
                "WHERE { " +
                "SELECT distinct (sample(?id) as ?Id) (sample(?copyEvent) as ?copyEventRes) (sample(?copyEventAction) as ?action) " +
                "                (sample(?copyEventUser) as ?user) (sample(?copyEventProgram) as ?program) " +
                "                (sample(?copyEventSourceFile) as ?sourceFile) (sample(?copyEventTargetFile) as ?targetFile) " +
                "                (sample(?copyEventSourceHost) as ?sourceHost) (sample(?copyEventTargetHost) as ?targetHost)" +
                "                ?timestamp ?usernameLog ?programName ?pid ?filenameLog ?temp ?pathNameSource ?sourceHostName ?targetHostName " +
                "WHERE {" +
                "       ?logEntry      file:timestamp     ?timestampLog . " +
                "       BIND (year(?timestampLog) AS ?yearLog) . " + // bind year, month, day, hour, minute and seconds to variables of log timestamp
                "       BIND (month(?timestampLog) AS ?monthLog) . " +
                "       BIND (day(?timestampLog) AS ?dayLog) . " +
                "       BIND (hours(?timestampLog) AS ?hoursLog) . " +
                "       BIND (minutes(?timestampLog) AS ?minutesLog) . " +
                "       BIND (seconds(?timestampLog) AS ?secondsLog) . " +
                "       ?logEntry      file:accessCall   \"getattrlist()\" . " +
                "       ?logEntry      file:hasFile      ?fileLog . " +
                "       ?fileLog       file:pathname     ?pathnameLog . " +
                "       ?logEntry      file:hasUser      ?userLog . " +
                "       ?userLog      file:username      ?usernameLog . " +
                "       BIND ( STRAFTER(str(?pathnameLog),\",\") AS ?temp ) . " +
                "       BIND ( STRAFTER(REPLACE(?temp, \"(/[a-zA-Z0-9-_ :.~$]+)+\", \"$1\"), \"/\") AS ?filenameLog)" + //extract filename from path logs
                "       BIND ( STRBEFORE(str(?temp), ?filenameLog) AS ?directoryLog)" +

                "       ?event      fae:timestamp     ?timestamp . " +
                "       FILTER ( ?timestamp >= \"" + timeThreshold + "\"^^<http://www.w3.org/2001/XMLSchema#dateTime>) . " +
                "       BIND (year(?timestamp) AS ?yearEvent) . " + // bind year, month, day, hour, minute and seconds to variables of event timestamp
                "       BIND (month(?timestamp) AS ?monthEvent) . " +
                "       BIND (day(?timestamp) AS ?dayEvent) . " +
                "       BIND (hours(?timestamp) AS ?hoursEvent) . " +
                "       BIND (minutes(?timestamp) AS ?minutesEvent) . " +
                "       BIND (seconds(?timestamp) AS ?secondsEvent) . " +
                "       BIND (IF((seconds(?timestamp) >= 10), (seconds(?timestamp) - 10), 0)  AS ?secondsEventMinus10) . " +
                "       BIND (IF((seconds(?timestamp) < 10) && (minutes(?timestamp) >= 1), (minutes(?timestamp) - 1), ?minutesEvent)  AS ?minutesEventMinus1) . " +
                "       ?event           fae:hasAction     ?eventAction . " +
                "       ?eventAction     fae:actionName    \"Created_Modified\" . " +
                "       ?event           fae:hasSourceFile ?EventSourceFile . " +
                "       ?EventSourceFile fae:pathName      ?pathNameSource . " +
                "       ?EventSourceFile fae:fileName      ?fileNameSource . " +
                "       BIND ( STRBEFORE(str(?pathNameSource), ?fileNameSource) AS ?directoryEvent)" +

                "       FILTER REGEX( ?pathNameSource , \"([a-zA-Z0-9-_ :.~$]+[.][a-zA-Z0-9-_ :.~$]+)+\" ) . " + //only valid filenames should be considered and filtered for
               // "       FILTER ( ?timestampLog <= ?timestamp ) . " +
                "       FILTER ( ?timestampLog < ?timestamp ) . " +
                "       FILTER ( (?yearLog=?yearEvent) && (?monthLog=?monthEvent) && (?dayLog=?dayEvent) " +
                "            && (?hoursLog=?hoursEvent) && (?minutesLog >= ?minutesEventMinus1) && (?secondsLog >= ?secondsEventMinus10) ) . " +
                "       FILTER ( ?filenameLog = ?fileNameSource ) . " +
                "       FILTER ( ?directoryLog != ?directoryEvent ) . " +

                "       ?event           fae:hasUser       ?EventUser . " +
                "       ?EventUser       fae:userName      ?username . " +
                "       FILTER ( ?usernameLog = ?username ) . " + //make sure access and create was preformed by same user
                "       ?event           fae:hasProgram    ?EventProgram . " +
                "       ?EventProgram    fae:programName   ?programName . " +
                "       ?EventProgram    fae:pid           ?pid . " +
                "       ?event           fae:hasSourceHost ?EventSourceHost . " +
                "       ?EventSourceHost fae:hostName      ?sourceHostName . " +
                "       ?event           fae:hasTargetHost ?EventTargetHost . " +
                "       ?EventTargetHost fae:hostName      ?targetHostName . " +

                // filter out move events which have the same target and source file
                "       ?eventMoved           fae:hasAction     ?eventMovedAction . " +
                "       ?eventMovedAction     fae:actionName    \"Moved\" . " +
                "       ?eventMoved           fae:hasSourceFile ?eventMovedSourceFile . " +
                "       ?eventMovedSourceFile fae:pathName      ?eventMovedPathNameSource . " +
                "       ?eventMoved           fae:hasTargetFile ?eventMovedTargetFile . " +
                "       ?eventMovedTargetFile fae:pathName      ?eventMovedPathNameTarget . " +
                "       FILTER ( !CONTAINS(?eventMovedPathNameSource, ?temp) && !CONTAINS(?eventMovedPathNameTarget, ?temp)) . " + //path of copy event must not be part of source or target path of a Moved event
                "       FILTER ( !CONTAINS(?eventMovedPathNameSource, ?pathNameSource) && !CONTAINS(?eventMovedPathNameTarget, ?pathNameSource)) . " +

                "       BIND (StrUUID() AS ?id) . " +
                "       BIND(URI(CONCAT(STR( fae: ), \"-\", ?id)) AS ?copyEvent ) . " +
                "       BIND(URI(CONCAT(STR( fae:Action ), \"-\", ?id)) AS ?copyEventAction ) . " +
                "       BIND(URI(CONCAT(STR( fae:User ), \"-\", ?id)) AS ?copyEventUser ) . " +
                "       BIND(URI(CONCAT(STR( fae:Program ), \"-\", ?id)) AS ?copyEventProgram ) . " +
                "       BIND(URI(CONCAT(STR( fae:SourceFile ), \"-\", ?id)) AS ?copyEventSourceFile ) . " +
                "       BIND(URI(CONCAT(STR( fae:TargetFile ), \"-\", ?id)) AS ?copyEventTargetFile ) . " +
                "       BIND(URI(CONCAT(STR( fae:SourceHost ), \"-\", ?id)) AS ?copyEventSourceHost ) . " +
                "       BIND(URI(CONCAT(STR( fae:TargetHost ), \"-\", ?id)) AS ?copyEventTargetHost ) . " +

                "      } GROUP BY ?timestamp ?usernameLog ?programName ?pid ?filenameLog ?temp ?pathNameSource ?sourceHostName ?targetHostName " +
                "}";
    }

    public static String equalEvent(String timestamp, String actionName, String pathNameSource, String pathNameTarget, String username,
                                    String sourceHostName, String targetHostName, String pid){
        return "PREFIX fae: <http://w3id.org/sepses/vocab/event/fileAccess#> " +
                "SELECT ?id " +
                "WHERE { " +
                "       ?event      fae:id     ?id . " +
                "       ?event      fae:timestamp     \""+timestamp+"\"^^<http://www.w3.org/2001/XMLSchema#dateTime> . " +
                "       ?event      fae:hasAction     ?action . " +
                "       ?action     fae:actionName    \""+actionName+"\" . " +
                "       ?event      fae:hasSourceFile ?sourceFile . " +
                "       ?sourceFile fae:pathName      \""+pathNameSource+"\" . " +
                "       ?event      fae:hasTargetFile ?targetFile . " +
                "       ?targetFile fae:pathName      \""+pathNameTarget+"\" . " +
                "       ?event      fae:hasUser       ?user . "+
                "       ?user       fae:userName      \""+username+"\" . "+
                "       ?event      fae:hasSourceHost ?sourceHost . "+
                "       ?sourceHost fae:hostName      \""+sourceHostName+"\" . "+
                "       ?event      fae:hasTargetHost ?targetHost . "+
                "       ?targetHost fae:hostName      \""+targetHostName+"\" . "+
                "       ?event      fae:hasProgram    ?program . "+
                "       ?program    fae:pid           \""+pid+"\" . "+
                " }";
    }
}
