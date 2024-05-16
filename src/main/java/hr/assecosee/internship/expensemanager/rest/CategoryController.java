package hr.assecosee.internship.expensemanager.rest;

import hr.assecosee.internship.expensemanager.core.CategoryService;
import hr.assecosee.internship.expensemanager.dto.CategoryInfoDto;
import hr.assecosee.internship.expensemanager.dto.Dto;
import hr.assecosee.internship.expensemanager.dto.StatusDto;
import hr.assecosee.internship.expensemanager.dto.UserInfoDto;
import jakarta.websocket.server.PathParam;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/", params = "categoryId")
    public ResponseEntity<Dto> getCategory(@PathParam("categoryId") Integer categoryId) {
        return ResponseEntity.ok(categoryService.getCategory(categoryId));
    }

    @PostMapping("")
    public ResponseEntity<Dto> createCategory(@RequestBody CategoryInfoDto categoryInfo){
        return ResponseEntity.ok(categoryService.createCategory(categoryInfo));
    }

    @PutMapping(value = "/", params = "categoryId")
    public ResponseEntity<Dto> updateCategory(@PathParam("categoryId") Integer categoryId, @RequestBody CategoryInfoDto categoryInfo){
        return ResponseEntity.ok(categoryService.updateCategory(categoryId, categoryInfo));
    }

    @DeleteMapping(value = "/", params = "categoryId")
    public ResponseEntity<Dto> deleteCategory(@PathParam("categoryId") Integer categoryId){
        return ResponseEntity.ok((categoryService.deleteCategory(categoryId)));
    }

}

