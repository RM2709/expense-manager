package hr.assecosee.internship.expensemanager.core;

import hr.assecosee.internship.expensemanager.ExpenseManagerApplication;
import hr.assecosee.internship.expensemanager.core.exception.ExpenseManagerException;
import hr.assecosee.internship.expensemanager.database.entity.Expense;
import hr.assecosee.internship.expensemanager.database.entity.User;
import hr.assecosee.internship.expensemanager.database.repository.ExpenseRepository;
import hr.assecosee.internship.expensemanager.database.repository.UserRepository;
import hr.assecosee.internship.expensemanager.dto.BudgetDto;
import hr.assecosee.internship.expensemanager.dto.StatusDto;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Optional;

/**
 * Business logic concerning users.
 */
@Service
public class BudgetService {

    private final UserRepository userRepository;

    private final ExpenseRepository expenseRepository;

    private static final Logger logger = LogManager.getLogger(ExpenseManagerApplication.class);

    private Timestamp periodStart;
    private Timestamp periodEnd;

    @Autowired
    public BudgetService(UserRepository userRepository, ExpenseRepository expenseRepository){
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
    }

    /**
     * Retrieves the state of a user's budget.
     *
     * @param userId ID of the user whose budget is to be retrieved.
     * @return BudgetDto object containing a Status code, a message describing the outcome of the operation, the selected user's id, and basic information on the user's budget (start of period, end of period, remaining amount).
     */
    public BudgetDto getBudget(Integer userId) throws ExpenseManagerException {
        logger.info("Method getBudget called with user id " + userId);
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            logger.info("User retrieved. Calculating budget.");
            if(user.getBudget()==null || user.getBudget()==0){
                throw new ExpenseManagerException(1, "User has no allocated budget amount!");
            }
            if(user.getBudgetDays()==null || user.getBudgetDays()==0){
                throw new ExpenseManagerException(1, "User has no defined budget period!");
            }
            calculatePeriod(user.getBudgetDays());
            BudgetDto budgetDto = new BudgetDto();
            budgetDto.setStatus(new StatusDto(0, "No error!"));
            budgetDto.setUserId(userId);
            budgetDto.setCurrentBudgetPeriodStart(periodStart);
            budgetDto.setCurrentBudgetPeriodEnd(periodEnd);
            budgetDto.setRemainingAmount(calculateRemainingBudget(user));
            logger.info("Budget calculated. Returning.");
            return budgetDto;
        } else{
            logger.error("User not found, throwing exception.");
            throw new ExpenseManagerException(1, "User with id="+userId+" not found!");
        }
    }

    private Double calculateRemainingBudget(User user) {
        Double budget = user.getBudget();
        for (Expense expense : expenseRepository.findAllByTimeBetweenAndUserId(periodStart, periodEnd, user.getUserId())) {
            budget -= expense.getAmount();
        }
        return budget;
    }

    private void calculatePeriod(Integer budgetDays) {
        Timestamp currentTime = new Timestamp(Calendar.getInstance().getTime().getTime());
        periodStart = new Timestamp(currentTime.getYear(), currentTime.getMonth(), 1, 0, 0, 0, 0);
        periodEnd = new Timestamp(periodStart.getTime());
        periodEnd.setTime(periodStart.getTime() + (budgetDays * 24 * 60 * 60 * 1000));
        while(!(periodStart.before(currentTime) && periodEnd.after(currentTime))){
            periodStart.setTime(periodStart.getTime() + (budgetDays * 24 * 60 * 60 * 1000));
            periodEnd.setTime(periodStart.getTime() + (budgetDays * 24 * 60 * 60 * 1000));
            if(periodEnd.getMonth()>currentTime.getMonth()){
                periodEnd = new Timestamp(currentTime.getYear(), currentTime.getMonth(), YearMonth.now().atEndOfMonth().getDayOfMonth(), 0, 0, 0, 0);
            }
        }
    }

}
