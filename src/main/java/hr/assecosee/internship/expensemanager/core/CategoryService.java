package hr.assecosee.internship.expensemanager.core;

import hr.assecosee.internship.expensemanager.database.entity.Category;
import hr.assecosee.internship.expensemanager.database.repository.CategoryRepository;
import hr.assecosee.internship.expensemanager.dto.CategoryDto;
import hr.assecosee.internship.expensemanager.dto.StatusDto;
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

    public ResponseEntity<StatusDto> getCategory(Integer categoryId){
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        Map<String, Object> status = new HashMap<>();
        if(categoryOptional.isPresent()){
            status.put("code", 0);
            status.put("message", "No error!");
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setStatus(status);
            categoryDto.setCategoryId(categoryOptional.get().getCategoryId());
            categoryDto.setName(categoryOptional.get().getName());
            categoryDto.setDescription(categoryOptional.get().getDescription());
            return ResponseEntity.ok(categoryDto);
        } else{
            StatusDto categoryDto = new StatusDto();
            status.put("code", 1);
            status.put("message", "Category with id="+categoryId+" not found!");
            categoryDto.setStatus(status);
            return ResponseEntity.ok(categoryDto);
        }
    }
}
