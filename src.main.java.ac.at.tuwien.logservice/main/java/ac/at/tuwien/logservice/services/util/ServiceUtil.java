package ac.at.tuwien.logservice.services.util;

import ac.at.tuwien.logservice.entities.FileAccessEvent;
import ac.at.tuwien.logservice.entities.schema.File_access_events;
import com.hp.hpl.jena.datatypes.xsd.XSDDateTime;
import eu.larkc.csparql.common.RDFTuple;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.util.DateParseException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ServiceUtil {

    private final static Logger logger = Logger.getLogger(ServiceUtil.class.getName());

    public static String extractIdFromUrL(String uri) {
        String id = "";
        Pattern regex = Pattern.compile("\\b[0-9a-f]{8}\\b-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-\\b[0-9a-f]{12}\\b");
        Matcher matcher = regex.matcher(uri);
        while (matcher.find()) {
            String pattern = matcher.group(0);
            id = pattern;
        }
        return id;
    }

    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    public static String generateMD5DigestCheckSumOfFile(String pathname) throws NoSuchFileException {
        try (InputStream is = Files.newInputStream(Paths.get(pathname))) {
            return DigestUtils.md5Hex(is);
        } catch (IOException e) {
            //log.warn("error creating md5 checksum of file: " + e.getMessage());
        } catch (Exception e) {
            throw new NoSuchFileException("File does not exist: " + e);
        }
        return null;
    }

    public static String[] identifyTuple(RDFTuple t, String uuid) {
        String subject = t.get(0);
        String predicate = t.get(1);
        String object = t.get(2);
        String[] fields = new String[3];
        if (predicate.equals(File_access_events.hasSourceHost.getURI())) {
            fields[0] = File_access_events.getURI() + uuid;
            fields[1] = predicate;
            fields[2] = File_access_events.getURI() + uuid + "-SourceHost";
        }
        if (predicate.equals(File_access_events.hasTargetHost.getURI())) {
            fields[0] = File_access_events.getURI() + uuid;
            fields[1] = predicate;
            fields[2] = File_access_events.getURI() + uuid + "-TargetHost";
        }
        if (predicate.equals(File_access_events.hasSourceFile.getURI())) {
            fields[0] = File_access_events.getURI() + uuid;
            fields[1] = predicate;
            fields[2] = File_access_events.getURI() + uuid + "-SourceFile";
        }
        if (predicate.equals(File_access_events.hasTargetFile.getURI())) {
            fields[0] = File_access_events.getURI() + uuid;
            fields[1] = predicate;
            fields[2] = File_access_events.getURI() + uuid + "-TargetFile";
        }

        if (subject.equals(File_access_events.SourceFile.getURI()) &&
                (predicate.equals(File_access_events.directory.getURI()) ||
                        predicate.equals(File_access_events.fileName.getURI()) ||
                        predicate.equals(File_access_events.pathName.getURI()) ||
                        (predicate.equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#type") &&
                                object.equals(File_access_events.File.getURI())))) {
            fields[0] = File_access_events.getURI() + uuid + "-SourceFile";
            fields[1] = predicate;
            fields[2] = object;
        }
        if (subject.equals(File_access_events.TargetFile.getURI()) &&
                (predicate.equals(File_access_events.directory.getURI()) ||
                        predicate.equals(File_access_events.fileName.getURI()) ||
                        predicate.equals(File_access_events.pathName.getURI()) ||
                        (predicate.equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#type") &&
                                object.equals(File_access_events.File.getURI())))) {
            fields[0] = File_access_events.getURI() + uuid + "-TargetFile";
            fields[1] = predicate;
            fields[2] = object;
        }

        if (subject.equals(File_access_events.SourceHost.getURI()) &&
                predicate.equals(File_access_events.hostName.getURI()) ||
                predicate.equals(File_access_events.ip4Address.getURI()) ||
                (predicate.equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#type") &&
                        object.equals(File_access_events.Host.getURI()))) {
            fields[0] = File_access_events.getURI() + uuid + "-SourceHost";
            fields[1] = predicate;
            fields[2] = object;
        }
        if (subject.equals(File_access_events.TargetHost.getURI()) &&
                predicate.equals(File_access_events.hostName.getURI()) ||
                predicate.equals(File_access_events.ip4Address.getURI()) ||
                (predicate.equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#type") &&
                        object.equals(File_access_events.Host.getURI()))) {
            fields[0] = File_access_events.getURI() + uuid + "-TargetHost";
            fields[1] = predicate;
            fields[2] = object;
        }
        if (predicate.equals(File_access_events.userName.getURI())
                || (predicate.equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#type") && object.equals(File_access_events.User.getURI()))) {
            fields[0] = File_access_events.getURI() + uuid + "-User";
            fields[1] = predicate;
            fields[2] = object;
        }
        if (predicate.equals(File_access_events.actionName.getURI())) {
            fields[0] = File_access_events.getURI() + uuid + "-Action";
            fields[1] = predicate;
            fields[2] = object;
        }
        if (predicate.equals(File_access_events.pid.getURI())
                || (predicate.equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#type") && object.equals(File_access_events.Program.getURI()))) {
            fields[0] = File_access_events.getURI() + uuid + "-Program";
            fields[1] = predicate;
            fields[2] = object;
        }
        if (predicate.equals(File_access_events.hasUser.getURI())) {
            fields[0] = File_access_events.getURI() + uuid;
            fields[1] = predicate;
            fields[2] = File_access_events.getURI() + uuid + "-User";
        }
        if (predicate.equals(File_access_events.hasAction.getURI())) {
            fields[0] = File_access_events.getURI() + uuid;
            fields[1] = predicate;
            fields[2] = File_access_events.getURI() + uuid + "-Action";
        }
        if (predicate.equals(File_access_events.hasProgram.getURI())) {
            fields[0] = File_access_events.getURI() + uuid;
            fields[1] = predicate;
            fields[2] = File_access_events.getURI() + uuid + "-Program";
        }
        if ((predicate.equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#type") &&
                object.equals(File_access_events.FileAccessEvent.getURI())) ||
                predicate.equals(File_access_events.timestamp.getURI())) {
            fields[0] = File_access_events.getURI() + uuid;
            fields[1] = predicate;
            fields[2] = object;
        }

        if (predicate.equals(File_access_events.id.getURI())) {
            fields[0] = File_access_events.getURI() + uuid;
            fields[1] = predicate;
            fields[2] = uuid;
        }
        if (predicate.equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#type")
                && (object.equals("http://w3id.org/sepses/vocab/event/fileAccess#CreatedModifiedAction") ||
                object.equals("http://w3id.org/sepses/vocab/event/fileAccess#CreatedAction") ||
                object.equals("http://w3id.org/sepses/vocab/event/fileAccess#MovedAction") ||
                object.equals("http://w3id.org/sepses/vocab/event/fileAccess#RenamedAction") ||
                object.equals("http://w3id.org/sepses/vocab/event/fileAccess#CreatedCopiedAction") ||
                object.equals("http://w3id.org/sepses/vocab/event/fileAccess#MovedToRecycleBinAction"))) {
            fields[0] = File_access_events.getURI() + uuid + "-Action";
            fields[1] = predicate;
            fields[2] = object;
        }
        return fields;
    }


    public static XSDDateTime parseStringToXSDDateTime(String timestamp, String format) throws DateParseException {
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = ServiceUtil.parseDate(timestamp, format);
            Date datePlus2Hours = new Date(date.getTime() + (2 * 60 * 60 * 1000)); //add 2 Hours
            calendar.setTime(datePlus2Hours);
        } catch (DateParseException e) {
            throw new DateParseException("Cannot parse date: " + timestamp + ", " + e);
        }
        return new XSDDateTime(calendar);
        // Creation of XSDDateTime Object used hardcoded GMT timezone --> which leads to a modification of date and 2 hours will be substracted
    }

    public static Date parseDate(String timestampLog, String format) throws DateParseException {
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            Date date = dateFormat.parse(timestampLog);
            return date;
        } catch (ParseException e) {
            logger.error("Cannot parse date: " + timestampLog);
        }
        logger.error("Date not parsed (problem with format?)" + timestampLog);
        throw new DateParseException("date cannot be parsed: " + timestampLog);
    }

    public static void setDateTimeOfEvent(FileAccessEvent event) {
        try {
            Date dateTime = ServiceUtil.parseDate(event.getTimestamp(), "yyyy-MM-dd'T'HH:mm:ss'Z'");
            event.setDateTime(dateTime);
        } catch (DateParseException e) {
            e.printStackTrace();
        }
    }

    public static String getTimestampFromXSDDate(String xsdDate) {
        return xsdDate.split("\\^")[0];
    }

    public static String getTimestampFromXSDDate2(String xsdDate) {
        return xsdDate.split("\\^")[0].split("\"")[1];
    }
}