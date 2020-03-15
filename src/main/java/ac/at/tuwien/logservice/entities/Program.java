package ac.at.tuwien.logservice.entities;

public class Program {

    private String programName;
    private String pid;

    public Program() {
    }

    public Program(String pid){
        this.pid = pid;
    }

    public Program(String programName, String pid) {
        this.programName = programName;
        this.pid = pid;
    }

    public String getProgramName() {
        return programName;
    }

    public String getPid() {
        return pid;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    @Override
    public String toString() {
        return "Program{" +
                "pid='" + pid + '\'' +
                '}';
    }
}
