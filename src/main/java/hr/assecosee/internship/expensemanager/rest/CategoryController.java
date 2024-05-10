package hr.assecosee.internship.expensemanager.rest;

import hr.assecosee.internship.expensemanager.core.CategoryService;
import hr.assecosee.internship.expensemanager.database.entity.Category;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@Log4j
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Object> getCategory(@PathVariable Integer categoryId) {
        Map<String, Object> responseBody = new HashMap<>();
        Map<String, Object> status = new HashMap<>();
        Optional<Category> categoryOptional = categoryService.findById(categoryId);
        if(categoryOptional.isPresent()){
            responseBody.put("categoryId", categoryOptional.get().getCategoryId());
            responseBody.put("name", categoryOptional.get().getName());
            responseBody.put("description", categoryOptional.get().getDescription());
            status.put("code", 0);
            status.put("message", "No error!");
        } else{
            status.put("code", 1);
            status.put("message", "Category with id="+categoryId+" not found!");
        }
        responseBody.put("status", status);
        return ResponseEntity.ok(responseBody);
    }

}

