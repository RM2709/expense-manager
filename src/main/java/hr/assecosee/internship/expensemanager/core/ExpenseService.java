package hr.assecosee.internship.expensemanager.core;

import hr.assecosee.internship.expensemanager.ExpenseManagerApplication;
import hr.assecosee.internship.expensemanager.core.exception.ExpenseManagerException;
import hr.assecosee.internship.expensemanager.database.entity.Category;
import hr.assecosee.internship.expensemanager.database.entity.Expense;
import hr.assecosee.internship.expensemanager.database.entity.User;
import hr.assecosee.internship.expensemanager.database.repository.CategoryRepository;
import hr.assecosee.internship.expensemanager.database.repository.ExpenseRepository;
import hr.assecosee.internship.expensemanager.database.repository.UserRepository;
import hr.assecosee.internship.expensemanager.dto.*;
import hr.assecosee.internship.expensemanager.util.ExpenseMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Optional;

/**
 * Business logic concerning expenses.
 */
@Service
public class ExpenseService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ExpenseRepository expenseRepository;

    private final EncryptionService encryptionService;

    private static final Logger logger = LogManager.getLogger(ExpenseManagerApplication.class);

    @Autowired
    public ExpenseService(CategoryRepository categoryRepository, UserRepository userRepository, ExpenseRepository expenseRepository, EncryptionService encryptionService){
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
        this.encryptionService = encryptionService;
    }

    /**
     * Retrieves an expense.
     *
     * @param expenseId ID of the expense to be retrieved.
     * @return ExpenseDto object containing a status message and basic information of the retrieved expense (id, user full name, category name, description, amount, time).
     */
    public ExpenseDto getExpense(Integer expenseId) throws ExpenseManagerException, UnrecoverableKeyException, NoSuchPaddingException, IllegalBlockSizeException, CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, BadPaddingException, InvalidKeyException {
        logger.info("Method getExpense called with id " + expenseId);
        Optional<Expense> expenseOptional = expenseRepository.findById(expenseId);
        if(expenseOptional.isPresent()){
            logger.info("Expense retrieved.");
            expenseOptional.get().setDescription(encryptionService.decrypt(expenseOptional.get().getDescription()));
            User user = expenseOptional.get().getUsersByUserId();
            user.setFirstName(encryptionService.decrypt(user.getFirstName()));
            user.setLastName(encryptionService.decrypt(user.getLastName()));
            return ExpenseMapper.getExpenseDto(expenseOptional.get(), user, expenseOptional.get().getCategoryByCategoryId());
        } else{
            logger.error("Expense not found, throwing exception.");
            throw new ExpenseManagerException(1, "Expense with id="+expenseId+" not found!");
        }
    }

    /**
     * Creates a new expense.
     *
     * @param expenseInfo JSON object which contains the user id, category id, description, amount, and time of the expense to be created.
     * @return Status code, a message describing the outcome of the operation, and basic information of the created expense (id, user full name, category name, description, amount, time).
     */
    public ExpenseDto createExpense(ExpenseInfoDto expenseInfo) throws ExpenseManagerException, UnrecoverableKeyException, IllegalBlockSizeException, NoSuchPaddingException, CertificateException, BadPaddingException, NoSuchAlgorithmException, KeyStoreException, IOException, InvalidKeyException {
        logger.info("Method createExpense called.");
        Optional<User> userOptional = userRepository.findById(expenseInfo.getUserId());
        if(userOptional.isEmpty()){
            logger.error("Attempted to get user with the user id " + expenseInfo.getUserId() + ". User not found. Throwing error.");
            throw new ExpenseManagerException(1, "User with id="+expenseInfo.getUserId()+" not found!");
        }
        Optional<Category> categoryOptional = categoryRepository.findById(expenseInfo.getCategoryId());
        if(categoryOptional.isEmpty()){
            logger.error("Attempted to get category with the category id " + expenseInfo.getCategoryId() + ". Category not found. Throwing error.");
            throw new ExpenseManagerException(1, "Category with id="+expenseInfo.getCategoryId()+" not found!");
        }
        Expense newExpense = new Expense();
        newExpense.setUserId(expenseInfo.getUserId());
        newExpense.setUsersByUserId(userOptional.get());
        newExpense.setCategoryId(expenseInfo.getCategoryId());
        newExpense.setCategoryByCategoryId(categoryOptional.get());
        newExpense.setDescription(encryptionService.encrypt(expenseInfo.getDescription()));
        newExpense.setAmount(expenseInfo.getAmount());
        newExpense.setTime(expenseInfo.getTime());
        newExpense = expenseRepository.save(newExpense);
        newExpense.setDescription(expenseInfo.getDescription());
        logger.info("Expense created with id " + newExpense.getExpenseId());
        User user = newExpense.getUsersByUserId();
        user.setFirstName(encryptionService.decrypt(user.getFirstName()));
        user.setLastName(encryptionService.decrypt(user.getLastName()));
        return ExpenseMapper.getExpenseDto(newExpense, user, newExpense.getCategoryByCategoryId());
    }

    /**
     * Updates an existing expense.
     *
     * @param expenseId ID of the expense to be updated.
     * @param expenseInfo JSON object which contains the user id, category id, description, amount, and time of the expense to be updated.
     * @return Status code, a message describing the outcome of the operation, and basic information of the updated expense (id, user full name, category name, description, amount, time)
     */
    public ExpenseDto updateExpense(Integer expenseId, ExpenseInfoDto expenseInfo) throws ExpenseManagerException, UnrecoverableKeyException, IllegalBlockSizeException, NoSuchPaddingException, CertificateException, BadPaddingException, NoSuchAlgorithmException, KeyStoreException, IOException, InvalidKeyException {
        logger.info("Method updateExpense called with id " + expenseId);
        Optional<Expense> updatedExpense = expenseRepository.findById(expenseId);
        if(updatedExpense.isPresent()){
            updatedExpense.get().setUserId(expenseInfo.getUserId());
            updatedExpense.get().setCategoryId(expenseInfo.getCategoryId());
            updatedExpense.get().setDescription(encryptionService.encrypt(expenseInfo.getDescription()));
            updatedExpense.get().setAmount(expenseInfo.getAmount());
            updatedExpense.get().setTime(expenseInfo.getTime());
            expenseRepository.save(updatedExpense.get());
            updatedExpense.get().setDescription(expenseInfo.getDescription());
            logger.info("Expense updated.");
            User user = updatedExpense.get().getUsersByUserId();
            user.setFirstName(encryptionService.decrypt(user.getFirstName()));
            user.setLastName(encryptionService.decrypt(user.getLastName()));
            return ExpenseMapper.getExpenseDto(updatedExpense.get(), user, updatedExpense.get().getCategoryByCategoryId());
        } else{
            logger.error("Expense not found, throwing exception.");
            throw new ExpenseManagerException(1, "Expense with id="+expenseId+" not found!");
        }

    }

    /**
     * Deletes an existing expense.
     *
     * @param expenseId ID of the expense to be deleted.
     * @return Status code and a message describing the outcome of the operation.
     */
    public Response deleteExpense(Integer expenseId) throws ExpenseManagerException {
        logger.info("Method deleteExpense called with id " + expenseId);
        if(expenseRepository.findById(expenseId).isPresent()){
            expenseRepository.deleteById(expenseId);
            logger.info("Expense deleted.");
            return new Response(0, "No error!");
        } else{
            logger.error("Expense not found, throwing exception.");
            throw new ExpenseManagerException(1, "Expense with id="+expenseId+" does not exist!");
        }
    }

    /**
     * Retrieves all expenses incurred by a single user.
     *
     * @param userId ID of the user whose expenses are being retrieved.
     * @return Status code, a message describing the outcome of the operation,information on the selected user (id, name, email), and information on all the expenses incurred by said user (category name, description, amount, time)."
     */
    public ExpensesByUserDto getExpensesByUser(Integer userId) throws ExpenseManagerException, UnrecoverableKeyException, NoSuchPaddingException, IllegalBlockSizeException, CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, BadPaddingException, InvalidKeyException {
        logger.info("Method getExpensesByUser called with user id "+userId);
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            logger.error("User not found. Throwing error.");
            throw new ExpenseManagerException(1, "User with id="+userId+" does not exist!");
        }
        ExpensesByUserDto expensesByUserDto = new ExpensesByUserDto();
        StatusDto status = new StatusDto(0, "No error!");
        expensesByUserDto.setStatus(status);
        UserDto user = new UserDto();
        user.setUserId(userOptional.get().getUserId());
        user.setFullName(encryptionService.decrypt(userOptional.get().getFirstName()) + " " + encryptionService.decrypt(userOptional.get().getLastName()));
        user.setEmail(encryptionService.decrypt(userOptional.get().getEmail()));
        expensesByUserDto.setUser(user);
        for (Expense expense : expenseRepository.findAllByUserId(userId)) {
            expensesByUserDto.getExpenses().add(new ExpenseDto(expense.getCategoryByCategoryId().getName(), encryptionService.decrypt(expense.getDescription()), expense.getAmount(), expense.getTime()));
        }
        logger.info("Retrieved all expenses made by user with id "+userId+".");
        return expensesByUserDto;
    }

    /**
     * Retrieves all expenses belonging to a single category.
     *
     * @param categoryId ID of the category to which the expenses being retrieved belong.
     * @return Status code, a message describing the outcome of the operation, information on the selected category (id, name, description), and information on all the expenses belonging to said category (id, user name, description, amount, time).
     */
    public ExpensesByCategoryDto getExpensesByCategory(Integer categoryId) throws ExpenseManagerException, UnrecoverableKeyException, NoSuchPaddingException, IllegalBlockSizeException, CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, BadPaddingException, InvalidKeyException {
        logger.info("Method getExpensesByCategory called with category id "+categoryId);
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if(categoryOptional.isEmpty()){
            logger.error("Category not found. Throwing error.");
            throw new ExpenseManagerException(1, "Category with id="+categoryId+" does not exist!");
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
            expensesByCategoryDto.getExpenses().add(new ExpenseDto(expense.getExpenseId(), encryptionService.decrypt(expense.getUsersByUserId().getFirstName()) + " " + encryptionService.decrypt(expense.getUsersByUserId().getLastName()), encryptionService.decrypt(expense.getDescription()), expense.getAmount(), expense.getTime()));
        }
        logger.info("Retrieved all expenses made by category with id "+categoryId+".");
        return expensesByCategoryDto;
    }

    /**
     * Retrieves all expenses incurred between any two points in time.
     *
     * @param timeframeDto JSON object which contains two times in 'yyyy-MM-dd hh:mm:ss' format (from and to) in between which the expenses were made.
     * @return Status code, a message describing the outcome of the operation, and information on all the expenses made in the selected timeframe (id, user full name, category name, description, amount, time).
     */
    public ExpensesByCategoryDto getExpensesByTimeframe(TimeframeDto timeframeDto) throws UnrecoverableKeyException, NoSuchPaddingException, IllegalBlockSizeException, CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, BadPaddingException, InvalidKeyException {
        logger.info("Method getExpensesByTimeframe called with timeframe from "+timeframeDto.getExpenseFrom()+" to " + timeframeDto.getExpenseTo());
        ExpensesByCategoryDto expensesByTimeframeDto = new ExpensesByCategoryDto();
        StatusDto status = new StatusDto(0, "No error!");
        expensesByTimeframeDto.setStatus(status);
        for (Expense expense : expenseRepository.findAllByTimeBetween(timeframeDto.getExpenseFrom(), timeframeDto.getExpenseTo())) {
            expensesByTimeframeDto.getExpenses().add(new ExpenseDto(expense.getExpenseId(), expense.getCategoryByCategoryId().getName(), encryptionService.decrypt(expense.getUsersByUserId().getFirstName()) + " " + encryptionService.decrypt(expense.getUsersByUserId().getLastName()), encryptionService.decrypt(expense.getDescription()), expense.getAmount(), expense.getTime()));
        }
        logger.info("Retrieved all expenses between "+timeframeDto.getExpenseFrom()+" and " + timeframeDto.getExpenseTo());
        return expensesByTimeframeDto;
    }
}
