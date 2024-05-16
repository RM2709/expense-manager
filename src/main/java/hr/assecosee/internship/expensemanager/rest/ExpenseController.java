package hr.assecosee.internship.expensemanager.rest;

import hr.assecosee.internship.expensemanager.core.ExpenseService;
import hr.assecosee.internship.expensemanager.dto.*;
import jakarta.websocket.server.PathParam;
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

    @GetMapping(value = "/", params = "expenseId")
    public ResponseEntity<Dto> getExpense(@PathParam("expenseId") Integer expenseId) {
        return ResponseEntity.ok(expenseService.getExpense(expenseId));
    }

    @PostMapping("")
    public ResponseEntity<Dto> createExpense(@RequestBody ExpenseInfoDto expenseInfo){
        return ResponseEntity.ok(expenseService.createExpense(expenseInfo));
    }

    @PutMapping(value = "/", params = "expenseId")
    public ResponseEntity<Dto> updateExpense(@PathParam("expenseId") Integer expenseId, @RequestBody ExpenseInfoDto expenseInfo){
        return ResponseEntity.ok(expenseService.updateExpense(expenseId, expenseInfo));
    }

    @DeleteMapping(value = "/", params = "expenseId")
    public ResponseEntity<Dto> deleteExpense(@PathParam("expenseId") Integer expenseId){
        return ResponseEntity.ok((expenseService.deleteExpense(expenseId)));
    }

    @GetMapping(value = "/", params = "userId")
    public ResponseEntity<Dto> getExpensesByUser(@PathParam("userId") Integer userId){
        return ResponseEntity.ok(expenseService.getExpensesByUser(userId));
    }

    @GetMapping(value = "/", params = "categoryId")
    public ResponseEntity<Dto> getExpensesByCategory(@PathParam("userId") Integer categoryId){
        return ResponseEntity.ok(expenseService.getExpensesByCategory(categoryId));
    }

    @PostMapping("/time")
    public ResponseEntity<Dto> getExpensesByTimeframe(@RequestBody TimeframeDto timeframeDto){
        return ResponseEntity.ok(expenseService.getExpensesByTimeframe(timeframeDto));
    }

}

