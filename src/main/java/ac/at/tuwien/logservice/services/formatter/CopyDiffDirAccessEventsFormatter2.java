package ac.at.tuwien.logservice.services.formatter;

import ac.at.tuwien.logservice.services.HandleLogService;
import eu.larkc.csparql.common.RDFTable;
import eu.larkc.csparql.core.ResultFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Observable;

@Component
public class CopyDiffDirAccessEventsFormatter2 extends ResultFormatter {

    @Autowired
    private HandleLogService handleLogService;

    @Override
    public void update(Observable o, Object arg) {
        RDFTable q = (RDFTable) arg;
        handleLogService.handleRDFTable(q);
    }
}