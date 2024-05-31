package hr.assecosee.internship.expensemanager.core;

import hr.assecosee.internship.expensemanager.core.exception.ExpenseManagerException;
import hr.assecosee.internship.expensemanager.database.entity.Expense;
import hr.assecosee.internship.expensemanager.database.entity.User;
import hr.assecosee.internship.expensemanager.database.repository.ExpenseRepository;
import hr.assecosee.internship.expensemanager.database.repository.UserRepository;
import hr.assecosee.internship.expensemanager.dto.BudgetDto;
import hr.assecosee.internship.expensemanager.dto.StatusDto;
import hr.assecosee.internship.expensemanager.dto.TimeframeDto;
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

    public static final int MILISECONDS_IN_DAY = 87400000;
    private final UserRepository userRepository;

    private final ExpenseRepository expenseRepository;

    private static final Logger logger = LogManager.getLogger(BudgetService.class);

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
            TimeframeDto period = calculatePeriod(user.getBudgetDays());
            BudgetDto budgetDto = new BudgetDto();
            budgetDto.setStatus(new StatusDto(0, "No error!"));
            budgetDto.setUserId(userId);
            budgetDto.setCurrentBudgetPeriodStart(period.getExpenseFrom());
            budgetDto.setCurrentBudgetPeriodEnd(period.getExpenseTo());
            budgetDto.setRemainingAmount(calculateRemainingBudget(user, period));
            logger.info("Budget calculated. Returning.");
            return budgetDto;
        } else{
            logger.error("User not found, throwing exception.");
            throw new ExpenseManagerException(1, "User with id="+userId+" not found!");
        }
    }

    /**
     * Calculates the amount of funds remaining in a user's budget (shows negative value if user went over budget).
     *
     * @param user User whose remaining budget is being calculated.
     * @param period Time period in which we're considering expenses.
     * @return Amount of funds remaining in budget for time period.
     */
    private Double calculateRemainingBudget(User user, TimeframeDto period) {
        Double budget = user.getBudget();
        for (Expense expense : expenseRepository.findAllByTimeBetweenAndUserId(period.getExpenseFrom(), period.getExpenseTo(), user.getUserId())) {
            budget -= expense.getAmount();
        }
        return budget;
    }

    /**
     * Calculates the current budgeting period.
     *
     * @param budgetDays The length of a user's budgeting period in days.
     * @return TimeframeDto object containing the start and end of the budgeting period.
     */
    private TimeframeDto calculatePeriod(Integer budgetDays) {
        logger.info("Method calculatePeriod called with budget days = " + budgetDays);
        Timestamp currentTime = new Timestamp(Calendar.getInstance().getTime().getTime());
        Timestamp periodStart = new Timestamp(currentTime.getYear(), currentTime.getMonth(), 1, 0, 0, 0, 0);
        Timestamp periodEnd = new Timestamp(currentTime.getYear(), currentTime.getMonth(), 1, 23, 59, 59, 590000000);
        periodEnd.setTime(periodEnd.getTime() + (budgetDays * 24 * 60 * 60 * 1000));
        while(!(periodStart.before(currentTime) && periodEnd.after(currentTime))){
            periodStart.setTime(periodStart.getTime() + (budgetDays * 24 * 60 * 60 * 1000));
            periodEnd.setTime(periodStart.getTime() + (budgetDays * 24 * 60 * 60 * 1000) + MILISECONDS_IN_DAY-1);
            if(periodEnd.getMonth()>currentTime.getMonth()){
                periodEnd = new Timestamp(currentTime.getYear(), currentTime.getMonth(), YearMonth.now().atEndOfMonth().getDayOfMonth(), 23, 59, 59, 590000000);
            }
        }
        return new TimeframeDto(periodStart, periodEnd);
    }

}
