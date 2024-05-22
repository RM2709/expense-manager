package hr.assecosee.internship.expensemanager.core.exception;

import hr.assecosee.internship.expensemanager.dto.Response;

public class ExpenseManagerException extends Exception{

    Response response;

    public ExpenseManagerException(Integer code, String message) {
        this.response = new Response(code, message);
    }

    public Response getResponse() {
        return response;
    }
}
