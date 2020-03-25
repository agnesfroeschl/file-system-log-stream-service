package ac.at.tuwien.logservice.controller.transfer;

import ac.at.tuwien.logservice.services.util.ServiceUtil;
import org.apache.commons.httpclient.util.DateParseException;

import java.util.Date;

public class Node implements Comparable<Node>{

    private Element time;
    private Element filename;
    private Element fileAccess;
    private Element tfilename;
    private Element program;
    private Element user;
    private Element sourceHost;
    private Element targetHost;

    public Node(Element time, Element filename, Element fileAccess, Element tfilename, Element program, Element user, Element sourceHost, Element targetHost) {
        this.time = time;
        this.filename = filename;
        this.fileAccess = fileAccess;
        this.tfilename = tfilename;
        this.program = program;
        this.user = user;
        this.sourceHost = sourceHost;
        this.targetHost = targetHost;
    }

    public Element getTime() {
        return time;
    }

    public void setTime(Element time) {
        this.time = time;
    }

    public Element getFilename() {
        return filename;
    }

    public void setFilename(Element filename) {
        this.filename = filename;
    }

    public Element getFileAccess() {
        return fileAccess;
    }

    public void setFileAccess(Element fileAccess) {
        this.fileAccess = fileAccess;
    }

    public Element getTfilename() {
        return tfilename;
    }

    public void setTfilename(Element tfilename) {
        this.tfilename = tfilename;
    }

    public Element getProgram() {
        return program;
    }

    public void setProgram(Element program) {
        this.program = program;
    }

    public Element getUser() {
        return user;
    }

    public void setUser(Element user) {
        this.user = user;
    }

    public Element getSourceHost() {
        return sourceHost;
    }

    public void setSourceHost(Element sourceHost) {
        this.sourceHost = sourceHost;
    }

    public Element getTargetHost() {
        return targetHost;
    }

    public void setTargetHost(Element targetHost) {
        this.targetHost = targetHost;
    }

    @Override
    public int compareTo(Node o) {
        int compare = 0;
        try {
            String dateString = ServiceUtil.getTimestampFromXSDDate(this.getTime().getValue());
            Date dateThis = ServiceUtil.parseDate(dateString, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            String dateStringNode = ServiceUtil.getTimestampFromXSDDate(o.getTime().getValue());
            Date dateNode = ServiceUtil.parseDate(dateStringNode, "yyyy-MM-dd'T'HH:mm:ss'Z'");
            compare = dateThis.compareTo(dateNode);
        } catch (DateParseException e) {
            e.printStackTrace();
        }
        return compare;
    }
}
