# file-system-log-stream-service


**Preconditions**
    
   Create folder structure in the root directory of the project:
       
   - tdb/DB_Background
   - tdb/DB_FileAccessEvent
   - tdb/DB_LogEntry
        
---


- **Start Logstash**

   - Download Logstash 6.3.0 from https://www.elastic.co/downloads/logstash
   - add .config files from '/logstash-files'
   - define pipelines.yml - example can be found in '/logstash-files'
   
   to start logstash run:
    
        > cd logstash-6.3.0
        > sudo bin/logstash
           
        
- **Adapt and start TripleWave instance for file system logs**

    - Download TripleWave from GitHub: https://github.com/streamreasoning/TripleWave
    - replace following files by our implementation:
        - 2rml-js/r2rml.js
        - stream/enricher.js
    - add files "logstash.r2rml" and "logstashStream.js" into directory /transform (replace by original files)
        
    Start a TripleWave instance
    
        > cd TripleWave-fileSystemLog
        > sh start.sh
        

- **Run Application**

        mvn spring-boot:run


- **Start Web UI**

        > cd app
        > npm start
        
        
---

        
- **Start client components**

    Start auditing which creates auditd log data:
    
        > sh auditlog.sh
        
    Start reading logs (created logs will be send to server):
        
         > sh readlog.sh