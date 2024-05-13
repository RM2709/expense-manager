package hr.assecosee.internship.expensemanager.core;

import hr.assecosee.internship.expensemanager.database.entity.Category;
import hr.assecosee.internship.expensemanager.database.repository.CategoryRepository;
import hr.assecosee.internship.expensemanager.dto.CategoryDto;
import hr.assecosee.internship.expensemanager.dto.Dto;
import hr.assecosee.internship.expensemanager.dto.StatusDto;
import hr.assecosee.internship.expensemanager.dto.StatusWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
}
