package hr.assecosee.internship.expensemanager.core;

import hr.assecosee.internship.expensemanager.database.entity.Expense;
import hr.assecosee.internship.expensemanager.database.repository.CategoryRepository;
import hr.assecosee.internship.expensemanager.database.repository.ExpenseRepository;
import hr.assecosee.internship.expensemanager.database.repository.UserRepository;
import hr.assecosee.internship.expensemanager.dto.*;
import hr.assecosee.internship.expensemanager.util.ConvertExpenseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.BDDMockito.will;
import static org.mockito.Mockito.doReturn;
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
        when(expenseRepository.findById(15)).thenReturn(Optional.empty());
        assert expenseService.getExpense(15).equals(new StatusWrapper(new StatusDto(1, "Expense with id=15 not found!"))) : "Expense with ID 15 retrieved when it should not exist";
    }


    @Test
    public void createExpense_WrongUserIdProvided_ExpenseNotCreated(){
        ExpenseInfoDto expenseInfo = new ExpenseInfoDto();
        expenseInfo.setUserId(15);
        assert !expenseService.createExpense(expenseInfo).equals(expenseInfo) : "Category created successfully when wrong user Id was provided";
    }


    @Test
    public void updateExpense_WrongExpenseInfoProvided_ExpenseNotUpdated(){
        ExpenseInfoDto expenseInfo = new ExpenseInfoDto();
        expenseInfo.setUserId(15);
        expenseInfo.setCategoryId(15);
        expenseInfo.setDescription("Test");
        expenseInfo.setAmount(1500.00);
        expenseInfo.setTime(new Timestamp(2024, 5, 5, 12, 0, 0, 0));
        Expense updatedExpense = new Expense();
        updatedExpense.setExpenseId(15);
        updatedExpense.setCategoryId(15);
        updatedExpense.setUserId(15);
        updatedExpense.setDescription("Test");
        updatedExpense.setAmount(1500.00);
        updatedExpense.setTime(new Timestamp(2024, 5, 5, 12, 0, 0, 0));
        when(expenseRepository.findById(15)).thenReturn(Optional.empty());
        assert expenseService.updateExpense(15, expenseInfo).equals(new StatusWrapper(new StatusDto(1, "Expense with id=15 does not exist!"))) : "Expense updated successfully when wrong info was given";
    }


    @Test
    public void deleteExpense_ExpenseIdProvided_ExpenseDeleted(){
        Expense deletedExpense = new Expense();
        deletedExpense.setExpenseId(15);
        when(expenseRepository.findById(15)).thenReturn(Optional.of(deletedExpense));
        assert expenseService.deleteExpense(15).equals(new StatusWrapper(new StatusDto(0, "No error!"))) : "Expense not deleted properly";
    }

    @Test
    public void getExpensesByUser_UserDoesNotExist_ExpensesNotRetrieved() {
        when(userRepository.findById(15)).thenReturn(Optional.empty());
        assert expenseService.getExpensesByUser(15).equals(new StatusWrapper(new StatusDto(1, "User with id=15 does not exist!"))) : "Expenses retrieved when wrong user id was given";
    }

    @Test
    public void getExpensesByCategory_CategoryDoesNotExist_ExpensesNotRetrieved() {
        when(categoryRepository.findById(15)).thenReturn(Optional.empty());
        assert expenseService.getExpensesByCategory(15).equals(new StatusWrapper(new StatusDto(1, "Category with id=15 does not exist!"))) : "Expenses retrieved when wrong category id was given";
    }

    @Test
    public void getExpensesByTimeframe_ShortTimeframe_ExpenseListEmpty(){
        ExpensesByCategoryDto expensesByTimeframeDto = new ExpensesByCategoryDto();
        StatusDto status = new StatusDto(0, "No error!");
        expensesByTimeframeDto.setStatus(status);
        expensesByTimeframeDto.setExpenses(new ArrayList<>());
        TimeframeDto timeFrame = new TimeframeDto();
        timeFrame.setExpenseFrom(new Timestamp(2024, 1, 1, 12, 0, 0, 0));
        timeFrame.setExpenseTo(new Timestamp(2024, 1, 1, 12, 0, 0, 1));
        when(expenseRepository.findAllByTimeBetween(timeFrame.getExpenseFrom(), timeFrame.getExpenseTo())).thenReturn(new ArrayList<>());
        assert expenseService.getExpensesByTimeframe(timeFrame).equals(expensesByTimeframeDto) : "Expense list is not empty when it should be0";
    }



}
