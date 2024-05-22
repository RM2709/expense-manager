package hr.assecosee.internship.expensemanager.util;

import hr.assecosee.internship.expensemanager.database.entity.Category;
import hr.assecosee.internship.expensemanager.dto.CategoryDto;
import hr.assecosee.internship.expensemanager.dto.StatusDto;

/**
 * Helper class for converting a Category object into a suitable CategoryDto object.
 */
public class CategoryMapper {

    /**
     * Converts a Category object into a CategoryDto object.
     *
     * @param category Category class object which is converted into a JSON object of the appropriate form.
     * @return CategoryDto object which is structured correctly (containing category id, name, and description) and ready to be returned in JSON form.
     */
    public static CategoryDto getCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setStatus(new StatusDto(0, "No error!"));
        categoryDto.setCategoryId(category.getCategoryId());
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());
        return categoryDto;
    }
}