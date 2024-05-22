package hr.assecosee.internship.expensemanager.core;

import hr.assecosee.internship.expensemanager.dto.Response;
import org.springframework.stereotype.Service;


/**
 * Business logic concerning the status of the application.
 */
@Service
public class StatusService {

    /**
     * Returns the status of the application if it's working.
     *
     * @return Status code and a message describing the outcome of the operation.
     */
    public Response status(){
        return new Response(0, "No error!");
    }
}
