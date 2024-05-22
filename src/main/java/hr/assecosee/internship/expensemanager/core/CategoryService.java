package hr.assecosee.internship.expensemanager.core;

import hr.assecosee.internship.expensemanager.core.exception.ExpenseManagerException;
import hr.assecosee.internship.expensemanager.database.entity.Category;
import hr.assecosee.internship.expensemanager.database.repository.CategoryRepository;
import hr.assecosee.internship.expensemanager.dto.*;
import hr.assecosee.internship.expensemanager.util.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Business logic concerning categories.
 */
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

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
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if(categoryOptional.isPresent()){
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setStatus(new StatusDto(0, "No error!"));
            categoryDto.setCategoryId(categoryOptional.get().getCategoryId());
            categoryDto.setName(categoryOptional.get().getName());
            categoryDto.setDescription(categoryOptional.get().getDescription());
            return categoryDto;
        } else{
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
        Category newCategory = new Category();
        if(categoryInfo.getName()==null){
            categoryInfo.setName("");
        }
        newCategory.setName(categoryInfo.getName());
        newCategory.setDescription(categoryInfo.getDescription());
        newCategory = categoryRepository.save(newCategory);
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
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if(categoryOptional.isPresent()){
            Category updatedCategory = categoryOptional.get();
            updatedCategory.setName(categoryInfo.getName());
            updatedCategory.setDescription(categoryInfo.getDescription());
            categoryRepository.save(updatedCategory);
            return CategoryMapper.getCategoryDto(updatedCategory);
        } else {
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
        if(categoryRepository.findById(categoryId).isPresent()){
            categoryRepository.deleteById(categoryId);
            return new Response(0, "No error!");
        } else{
            throw new ExpenseManagerException(1, "Category with id="+categoryId+" does not exist!");
        }
    }
}
