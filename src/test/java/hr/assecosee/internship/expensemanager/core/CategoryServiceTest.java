package hr.assecosee.internship.expensemanager.core;

import hr.assecosee.internship.expensemanager.core.exception.ExpenseManagerException;
import hr.assecosee.internship.expensemanager.database.entity.Category;
import hr.assecosee.internship.expensemanager.database.repository.CategoryRepository;
import hr.assecosee.internship.expensemanager.dto.CategoryInfoDto;
import hr.assecosee.internship.expensemanager.dto.Response;
import hr.assecosee.internship.expensemanager.util.CategoryMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryService categoryService;

    @Test
    void getCategory_CategoryIdIs15_CategoryRetrieved() throws ExpenseManagerException {
        Optional<Category> testCategory = Optional.of(new Category());
        testCategory.get().setCategoryId(15);
        testCategory.get().setName("Test");
        testCategory.get().setDescription("Test");
        Mockito.when(categoryRepository.findById(15)).thenReturn(testCategory);
        assertEquals(CategoryMapper.getCategoryDto(testCategory.get()), categoryService.getCategory(15), "Unable to retrieve category with ID 15");
    }

    @Test
    void getCategory_CategoryIdIs12_CategoryNotRetrieved(){
        assertThrows(ExpenseManagerException.class, () -> categoryService.getCategory(12), "Category retrieved when wrong ID was given");
    }

    @Test
    void createCategory_CategoryInfoProvided_CategoryCreated(){
        CategoryInfoDto categoryInfo = new CategoryInfoDto();
        categoryInfo.setName("Test");
        categoryInfo.setDescription("Test");
        Category newCategory = new Category();
        newCategory.setName("Test");
        newCategory.setDescription("Test");
        Category createdCategory = new Category();
        createdCategory.setCategoryId(15);
        createdCategory.setName("Test");
        createdCategory.setDescription("Test");
        Mockito.when(categoryRepository.save(newCategory)).thenReturn(createdCategory);
        assertEquals(CategoryMapper.getCategoryDto(createdCategory), categoryService.createCategory(categoryInfo), "Category not created successfully");
    }

    @Test
    void updateCategory_WrongCategoryInfoProvided_CategoryNotUpdated(){
        CategoryInfoDto categoryInfo = new CategoryInfoDto();
        categoryInfo.setName("Test");
        categoryInfo.setDescription("Test");
        Mockito.when(categoryRepository.findById(15)).thenReturn(Optional.empty());
        assertThrows(ExpenseManagerException.class, () -> categoryService.updateCategory(15, categoryInfo), "Category updated successfully when wrong info was provided");
    }

    @Test
    void deleteCategory_CategoryIdProvided_CategoryDeleted() throws ExpenseManagerException {
        Category deletedCategory = new Category();
        deletedCategory.setCategoryId(15);
        Mockito.when(categoryRepository.findById(15)).thenReturn(Optional.of(deletedCategory));
        assertEquals(new Response(0, "No error!"), categoryService.deleteCategory(15), "Category not deleted properly");
    }
}
