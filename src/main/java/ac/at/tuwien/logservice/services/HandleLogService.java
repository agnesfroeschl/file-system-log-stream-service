package ac.at.tuwien.logservice.services;

import ac.at.tuwien.logservice.entities.Tuple;
import ac.at.tuwien.logservice.entities.schema.File_access_events;
import ac.at.tuwien.logservice.services.util.ServiceUtil;
import eu.larkc.csparql.common.RDFTable;
import eu.larkc.csparql.common.RDFTuple;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.util.DateParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class HandleLogService {

    private static String ipExternal;
    private static String hostName;
    private static Map<String, String> userMap;
    private static final String SERVER_IP = "localhost";
    private static final String SERVER_PORT = "9000";
    private static final String TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    @Autowired
    private LogConverterService logConverterService;

    @Autowired
    public HandleLogService() {
        userMap = new HashMap<>();
    }

    public void handleRDFTable(RDFTable table) {
        Iterator var4 = table.iterator();

        while (var4.hasNext()) {
            RDFTuple t = (RDFTuple) var4.next();
            RDFTuple dto = new RDFTuple();
            String predicate = t.get(1);
            String object = t.get(2);
            String[] fields = new String[]{t.get(0), predicate, object};
            dto.addFields(fields);

            Tuple tuple = new Tuple(Arrays.asList(fields));
            logConverterService.saveRDFTuple(tuple);

            if (predicate.equals(File_access_events.timestamp.getURI())) {

                String currentTime = ServiceUtil.getCurrentTimeStamp();
                String currentTimeXSDate = "\"" + currentTime + "\"^^http://www.w3.org/2001/XMLSchema#dateTime";

                String[] detectedTimeFields = new String[3];
                detectedTimeFields[0] = t.get(0);
                detectedTimeFields[1] = File_access_events.detected.getURI();
                detectedTimeFields[2] = currentTimeXSDate;

                Tuple tupleDetected = new Tuple(Arrays.asList(detectedTimeFields));
                logConverterService.saveRDFTuple(tupleDetected);

                try {
                    String temp = ServiceUtil.getTimestampFromXSDDate2(object);
                    Date before = ServiceUtil.parseDate(temp, TIME_FORMAT);
                    Date after = ServiceUtil.parseDate(currentTime, TIME_FORMAT);
                    String diffSeconds = String.valueOf(getDiffBetweenTimestampsInSeconds(before, after));

                    String[] periodUntilDetectedFields = new String[3];
                    periodUntilDetectedFields[0] = t.get(0);
                    periodUntilDetectedFields[1] = File_access_events.periodUntilDetected.getURI();
                    periodUntilDetectedFields[2] = diffSeconds;

                    Tuple tuplePeriodUntil = new Tuple(Arrays.asList(periodUntilDetectedFields));
                    logConverterService.saveRDFTuple(tuplePeriodUntil);

                } catch (DateParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private long getDiffBetweenTimestampsInSeconds(Date firstDate, Date secondDate){
        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
        long diff = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return diff;
    }

    public void handleLogEventRDFTable(RDFTable table) {
        Iterator var4 = table.iterator();

        while (var4.hasNext()) {
            RDFTuple t = (RDFTuple) var4.next();
            logConverterService.saveLogEntryRDFTuple(t);
        }
    }

    public static void setIpExternal(String ipExternal) {
        HandleLogService.ipExternal = ipExternal;
    }

    public static String getIpExternal() {
        return ipExternal;
    }

    public static void setHostName(String hostname) {
        HandleLogService.hostName = hostname;
    }

    public static String getHostName() {
        return hostName;
    }

    public static Map<String, String> getUserMap() {
        return userMap;
    }

    public static void addUserToMap(String id, String username) {
        if (userMap == null)
            userMap = new HashMap<>();
        HandleLogService.userMap.put(id, username);
    }
}
