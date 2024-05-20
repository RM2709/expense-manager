package hr.assecosee.internship.expensemanager.core;

import hr.assecosee.internship.expensemanager.dto.Dto;
import hr.assecosee.internship.expensemanager.dto.StatusDto;
import hr.assecosee.internship.expensemanager.dto.StatusWrapper;
import org.springframework.stereotype.Service;


/**
 * Business logic concering the status of the application.
 */
@Service
public class StatusService {

    /**
     * Returns the status of the application if it's working.
     *
     * @return Status code and a message describing the outcome of the operation.
     */
    public Dto status(){
        return new StatusWrapper(new StatusDto(0, "No error!"));
    }
}
