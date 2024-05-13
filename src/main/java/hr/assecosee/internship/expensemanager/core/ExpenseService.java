package hr.assecosee.internship.expensemanager.core;

import hr.assecosee.internship.expensemanager.database.entity.Category;
import hr.assecosee.internship.expensemanager.database.entity.Expense;
import hr.assecosee.internship.expensemanager.database.entity.User;
import hr.assecosee.internship.expensemanager.database.repository.CategoryRepository;
import hr.assecosee.internship.expensemanager.database.repository.ExpenseRepository;
import hr.assecosee.internship.expensemanager.database.repository.UserRepository;
import hr.assecosee.internship.expensemanager.dto.ExpenseDto;
import hr.assecosee.internship.expensemanager.dto.StatusDto;
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

    public ResponseEntity<StatusDto> getExpense(Integer expenseId){
        Optional<Expense> expenseOptional = expenseRepository.findById(expenseId);
        Map<String, Object> status = new HashMap<>();
        StatusDto statusDto = new StatusDto();
        if(expenseOptional.isPresent()){
            Optional<User> userOptional = userRepository.findById(expenseOptional.get().getUserId());
            Optional<Category> categoryOptional = categoryRepository.findById(expenseOptional.get().getCategoryId());
            if(userOptional.isPresent() && categoryOptional.isPresent()){
                status.put("code", 0);
                status.put("message", "No error!");
                ExpenseDto expenseDto = new ExpenseDto();
                expenseDto.setStatus(status);
                expenseDto.setExpenseId(expenseOptional.get().getExpenseId());
                expenseDto.setUserFullName(userOptional.get().getFirstName() + " " + userOptional.get().getLastName());
                expenseDto.setCategoryName(categoryOptional.get().getName());
                expenseDto.setDescription(expenseOptional.get().getDescription());
                expenseDto.setAmount(expenseOptional.get().getAmount());
                expenseDto.setTime(expenseOptional.get().getTime());
                return ResponseEntity.ok(expenseDto);
            }else{
                status.put("message", "User with id="+expenseOptional.get().getUserId()+" not found OR Category with id="+expenseOptional.get().getCategoryId()+" not found!");
            }
        } else{
            status.put("message", "Expense with id="+expenseId+" not found!");
        }
        status.put("code", 1);
        statusDto.setStatus(status);
        return ResponseEntity.ok(statusDto);
    }
}
