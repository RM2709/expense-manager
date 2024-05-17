package hr.assecosee.internship.expensemanager.rest;


import hr.assecosee.internship.expensemanager.core.StatusService;
import hr.assecosee.internship.expensemanager.dto.Dto;
import hr.assecosee.internship.expensemanager.dto.StatusDto;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Check application status.", description = "Checks if the application is running properly or not. Response will contain the status code '0' and the message 'No error!' if it is running properly.")
    public ResponseEntity<Dto> getStatus() {
        return ResponseEntity.ok(statusService.status());
    }

}
