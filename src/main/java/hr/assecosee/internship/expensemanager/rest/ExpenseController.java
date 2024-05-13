package hr.assecosee.internship.expensemanager.rest;

import hr.assecosee.internship.expensemanager.core.ExpenseService;
import hr.assecosee.internship.expensemanager.dto.Dto;
import hr.assecosee.internship.expensemanager.dto.StatusDto;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j
@RequestMapping("/expense")
public class ExpenseController {

    private final ExpenseService expenseService;


    @Autowired
    public ExpenseController(ExpenseService expenseService){
        this.expenseService = expenseService;
    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<Dto> getExpense(@PathVariable Integer expenseId) {
        return ResponseEntity.ok(expenseService.getExpense(expenseId));
    }

}

