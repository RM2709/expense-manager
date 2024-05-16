package hr.assecosee.internship.expensemanager.core;

import hr.assecosee.internship.expensemanager.database.entity.Category;
import hr.assecosee.internship.expensemanager.database.repository.CategoryRepository;
import hr.assecosee.internship.expensemanager.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public Dto getCategory(Integer categoryId){
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        Map<String, Object> status = new HashMap<>();
        if(categoryOptional.isPresent()){
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setStatus(new StatusDto(0, "No error!"));
            categoryDto.setCategoryId(categoryOptional.get().getCategoryId());
            categoryDto.setName(categoryOptional.get().getName());
            categoryDto.setDescription(categoryOptional.get().getDescription());
            return categoryDto;
        } else{
            return new StatusWrapper(new StatusDto(1, "Category with id="+categoryId+" not found!"));
        }
    }

    public Dto createCategory(CategoryInfoDto categoryInfo) {
        Category newCategory = new Category();
        if(categoryInfo.getName()==null){
            categoryInfo.setName("");
        }
        newCategory.setName(categoryInfo.getName());
        newCategory.setDescription(categoryInfo.getDescription());
        newCategory = categoryRepository.save(newCategory);
        return getCategoryDto(newCategory);
    }

    public Dto updateCategory(Integer categoryId, CategoryInfoDto categoryInfo) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if(categoryOptional.isPresent()){
            Category updatedCategory = categoryOptional.get();
            updatedCategory.setName(categoryInfo.getName());
            updatedCategory.setDescription(categoryInfo.getDescription());
            categoryRepository.save(updatedCategory);
            return getCategoryDto(updatedCategory);
        } else {
            return new StatusWrapper(new StatusDto(1, "Category with id="+categoryId+" does not exist!"));
        }
    }

    private static CategoryDto getCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setStatus(new StatusDto(0, "No error!"));
        categoryDto.setCategoryId(category.getCategoryId());
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());
        return categoryDto;
    }

    public Dto deleteCategory(Integer categoryId) {
        if(categoryRepository.findById(categoryId).isPresent()){
            categoryRepository.deleteById(categoryId);
            return new StatusWrapper(new StatusDto(0, "No error!"));
        } else{
            return new StatusWrapper(new StatusDto(1, "Category with id="+categoryId+" does not exist!"));
        }
    }
}
