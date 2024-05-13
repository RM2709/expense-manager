package hr.assecosee.internship.expensemanager.core;

import hr.assecosee.internship.expensemanager.database.entity.Category;
import hr.assecosee.internship.expensemanager.database.entity.Expense;
import hr.assecosee.internship.expensemanager.database.entity.User;
import hr.assecosee.internship.expensemanager.database.repository.CategoryRepository;
import hr.assecosee.internship.expensemanager.database.repository.ExpenseRepository;
import hr.assecosee.internship.expensemanager.database.repository.UserRepository;
import hr.assecosee.internship.expensemanager.dto.Dto;
import hr.assecosee.internship.expensemanager.dto.ExpenseDto;
import hr.assecosee.internship.expensemanager.dto.StatusDto;
import hr.assecosee.internship.expensemanager.dto.StatusWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
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
            Optional<User> userOptional = userRepository.findById(expenseOptional.get().getUserId());
            Optional<Category> categoryOptional = categoryRepository.findById(expenseOptional.get().getCategoryId());
            if(userOptional.isPresent() && categoryOptional.isPresent()){
                ExpenseDto expenseDto = new ExpenseDto();
                expenseDto.setStatus(new StatusDto(0, "No error!"));
                expenseDto.setExpenseId(expenseOptional.get().getExpenseId());
                expenseDto.setUserFullName(userOptional.get().getFirstName() + " " + userOptional.get().getLastName());
                expenseDto.setCategoryName(categoryOptional.get().getName());
                expenseDto.setDescription(expenseOptional.get().getDescription());
                expenseDto.setAmount(expenseOptional.get().getAmount());
                expenseDto.setTime(expenseOptional.get().getTime());
                return expenseDto;
            }else{
                if(!userOptional.isPresent()){
                    return new StatusWrapper(new StatusDto(1, "User with id="+expenseOptional.get().getUserId()+" not found!"));
                } else if (!categoryOptional.isPresent()){
                    return new StatusWrapper(new StatusDto(1, "Category with id="+expenseOptional.get().getCategoryId()+" not found!"));
                }
            }
        }
        return new StatusWrapper(new StatusDto(1, "Expense with id="+expenseId+" not found!"));
    }
}
