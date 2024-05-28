package hr.assecosee.internship.expensemanager.core;

import hr.assecosee.internship.expensemanager.ExpenseManagerApplication;
import hr.assecosee.internship.expensemanager.core.exception.ExpenseManagerException;
import hr.assecosee.internship.expensemanager.database.entity.Category;
import hr.assecosee.internship.expensemanager.database.repository.CategoryRepository;
import hr.assecosee.internship.expensemanager.dto.*;
import hr.assecosee.internship.expensemanager.util.CategoryMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Business logic concerning categories.
 */
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    private static final Logger logger = LogManager.getLogger(ExpenseManagerApplication.class);

    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    /**
     * Retrieves a category.
     *
     * @param categoryId ID of the category to be retrieved.
     * @return CategoryDto object containing a Status code, a message describing the outcome of the operation, and basic information of the retrieved category (id, name, description).
     */
    public CategoryDto getCategory(Integer categoryId) throws ExpenseManagerException {
        logger.info("Method getCategory called with id " + categoryId);
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if(categoryOptional.isPresent()){
            logger.info("Category retrieved.");
            return CategoryMapper.getCategoryDto(categoryOptional.get());
        } else{
            logger.error("Category not found, throwing exception.");
            throw new ExpenseManagerException(1, "Category with id="+categoryId+" not found!");
        }
    }

    /**
     * Creates a new category.
     *
     * @param categoryInfo JSON object which contains the name and description of the category to be created.
     * @return Status code, a message describing the outcome of the operation, and basic information of the created category (id, name, description).
     */
    public CategoryDto createCategory(CategoryInfoDto categoryInfo) {
        logger.info("Method createCategory called.");
        Category newCategory = new Category();
        if(categoryInfo.getName()==null){
            categoryInfo.setName("");
        }
        newCategory.setName(categoryInfo.getName());
        newCategory.setDescription(categoryInfo.getDescription());
        newCategory = categoryRepository.save(newCategory);
        logger.info("Category created with id " + newCategory.getCategoryId());
        return CategoryMapper.getCategoryDto(newCategory);
    }

    /**
     * Updates an existing category.
     *
     * @param categoryId ID of the category to be updated.
     * @param categoryInfo JSON object which contains the name and description of the category to be updated.
     * @return Status code, a message describing the outcome of the operation, and basic information of the updated category (id, name, description).
     */
    public CategoryDto updateCategory(Integer categoryId, CategoryInfoDto categoryInfo) throws ExpenseManagerException {
        logger.info("Method updateCategory called with id " + categoryId);
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if(categoryOptional.isPresent()){
            Category updatedCategory = categoryOptional.get();
            updatedCategory.setName(categoryInfo.getName());
            updatedCategory.setDescription(categoryInfo.getDescription());
            categoryRepository.save(updatedCategory);
            logger.info("Category updated.");
            return CategoryMapper.getCategoryDto(updatedCategory);
        } else {
            logger.error("Category not found, throwing exception.");
            throw new ExpenseManagerException(1, "Category with id="+categoryId+" does not exist!");
        }
    }

    /**
     * Deletes an existing category.
     *
     * @param categoryId ID of the category to be deleted.
     * @return Status code and a message describing the outcome of the operation.
     */
    public Response deleteCategory(Integer categoryId) throws ExpenseManagerException {
        logger.info("Method deleteCategory called with id " + categoryId);
        if(categoryRepository.findById(categoryId).isPresent()){
            categoryRepository.deleteById(categoryId);
            logger.info("Category deleted.");
            return new Response(0, "No error!");
        } else{
            logger.error("Category not found, throwing exception.");
            throw new ExpenseManagerException(1, "Category with id="+categoryId+" does not exist!");
        }
    }
}
