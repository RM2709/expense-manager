package hr.assecosee.internship.expensemanager.core;


import hr.assecosee.internship.expensemanager.dto.Response;
import org.junit.jupiter.api.Test;

public class StatusServiceTest {

    @Test
    public void getStatus(){
        Response desiredResult = new Response(0, "No error!");
        StatusService statusService = new StatusService();
        assert statusService.status().equals(desiredResult) : "Application not running";
    }
}
