package hr.assecosee.internship.expensemanager.rest;

import hr.assecosee.internship.expensemanager.core.BudgetService;
import hr.assecosee.internship.expensemanager.core.exception.ExpenseManagerException;
import hr.assecosee.internship.expensemanager.dto.BudgetDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST mapping concerning budget.
 */
@RestController
@Log4j
@RequestMapping("/budget")
public class BudgetController {

    private final BudgetService budgetService;

    @Autowired
    public BudgetController(BudgetService budgetService){
        this.budgetService = budgetService;
    }

    /**
     * Retrieves the state of a user's budget.
     *
     * @param userId ID of the user whose budget is to be retrieved.
     * @return JSON object containing a Status code, a message describing the outcome of the operation, the selected user's id, and basic information on the user's budget (start of period, end of period, remaining amount).
     */
    @GetMapping(value = "/{userId}")
    @Operation(summary = "Retrieve the state of a user's budget.",
            description = "Retrieves information about the selected user's budget such as start and end of the current budget period " +
                    "and the remaining amount of funds in the budget for said period.")
    public ResponseEntity<BudgetDto> getBudget(@Parameter(description = "ID of the user whose budget is to be retrieved.", required = true) @PathVariable("userId") Integer userId) throws ExpenseManagerException {
        return ResponseEntity.ok(budgetService.getBudget(userId));
    }


}

