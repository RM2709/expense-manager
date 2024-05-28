package hr.assecosee.internship.expensemanager.rest;

import hr.assecosee.internship.expensemanager.core.ExpenseService;
import hr.assecosee.internship.expensemanager.core.exception.ExpenseManagerException;
import hr.assecosee.internship.expensemanager.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

/**
 * REST mapping concerning expenses.
 */
@RestController
@Log4j
@RequestMapping("/expense")
public class ExpenseController {

    private final ExpenseService expenseService;


    @Autowired
    public ExpenseController(ExpenseService expenseService){
        this.expenseService = expenseService;
    }

    /**
     * Retrieves an expense.
     *
     * @param expenseId ID of the expense to be retrieved.
     * @return JSON object containing a status message and basic information of the retrieved expense (id, user full name, category name, description, amount, time).
     */
    @GetMapping(value = "/{expenseId}")
    @Operation(operationId = "get",
            summary = "Retrieve an expense.",
            description = "Retrieves an existing expense from the database. Response contains a status code, a message describing the outcome " +
                    "of the operation and the retrieved expense's data (id, user name, category name, description, amount, time).")
    public ResponseEntity<ExpenseDto> getExpense(@Parameter(description = "ID of the category to be retrieved", required = true)
                                              @PathVariable("expenseId") Integer expenseId) throws ExpenseManagerException, UnrecoverableKeyException, NoSuchPaddingException, IllegalBlockSizeException, CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, BadPaddingException, InvalidKeyException {
        return ResponseEntity.ok(expenseService.getExpense(expenseId));
    }

    /**
     * Creates a new expense.
     *
     * @param expenseInfo JSON object which contains the user id, category id, description, amount, and time of the expense to be created.
     * @return Status code, a message describing the outcome of the operation, and basic information of the created expense (id, user full name, category name, description, amount, time).
     */
    @PostMapping("")
    @Operation(summary = "Create a new expense.",
            description = "Creates a new expense and inserts it into the database. Response contains a status code, a message describing " +
            "the outcome of the operation and the newly created expense's data (id, user name, category name, description, amount, time).")
    public ResponseEntity<ExpenseDto> createExpense(@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "User id, category id, description, amount, and time of the expense to be created.",
            content = @Content(schema =@Schema(implementation = ExpenseInfoDto.class)))
                                                 @RequestBody ExpenseInfoDto expenseInfo) throws ExpenseManagerException, UnrecoverableKeyException, IllegalBlockSizeException, NoSuchPaddingException, CertificateException, BadPaddingException, NoSuchAlgorithmException, KeyStoreException, IOException, InvalidKeyException {
        return ResponseEntity.ok(expenseService.createExpense(expenseInfo));
    }

    /**
     * Updates an existing expense.
     *
     * @param expenseId ID of the expense to be updated.
     * @param expenseInfo JSON object which contains the user id, category id, description, amount, and time of the expense to be updated.
     * @return Status code, a message describing the outcome of the operation, and basic information of the updated expense (id, user full name, category name, description, amount, time)
     */
    @PutMapping(value = "/{expenseId}")
    @Operation(summary = "Update an existing expense.",
            description = "Updates an existing expense in the database. Response contains a status code, a message describing " +
                    "the outcome of the operation and the updated expense's data (id, user name, category name, description, amount, time).")
    public ResponseEntity<ExpenseDto> updateExpense(@Parameter(description = "ID of the expense to be updated", required = true)
                                                 @PathVariable("expenseId") Integer expenseId, @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            description = "User id, category id, description, amount, and time of the expense to be updated.",
            content = @Content(schema =@Schema(implementation = ExpenseInfoDto.class))) @RequestBody ExpenseInfoDto expenseInfo) throws ExpenseManagerException, UnrecoverableKeyException, IllegalBlockSizeException, NoSuchPaddingException, CertificateException, BadPaddingException, NoSuchAlgorithmException, KeyStoreException, IOException, InvalidKeyException {
        return ResponseEntity.ok(expenseService.updateExpense(expenseId, expenseInfo));
    }

    /**
     * Deletes an existing expense.
     *
     * @param expenseId ID of the expense to be deleted.
     * @return Status code and a message describing the outcome of the operation.
     */
    @DeleteMapping(value = "/{expenseId}")
    @Operation(summary = "Delete an existing expense.",
            description = "Deletes an existing expense from the database. Response contains a status code and message describing the outcome of the operation.")
    public ResponseEntity<Response> deleteExpense(@Parameter(description = "ID of the category to be deleted", required = true)
                                                 @PathVariable("expenseId") Integer expenseId) throws ExpenseManagerException {
        return ResponseEntity.ok((expenseService.deleteExpense(expenseId)));
    }

    /**
     * Retrieves all expenses incurred by a single user.
     *
     * @param userId ID of the user whose expenses are being retrieved.
     * @return Status code, a message describing the outcome of the operation,information on the selected user (id, name, email), and information on all the expenses incurred by said user (category name, description, amount, time)."
     */
    @GetMapping(value = "/user/{userId}")
    @Operation(operationId = "getByUser",
            summary = "Retrieve all expenses from a user.",
            description = "Retrieves all the expenses incurred by a user. Response contains a status code, a message describing the outcome of the operation, " +
                    "information on the selected user (id, name, email), and information on all the expenses incurred by said user (category name, description, amount, time).")
    public ResponseEntity<ExpensesByUserDto> getExpensesByUser(@Parameter(description = "ID of the user whose expenses to retrieve", required = true)
                                                     @PathVariable("userId") Integer userId) throws ExpenseManagerException, UnrecoverableKeyException, NoSuchPaddingException, IllegalBlockSizeException, CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, BadPaddingException, InvalidKeyException {
        return ResponseEntity.ok(expenseService.getExpensesByUser(userId));
    }

    /**
     * Retrieves all expenses belonging to a single category.
     *
     * @param categoryId ID of the category to which the expenses being retrieved belong.
     * @return Status code, a message describing the outcome of the operation, information on the selected category (id, name, description), and information on all the expenses belonging to said category (id, user name, description, amount, time).
     */
    @GetMapping(value = "/category/{categoryId}")
    @Operation(operationId = "getByCategory",
            summary = "Retrieve all expenses from a category.",
            description = "Retrieves all the expenses belonging to a category. Response contains a status code, a message describing the outcome of the operation, " +
                    "information on the selected category (id, name, description), and information on all the expenses belonging to said category (id, user name, description, amount, time).")
    public ResponseEntity<ExpensesByCategoryDto> getExpensesByCategory(@Parameter(description = "ID of the category whose expenses to retrieve", required = true)
                                                         @PathVariable("categoryId") Integer categoryId) throws ExpenseManagerException, UnrecoverableKeyException, NoSuchPaddingException, IllegalBlockSizeException, CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, BadPaddingException, InvalidKeyException {
        return ResponseEntity.ok(expenseService.getExpensesByCategory(categoryId));
    }

    /**
     * Retrieves all expenses incurred between any two points in time.
     *
     * @param timeframeDto JSON object which contains two times in 'yyyy-MM-dd hh:mm:ss' format (from and to) in between which the expenses were made.
     * @return Status code, a message describing the outcome of the operation, and information on all the expenses made in the selected timeframe (id, user full name, category name, description, amount, time).
     */
    @PostMapping("/time")
    @Operation(summary = "Retrieve all expenses made in a specific time frame.", description = "Retrieves all the expenses made in a specific time frame, " +
            "aka. between two moments in time. Response contains a status code, a message describing the outcome of the operation, and information on all the " +
            "expenses made in the selected timeframe (id, user name, category name, description, amount, time).")
    public ResponseEntity<ExpensesByCategoryDto> getExpensesByTimeframe(@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            description = "Two times in 'yyyy-MM-dd hh:mm:ss' format (from and to) in between which the expenses were made.",
            content = @Content(schema =@Schema(implementation = TimeframeDto.class)))
                                                          @RequestBody TimeframeDto timeframeDto) throws UnrecoverableKeyException, NoSuchPaddingException, IllegalBlockSizeException, CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, BadPaddingException, InvalidKeyException {
        return ResponseEntity.ok(expenseService.getExpensesByTimeframe(timeframeDto));
    }

}

