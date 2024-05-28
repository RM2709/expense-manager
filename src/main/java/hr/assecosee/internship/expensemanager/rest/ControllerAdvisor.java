package hr.assecosee.internship.expensemanager.rest;

import hr.assecosee.internship.expensemanager.dto.Response;
import hr.assecosee.internship.expensemanager.core.exception.ExpenseManagerException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;


@RestControllerAdvice
@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Response> generalException(Exception exception){
        return ResponseEntity.status(500).body(new Response(500, exception.getMessage()));
    }



    @ExceptionHandler(value = ExpenseManagerException.class)
    public ResponseEntity<Response> expenseManagerException(ExpenseManagerException expenseManagerException){
        return ResponseEntity.ok(expenseManagerException.getResponse());
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<Response> authenticationException(AuthenticationException authenticationException){
        return ResponseEntity.status(401).body(new Response(401, "Client cannot be authenticated! Error message: " + authenticationException.getMessage()));
    }

}
