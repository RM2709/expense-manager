package hr.assecosee.internship.expensemanager.util;

import hr.assecosee.internship.expensemanager.database.entity.Category;
import hr.assecosee.internship.expensemanager.database.entity.Expense;
import hr.assecosee.internship.expensemanager.database.entity.User;
import hr.assecosee.internship.expensemanager.dto.ExpenseDto;
import hr.assecosee.internship.expensemanager.dto.StatusDto;
import org.junit.jupiter.api.Test;

public class ExpenseMapperTest {

    @Test
    public void testConvertExpense(){
        Expense testExpense = new Expense();
        User testUser = new User();
        testUser.setUserId(15);
        testUser.setFirstName("Test");
        testUser.setLastName("Test");
        testUser.setEmail("Test");
        Category testCategory = new Category();
        testCategory.setCategoryId(15);
        testCategory.setName("Test");
        testCategory.setDescription("Test");
        testExpense.setExpenseId(15);
        testExpense.setCategoryId(15);
        testExpense.setUserId(15);
        testExpense.setDescription("Test");
        testExpense.setAmount(400.0);
        ExpenseDto desiredResult = new ExpenseDto();
        desiredResult.setStatus(new StatusDto(0, "No error!"));
        desiredResult.setExpenseId(15);
        desiredResult.setUserFullName("Test Test");
        desiredResult.setCategoryName("Test");
        desiredResult.setDescription("Test");
        desiredResult.setAmount(400.0);
        ExpenseDto result = ExpenseMapper.getExpenseDto(testExpense, testUser, testCategory);
        assert result.equals(desiredResult) : "Expense to Expense conversion unsuccessful";
    }
}
