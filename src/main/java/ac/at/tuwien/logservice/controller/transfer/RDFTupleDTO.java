package ac.at.tuwien.logservice.controller.transfer;


import eu.larkc.csparql.common.RDFTuple;

import java.util.List;

public class RDFTupleDTO extends RDFTuple {

    private List<String> fields;

    public RDFTupleDTO() {
    }

    public RDFTupleDTO(List<String> fields) {
        this.fields = fields;
    }

    public List<String> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        return "RDFTupleDTO{" +
                "fields=" + fields +
                '}';
    }
}
