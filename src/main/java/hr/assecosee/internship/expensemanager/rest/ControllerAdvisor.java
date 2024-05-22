package hr.assecosee.internship.expensemanager.rest;

import hr.assecosee.internship.expensemanager.dto.Response;
import hr.assecosee.internship.expensemanager.core.exception.ExpenseManagerException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Response> generalException(Exception exception){
        return ResponseEntity.status(500).body(new Response(500, exception.getMessage()));
    }

    @ExceptionHandler(value = ExpenseManagerException.class)
    public ResponseEntity<Response> expenseManagerException(ExpenseManagerException expenseManagerException){
        return ResponseEntity.ok(expenseManagerException.getResponse());
    }

}
