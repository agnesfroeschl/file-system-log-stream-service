package ac.at.tuwien.logservice.entities;

import java.util.List;

public class Tuple {

    private List<String> fields;

    public Tuple() {
    }

    public Tuple(List<String> fields) {
        this.fields = fields;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }
}
