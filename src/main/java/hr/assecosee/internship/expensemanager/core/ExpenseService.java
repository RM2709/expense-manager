package hr.assecosee.internship.expensemanager.core;

import hr.assecosee.internship.expensemanager.database.entity.Category;
import hr.assecosee.internship.expensemanager.database.entity.Expense;
import hr.assecosee.internship.expensemanager.database.entity.User;
import hr.assecosee.internship.expensemanager.database.repository.CategoryRepository;
import hr.assecosee.internship.expensemanager.database.repository.ExpenseRepository;
import hr.assecosee.internship.expensemanager.database.repository.UserRepository;
import hr.assecosee.internship.expensemanager.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExpenseService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseService(CategoryRepository categoryRepository, UserRepository userRepository, ExpenseRepository expenseRepository){
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
    }

    public Dto getExpense(Integer expenseId){
        Optional<Expense> expenseOptional = expenseRepository.findById(expenseId);
        if(expenseOptional.isPresent()){
                ExpenseDto expenseDto = new ExpenseDto();
                expenseDto.setStatus(new StatusDto(0, "No error!"));
                expenseDto.setExpenseId(expenseOptional.get().getExpenseId());
                expenseDto.setUserFullName(expenseOptional.get().getUsersByUserId().getFirstName() + " " + expenseOptional.get().getUsersByUserId().getLastName());
                expenseDto.setCategoryName(expenseOptional.get().getCategoryByCategoryId().getName());
                expenseDto.setDescription(expenseOptional.get().getDescription());
                expenseDto.setAmount(expenseOptional.get().getAmount());
                expenseDto.setTime(expenseOptional.get().getTime());
                return expenseDto;
        } else{
            return new StatusWrapper(new StatusDto(1, "Expense with id="+expenseId+" not found!"));
        }
    }

    public Dto createExpense(ExpenseInfoDto expenseInfo) {
        Optional<User> userOptional = userRepository.findById(expenseInfo.getUserId());
        if(userOptional.isEmpty()){
            return new StatusWrapper(new StatusDto(1, "User with id="+expenseInfo.getUserId()+" does not exist!"));
        }
        Optional<Category> categoryOptional = categoryRepository.findById(expenseInfo.getCategoryId());
        if(categoryOptional.isEmpty()){
            return new StatusWrapper(new StatusDto(1, "Category with id="+expenseInfo.getCategoryId()+" does not exist!"));
        }
        Expense newExpense = new Expense();
        newExpense.setUserId(expenseInfo.getUserId());
        newExpense.setUsersByUserId(userOptional.get());
        newExpense.setCategoryId(expenseInfo.getCategoryId());
        newExpense.setCategoryByCategoryId(categoryOptional.get());
        newExpense.setDescription(expenseInfo.getDescription());
        newExpense.setAmount(expenseInfo.getAmount());
        newExpense.setTime(expenseInfo.getTime());
        newExpense = expenseRepository.save(newExpense);
        return getExpenseDto(newExpense, newExpense.getUsersByUserId(), newExpense.getCategoryByCategoryId());
    }

    public Dto updateExpense(Integer expenseId, ExpenseInfoDto expenseInfo) {
        Optional<Expense> updatedExpense = expenseRepository.findById(expenseId);
        if(updatedExpense.isPresent()){
            updatedExpense.get().setUserId(expenseInfo.getUserId());
            updatedExpense.get().setCategoryId(expenseInfo.getCategoryId());
            updatedExpense.get().setDescription(expenseInfo.getDescription());
            updatedExpense.get().setAmount(expenseInfo.getAmount());
            updatedExpense.get().setTime(expenseInfo.getTime());
            expenseRepository.save(updatedExpense.get());
            return getExpenseDto(updatedExpense.get(), updatedExpense.get().getUsersByUserId(), updatedExpense.get().getCategoryByCategoryId());
        } else{
            return new StatusWrapper(new StatusDto(1, "Expense with id="+expenseId+" does not exist!"));
        }

    }

    private static ExpenseDto getExpenseDto(Expense expense, User user, Category category) {
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

    public Dto deleteExpense(Integer expenseId) {
        if(expenseRepository.findById(expenseId).isPresent()){
            expenseRepository.deleteById(expenseId);
            return new StatusWrapper(new StatusDto(0, "No error!"));
        } else{
            return new StatusWrapper(new StatusDto(1, "User with id="+expenseId+" does not exist!"));
        }
    }

}
