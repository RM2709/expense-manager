package hr.assecosee.internship.expensemanager.core;

import hr.assecosee.internship.expensemanager.core.exception.ExpenseManagerException;
import hr.assecosee.internship.expensemanager.database.entity.Expense;
import hr.assecosee.internship.expensemanager.database.repository.CategoryRepository;
import hr.assecosee.internship.expensemanager.database.repository.ExpenseRepository;
import hr.assecosee.internship.expensemanager.database.repository.UserRepository;
import hr.assecosee.internship.expensemanager.dto.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTest {

    @Mock
    ExpenseRepository expenseRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    ExpenseService expenseService;

    @Test
    public void getExpense_ExpenseIdIs15_ExpenseNotRetrieved(){
        assertThrows(ExpenseManagerException.class, () -> expenseService.getExpense(15), "Expense with ID 15 retrieved when it should not exist");
    }


    @Test
    public void createExpense_WrongUserIdProvided_ExpenseNotCreated(){
        ExpenseInfoDto expenseInfo = new ExpenseInfoDto();
        expenseInfo.setUserId(15);
        assertThrows(ExpenseManagerException.class, () -> expenseService.createExpense(expenseInfo), "Category created successfully when wrong user Id was provided");
    }


    @Test
    public void updateExpense_WrongExpenseInfoProvided_ExpenseNotUpdated(){
        ExpenseInfoDto expenseInfo = new ExpenseInfoDto();
        expenseInfo.setUserId(15);
        expenseInfo.setCategoryId(15);
        expenseInfo.setDescription("Test");
        expenseInfo.setAmount(1500.00);
        expenseInfo.setTime(new Timestamp(1724000100000L));
        when(expenseRepository.findById(15)).thenReturn(Optional.empty());
        assertThrows(ExpenseManagerException.class, () -> expenseService.updateExpense(15, expenseInfo), "Expense updated successfully when wrong info was given");
    }


    @Test
    public void deleteExpense_ExpenseIdProvided_ExpenseDeleted() throws ExpenseManagerException {
        Expense deletedExpense = new Expense();
        deletedExpense.setExpenseId(15);
        when(expenseRepository.findById(15)).thenReturn(Optional.of(deletedExpense));
        assert expenseService.deleteExpense(15).equals(new Response(0, "No error!")) : "Expense not deleted properly";
    }

    @Test
    public void getExpensesByUser_UserDoesNotExist_ExpensesNotRetrieved(){
        when(userRepository.findById(15)).thenReturn(Optional.empty());
        assertThrows(ExpenseManagerException.class, () -> expenseService.getExpensesByUser(15), "Expenses retrieved when wrong user id was given");
    }

    @Test
    public void getExpensesByCategory_CategoryDoesNotExist_ExpensesNotRetrieved() {
        when(categoryRepository.findById(15)).thenReturn(Optional.empty());
        assertThrows(ExpenseManagerException.class, () ->  expenseService.getExpensesByCategory(15), "Expenses retrieved when wrong category id was given");
    }

    @Test
    public void getExpensesByTimeframe_ShortTimeframe_ExpenseListEmpty(){
        ExpensesByCategoryDto expensesByTimeframeDto = new ExpensesByCategoryDto();
        StatusDto status = new StatusDto(0, "No error!");
        expensesByTimeframeDto.setStatus(status);
        expensesByTimeframeDto.setExpenses(new ArrayList<>());
        TimeframeDto timeFrame = new TimeframeDto();
        timeFrame.setExpenseFrom(new Timestamp(1724000100000L));
        timeFrame.setExpenseTo(new Timestamp(1724000100000L));
        when(expenseRepository.findAllByTimeBetween(timeFrame.getExpenseFrom(), timeFrame.getExpenseTo())).thenReturn(new ArrayList<>());
        assert expenseService.getExpensesByTimeframe(timeFrame).equals(expensesByTimeframeDto) : "Expense list is not empty when it should be";
    }



}
