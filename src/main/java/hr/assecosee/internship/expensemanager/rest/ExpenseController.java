package hr.assecosee.internship.expensemanager.rest;

import hr.assecosee.internship.expensemanager.core.CategoryService;
import hr.assecosee.internship.expensemanager.core.ExpenseService;
import hr.assecosee.internship.expensemanager.core.UserService;
import hr.assecosee.internship.expensemanager.database.entity.Expense;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@Log4j
@RequestMapping("/expense")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public ExpenseController(CategoryService categoryService, UserService userService, ExpenseService expenseService){
        this.categoryService = categoryService;
        this.userService = userService;
        this.expenseService = expenseService;
    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<Object> getExpense(@PathVariable Integer expenseId) {
        Map<String, Object> responseBody = new HashMap<>();
        Map<String, Object> status = new HashMap<>();
        Optional<Expense> expenseOptional = expenseService.findById(expenseId);
        if(expenseOptional.isPresent()){
            responseBody.put("categoryName", categoryService.findById(expenseOptional.get().getCategoryId()).get().getName());
            responseBody.put("userFullName", userService.findById(expenseOptional.get().getUserId()).get().getFirstName() + " " + userService.findById(expenseOptional.get().getUserId()).get().getLastName());
            responseBody.put("description", expenseOptional.get().getDescription());
            responseBody.put("amount", expenseOptional.get().getAmount());
            responseBody.put("time", expenseOptional.get().getTime());
            status.put("code", 0);
            status.put("message", "No error!");
        } else{
            status.put("code", 1);
            status.put("message", "Expense with id="+expenseId+" not found!");
        }
        responseBody.put("status", status);
        return ResponseEntity.ok(responseBody);
    }

}

