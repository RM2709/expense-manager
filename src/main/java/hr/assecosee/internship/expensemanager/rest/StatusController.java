package hr.assecosee.internship.expensemanager.rest;


import lombok.extern.log4j.Log4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Log4j
public class StatusController {

    @GetMapping("/status")
    public ResponseEntity<Object> getStatus() {
        Map<String, Object> responseBody = new HashMap<>();
        Map<String, Object> status = new HashMap<>();
        status.put("code", 0);
        status.put("message", "No error!");
        responseBody.put("status", status);
        return ResponseEntity.ok(responseBody);
    }

}
