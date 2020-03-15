package ac.at.tuwien.logservice.controller;

import ac.at.tuwien.logservice.controller.transfer.SearchParam;
import ac.at.tuwien.logservice.services.FileHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class ViewController {

    @Autowired
    private FileHistoryService fileHistoryService;


   @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/search", method = RequestMethod.POST, produces = "application/json")
    public String searchPathname(@RequestBody SearchParam param) {
        System.out.println("search history for: " + param.getPathname());
        String graph = fileHistoryService.getHistoryJson(param.getPathname());
        return graph;
    }

}
