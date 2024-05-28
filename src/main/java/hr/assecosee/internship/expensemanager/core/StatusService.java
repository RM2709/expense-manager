package hr.assecosee.internship.expensemanager.core;

import hr.assecosee.internship.expensemanager.ExpenseManagerApplication;
import hr.assecosee.internship.expensemanager.dto.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;


/**
 * Business logic concerning the status of the application.
 */
@Service
public class StatusService {
    private static final Logger logger = LogManager.getLogger(ExpenseManagerApplication.class);

    /**
     * Returns the status of the application if it's working.
     *
     * @return Status code and a message describing the outcome of the operation.
     */
    public Response status(){
        logger.info("Method status called. Application running normally.");
        return new Response(0, "No error!");
    }
}
