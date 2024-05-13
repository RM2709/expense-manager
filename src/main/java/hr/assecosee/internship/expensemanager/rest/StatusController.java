package hr.assecosee.internship.expensemanager.rest;


import hr.assecosee.internship.expensemanager.core.StatusService;
import hr.assecosee.internship.expensemanager.dto.StatusDto;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j
public class StatusController {

    private final StatusService statusService;

    @Autowired
    public StatusController(StatusService statusService){
        this.statusService = statusService;
    }

    @GetMapping("/status")
    public ResponseEntity<StatusDto> getStatus() {
        return statusService.status();
    }

}
