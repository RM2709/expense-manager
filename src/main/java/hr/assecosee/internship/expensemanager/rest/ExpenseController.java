package hr.assecosee.internship.expensemanager.rest;

import hr.assecosee.internship.expensemanager.core.ExpenseService;
import hr.assecosee.internship.expensemanager.database.entity.Expense;
import hr.assecosee.internship.expensemanager.dto.CategoryInfoDto;
import hr.assecosee.internship.expensemanager.dto.Dto;
import hr.assecosee.internship.expensemanager.dto.ExpenseInfoDto;
import hr.assecosee.internship.expensemanager.dto.StatusDto;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("")
    public ResponseEntity<Dto> createExpense(@RequestBody ExpenseInfoDto expenseInfo){
        return ResponseEntity.ok(expenseService.createExpense(expenseInfo));
    }

    @PutMapping("/{expenseId}")
    public ResponseEntity<Dto> updateExpense(@PathVariable Integer expenseId, @RequestBody ExpenseInfoDto expenseInfo){
        return ResponseEntity.ok(expenseService.updateExpense(expenseId, expenseInfo));
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Dto> deleteExpense(@PathVariable Integer expenseId){
        return ResponseEntity.ok((expenseService.deleteExpense(expenseId)));
    }

}

