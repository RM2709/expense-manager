package hr.assecosee.internship.expensemanager.util;

import hr.assecosee.internship.expensemanager.database.entity.Category;
import hr.assecosee.internship.expensemanager.database.entity.Expense;
import hr.assecosee.internship.expensemanager.database.entity.User;
import hr.assecosee.internship.expensemanager.dto.ExpenseDto;
import hr.assecosee.internship.expensemanager.dto.StatusDto;

/**
 * Helper class for converting an Expense object into a suitable ExpenseDto object.
 */
public class ConvertExpenseDto {
    /**
     * Converts an Expense object into an ExpenseDto object.
     *
     * @param category Expense class object which is converted into a JSON object of the appropriate form.
     * @return ExpenseDto object which is structured correctly (containing expense id, user full name, category name, description, amount, time) and ready to be returned in JSON form.
     */
    public static ExpenseDto getExpenseDto(Expense expense, User user, Category category) {
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setStatus(new StatusDto(0, "No error!"));
        expenseDto.setExpenseId(expense.getExpenseId());
        expenseDto.setUserFullName(user.getFirstName() + " " + user.getLastName());
        expenseDto.setCategoryName(category.getName());
        expenseDto.setDescription(expense.getDescription());
        expenseDto.setAmount(expense.getAmount());
        expenseDto.setTime(expense.getTime());
        return expenseDto;
    }
}