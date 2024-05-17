package hr.assecosee.internship.expensemanager.rest;

import hr.assecosee.internship.expensemanager.core.ExpenseService;
import hr.assecosee.internship.expensemanager.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Operation(operationId = "get", summary = "Retrieve an expense.", description = "Retrieves an existing expense from the database. Response contains a status code, a message describing the outcome of the operation and the retrieved expense's data (id, user name, category name, description, amount, time).")
    public ResponseEntity<Dto> getExpense(@Parameter(description = "ID of the category to be retrieved", required = true) @PathParam("expenseId") Integer expenseId) {
        return ResponseEntity.ok(expenseService.getExpense(expenseId));
    }

    @PostMapping("")
    @Operation(summary = "Create a new expense.", description = "Creates a new expense and inserts it into the database. Response contains a status code, a message describing the outcome of the operation and the newly created expense's data (id, user name, category name, description, amount, time).")
    public ResponseEntity<Dto> createExpense(@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "User id, category id, description, amount, and time of the expense to be created.", content = @Content(schema =@Schema(implementation = ExpenseInfoDto.class))) @RequestBody ExpenseInfoDto expenseInfo){
        return ResponseEntity.ok(expenseService.createExpense(expenseInfo));
    }

    @PutMapping(value = "/", params = "expenseId")
    @Operation(summary = "Update an existing expense.", description = "Updates an existing expense in the database. Response contains a status code, a message describing the outcome of the operation and the updated expense's data (id, user name, category name, description, amount, time).")
    public ResponseEntity<Dto> updateExpense(@Parameter(description = "ID of the expense to be updated", required = true) @PathParam("expenseId") Integer expenseId, @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "User id, category id, description, amount, and time of the expense to be updated.", content = @Content(schema =@Schema(implementation = ExpenseInfoDto.class))) @RequestBody ExpenseInfoDto expenseInfo){
        return ResponseEntity.ok(expenseService.updateExpense(expenseId, expenseInfo));
    }

    @DeleteMapping(value = "/", params = "expenseId")
    @Operation(summary = "Delete an existing expense.", description = "Deletes an existing expense from the database. Response contains a status code and message describing the outcome of the operation.")
    public ResponseEntity<Dto> deleteExpense(@Parameter(description = "ID of the category to be deleted", required = true) @PathParam("expenseId") Integer expenseId){
        return ResponseEntity.ok((expenseService.deleteExpense(expenseId)));
    }

    @GetMapping(value = "/", params = "userId")
    @Operation(operationId = "getByUser", summary = "Retrieve all expenses from a user.", description = "Retrieves all the expenses incurred by a user. Response contains a status code, a message describing the outcome of the operation, information on the selected user (id, name, email), and information on all the expenses incurred by said user (category name, description, amount, time).")
    public ResponseEntity<Dto> getExpensesByUser(@Parameter(description = "ID of the user whose expenses to retrieve", required = true) @PathParam("userId") Integer userId){
        return ResponseEntity.ok(expenseService.getExpensesByUser(userId));
    }

    @GetMapping(value = "/", params = "categoryId")
    @Operation(operationId = "getByCategory", summary = "Retrieve all expenses from a category.", description = "Retrieves all the expenses belonging to a category. Response contains a status code, a message describing the outcome of the operation, information on the selected category (id, name, description), and information on all the expenses belonging to said category (id, user name, description, amount, time).")
    public ResponseEntity<Dto> getExpensesByCategory(@Parameter(description = "ID of the category whose expenses to retrieve", required = true) @PathParam("categoryId") Integer categoryId){
        return ResponseEntity.ok(expenseService.getExpensesByCategory(categoryId));
    }

    @PostMapping("/time")
    @Operation(summary = "Retrieve all expenses made in a specific time frame.", description = "Retrieves all the expenses made in a specific time frame, aka. between two moments in time. Response contains a status code, a message describing the outcome of the operation, and information on all the expenses made in the selected timeframe (id, user name, category name, description, amount, time).")
    public ResponseEntity<Dto> getExpensesByTimeframe(@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Two times (from and to) in between which the expenses were made.", content = @Content(schema =@Schema(implementation = TimeframeDto.class))) @RequestBody TimeframeDto timeframeDto){
        return ResponseEntity.ok(expenseService.getExpensesByTimeframe(timeframeDto));
    }

}

