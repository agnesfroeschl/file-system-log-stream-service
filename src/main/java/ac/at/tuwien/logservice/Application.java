package ac.at.tuwien.logservice;

import ac.at.tuwien.logservice.services.CSparqlStreamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;
import java.text.ParseException;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class Application {

    @Autowired
    private CSparqlStreamService cSparqlStreamService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {

        boolean success = (new File("tdb/DB_FileAccessEvent")).mkdirs();
        success = (new File("tdb/DB_LogEntry")).mkdirs();
        success = (new File("tdb/DB_Background")).mkdirs();
        if (!success) {
            // Directory creation failed
        }

        // livestream csparql service
        try {
            cSparqlStreamService.initCSparqlEngine();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
