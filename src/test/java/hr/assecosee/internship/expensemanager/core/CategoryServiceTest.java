package hr.assecosee.internship.expensemanager.core;

import hr.assecosee.internship.expensemanager.database.entity.Category;
import hr.assecosee.internship.expensemanager.database.entity.User;
import hr.assecosee.internship.expensemanager.database.repository.CategoryRepository;
import hr.assecosee.internship.expensemanager.database.repository.UserRepository;
import hr.assecosee.internship.expensemanager.dto.CategoryInfoDto;
import hr.assecosee.internship.expensemanager.dto.StatusDto;
import hr.assecosee.internship.expensemanager.dto.StatusWrapper;
import hr.assecosee.internship.expensemanager.dto.UserInfoDto;
import hr.assecosee.internship.expensemanager.util.ConvertCategoryDto;
import hr.assecosee.internship.expensemanager.util.ConvertUserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryService categoryService;

    @Test
    public void getCategory_CategoryIdIs15_CategoryRetrieved(){
        Optional<Category> testCategory = Optional.of(new Category());
        testCategory.get().setCategoryId(15);
        testCategory.get().setName("Test");
        testCategory.get().setDescription("Test");
        Mockito.when(categoryRepository.findById(15)).thenReturn(testCategory);
        assert categoryService.getCategory(15).equals(ConvertCategoryDto.getCategoryDto(testCategory.get())) : "Unable to retrieve category with ID 15";
    }

    @Test
    public void getCategory_CategoryIdIs12_CategoryNotRetrieved(){
        StatusWrapper desiredResult = new StatusWrapper(new StatusDto(1, "Category with id=12 not found!"));
        assert categoryService.getCategory(12).equals(desiredResult) : "Category with ID 12 exists";
    }

    @Test
    public void createCategory_CategoryInfoProvided_CategoryCreated(){
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
        assert categoryService.createCategory(categoryInfo).equals(ConvertCategoryDto.getCategoryDto(createdCategory)) : "Category not created successfully";
    }

    @Test
    public void updateCategory_WrongCategoryInfoProvided_CategoryNotUpdated(){
        CategoryInfoDto categoryInfo = new CategoryInfoDto();
        categoryInfo.setName("Test");
        categoryInfo.setDescription("Test");
        Category updatedCategory = new Category();
        updatedCategory.setCategoryId(15);
        updatedCategory.setName("Test");
        updatedCategory.setDescription("Test");
        Mockito.when(categoryRepository.findById(15)).thenReturn(Optional.empty());
        assert !categoryService.updateCategory(15, categoryInfo).equals(ConvertCategoryDto.getCategoryDto(updatedCategory)) : "Category updated successfully when wrong info was given";
    }

    @Test
    public void deleteCategory_CategoryIdProvided_CategoryDeleted(){
        Category deletedCategory = new Category();
        deletedCategory.setCategoryId(15);
        Mockito.when(categoryRepository.findById(15)).thenReturn(Optional.of(deletedCategory));
        assert categoryService.deleteCategory(15).equals(new StatusWrapper(new StatusDto(0, "No error!"))) : "Category not deleted properly";
    }
}
