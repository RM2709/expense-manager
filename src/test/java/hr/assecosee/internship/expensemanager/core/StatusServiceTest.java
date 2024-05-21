package hr.assecosee.internship.expensemanager.core;


import hr.assecosee.internship.expensemanager.dto.StatusDto;
import hr.assecosee.internship.expensemanager.dto.StatusWrapper;
import org.junit.jupiter.api.Test;

public class StatusServiceTest {

    @Test
    public void getStatus(){
        StatusWrapper desiredResult = new StatusWrapper(new StatusDto(0, "No error!"));
        StatusService statusService = new StatusService();
        assert statusService.status().equals(desiredResult) : "Application not running";
    }
}
