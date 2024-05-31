package hr.assecosee.internship.expensemanager.core;


import hr.assecosee.internship.expensemanager.dto.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatusServiceTest {

    @Test
    void getStatus(){
        Response desiredResult = new Response(0, "No error!");
        StatusService statusService = new StatusService();
        assertEquals(desiredResult, statusService.status(), "Application not running");
    }
}
