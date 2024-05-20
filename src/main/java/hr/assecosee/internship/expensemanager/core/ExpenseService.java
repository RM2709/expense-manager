package hr.assecosee.internship.expensemanager.core;

import hr.assecosee.internship.expensemanager.database.entity.Category;
import hr.assecosee.internship.expensemanager.database.entity.Expense;
import hr.assecosee.internship.expensemanager.database.entity.User;
import hr.assecosee.internship.expensemanager.database.repository.CategoryRepository;
import hr.assecosee.internship.expensemanager.database.repository.ExpenseRepository;
import hr.assecosee.internship.expensemanager.database.repository.UserRepository;
import hr.assecosee.internship.expensemanager.dto.*;
import hr.assecosee.internship.expensemanager.util.ConvertExpenseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Business logic concerning expenses.
 */
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

    /**
     * Retrieves an expense.
     *
     * @param expenseId ID of the expense to be retrieved.
     * @return ExpenseDto object containing a status message and basic information of the retrieved expense (id, user full name, category name, description, amount, time).
     */
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

    /**
     * Creates a new expense.
     *
     * @param expenseInfo JSON object which contains the user id, category id, description, amount, and time of the expense to be created.
     * @return Status code, a message describing the outcome of the operation, and basic information of the created expense (id, user full name, category name, description, amount, time).
     */
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
        return ConvertExpenseDto.getExpenseDto(newExpense, newExpense.getUsersByUserId(), newExpense.getCategoryByCategoryId());
    }

    /**
     * Updates an existing expense.
     *
     * @param expenseId ID of the expense to be updated.
     * @param expenseInfo JSON object which contains the user id, category id, description, amount, and time of the expense to be updated.
     * @return Status code, a message describing the outcome of the operation, and basic information of the updated expense (id, user full name, category name, description, amount, time)
     */
    public Dto updateExpense(Integer expenseId, ExpenseInfoDto expenseInfo) {
        Optional<Expense> updatedExpense = expenseRepository.findById(expenseId);
        if(updatedExpense.isPresent()){
            updatedExpense.get().setUserId(expenseInfo.getUserId());
            updatedExpense.get().setCategoryId(expenseInfo.getCategoryId());
            updatedExpense.get().setDescription(expenseInfo.getDescription());
            updatedExpense.get().setAmount(expenseInfo.getAmount());
            updatedExpense.get().setTime(expenseInfo.getTime());
            expenseRepository.save(updatedExpense.get());
            return ConvertExpenseDto.getExpenseDto(updatedExpense.get(), updatedExpense.get().getUsersByUserId(), updatedExpense.get().getCategoryByCategoryId());
        } else{
            return new StatusWrapper(new StatusDto(1, "Expense with id="+expenseId+" does not exist!"));
        }

    }

    /**
     * Deletes an existing expense.
     *
     * @param expenseId ID of the expense to be deleted.
     * @return Status code and a message describing the outcome of the operation.
     */
    public Dto deleteExpense(Integer expenseId) {
        if(expenseRepository.findById(expenseId).isPresent()){
            expenseRepository.deleteById(expenseId);
            return new StatusWrapper(new StatusDto(0, "No error!"));
        } else{
            return new StatusWrapper(new StatusDto(1, "Expense with id="+expenseId+" does not exist!"));
        }
    }

    /**
     * Retrieves all expenses incurred by a single user.
     *
     * @param userId ID of the user whose expenses are being retrieved.
     * @return Status code, a message describing the outcome of the operation,information on the selected user (id, name, email), and information on all the expenses incurred by said user (category name, description, amount, time)."
     */
    public Dto getExpensesByUser(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            return new StatusWrapper(new StatusDto(1, "User with id="+userId+" does not exist!"));
        }
        ExpensesByUserDto expensesByUserDto = new ExpensesByUserDto();
        StatusDto status = new StatusDto(0, "No error!");
        expensesByUserDto.setStatus(status);
        UserDto user = new UserDto();
        user.setUserId(userOptional.get().getUserId());
        user.setFullName(userOptional.get().getFirstName() + " " + userOptional.get().getLastName());
        user.setEmail(userOptional.get().getEmail());
        expensesByUserDto.setUser(user);
        for (Expense expense : expenseRepository.findAllByUserId(userId)) {
            expensesByUserDto.getExpenses().add(new ExpenseDto(expense.getCategoryByCategoryId().getName(), expense.getDescription(), expense.getAmount(), expense.getTime()));
        }
        return expensesByUserDto;
    }

    /**
     * Retrieves all expenses belonging to a single category.
     *
     * @param categoryId ID of the category to which the expenses being retrieved belong.
     * @return Status code, a message describing the outcome of the operation, information on the selected category (id, name, description), and information on all the expenses belonging to said category (id, user name, description, amount, time).
     */
    public Dto getExpensesByCategory(Integer categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if(categoryOptional.isEmpty()){
            return new StatusWrapper(new StatusDto(1, "Category with id="+categoryId+" does not exist!"));
        }
        ExpensesByCategoryDto expensesByCategoryDto = new ExpensesByCategoryDto();
        StatusDto status = new StatusDto(0, "No error!");
        expensesByCategoryDto.setStatus(status);
        CategoryDto category = new CategoryDto();
        category.setCategoryId(categoryOptional.get().getCategoryId());
        category.setName(categoryOptional.get().getName());
        category.setDescription(categoryOptional.get().getDescription());
        expensesByCategoryDto.setCategory(category);
        for (Expense expense : expenseRepository.findAllByCategoryId(categoryId)) {
            expensesByCategoryDto.getExpenses().add(new ExpenseDto(expense.getExpenseId(), expense.getUsersByUserId().getFirstName() + " " + expense.getUsersByUserId().getLastName(), expense.getDescription(), expense.getAmount(), expense.getTime()));
        }
        return expensesByCategoryDto;
    }

    /**
     * Retrieves all expenses incurred between any two points in time.
     *
     * @param timeframeDto JSON object which contains two times in 'yyyy-MM-dd hh:mm:ss' format (from and to) in between which the expenses were made.
     * @return Status code, a message describing the outcome of the operation, and information on all the expenses made in the selected timeframe (id, user full name, category name, description, amount, time).
     */
    public Dto getExpensesByTimeframe(TimeframeDto timeframeDto) {
        ExpensesByCategoryDto expensesByTimeframeDto = new ExpensesByCategoryDto();
        StatusDto status = new StatusDto(0, "No error!");
        expensesByTimeframeDto.setStatus(status);
        for (Expense expense : expenseRepository.findAllByTimeBetween(timeframeDto.getExpenseFrom(), timeframeDto.getExpenseTo())) {
            expensesByTimeframeDto.getExpenses().add(new ExpenseDto(expense.getExpenseId(), expense.getCategoryByCategoryId().getName(), expense.getUsersByUserId().getFirstName() + " " + expense.getUsersByUserId().getLastName(), expense.getDescription(), expense.getAmount(), expense.getTime()));
        }
        return expensesByTimeframeDto;
    }
}
