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
           
        
- **Start TripleWave instance for file system logs**

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