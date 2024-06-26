package hr.assecosee.internship.expensemanager.util;

import hr.assecosee.internship.expensemanager.database.entity.Category;
import hr.assecosee.internship.expensemanager.dto.CategoryDto;
import hr.assecosee.internship.expensemanager.dto.StatusDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryMapperTest {

    @Test
    void testConvertCategory(){
        Category testCategory = new Category();
        testCategory.setCategoryId(15);
        testCategory.setName("Test");
        testCategory.setDescription("Test");
        CategoryDto desiredResult = new CategoryDto();
        desiredResult.setStatus(new StatusDto(0, "No error!"));
        desiredResult.setCategoryId(15);
        desiredResult.setName("Test");
        desiredResult.setDescription("Test");
        CategoryDto result = CategoryMapper.getCategoryDto(testCategory);
        assertEquals(desiredResult, result, "Category to CategoryDto conversion unsuccessful");
    }
}
