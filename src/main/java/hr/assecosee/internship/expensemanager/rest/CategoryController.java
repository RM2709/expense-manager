package hr.assecosee.internship.expensemanager.rest;

import hr.assecosee.internship.expensemanager.core.CategoryService;
import hr.assecosee.internship.expensemanager.dto.CategoryInfoDto;
import hr.assecosee.internship.expensemanager.dto.Dto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST mapping concerning categories.
 */
@RestController
@Log4j
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    /**
     * Retrieves a category.
     *
     * @param categoryId ID of the category to be retrieved.
     * @return JSON object containing a Status code, a message describing the outcome of the operation, and basic information of the retrieved category (id, name, description).
     */
    @GetMapping(value = "/{categoryId}")
    @Operation(summary = "Retrieve a category.",
            description = "Retrieves an existing category from the database. Response contains a status code, a message describing the outcome of the operation " +
                    "and the retrieved category's data (id, name, description).")
    public ResponseEntity<Dto> getCategory(@Parameter(description = "ID of the category to be retrieved", required = true) @PathVariable("categoryId") Integer categoryId) {
        return ResponseEntity.ok(categoryService.getCategory(categoryId));
    }

    /**
     * Creates a new category.
     *
     * @param categoryInfo JSON object which contains the name and description of the category to be created.
     * @return Status code, a message describing the outcome of the operation, and basic information of the created category (id, name, description).
     */
    @PostMapping("")
    @Operation(summary = "Create a new category.",
            description = "Creates a new category and inserts it into the database. Response contains a status code, " +
                    "a message describing the outcome of the operation and the newly created category's data (id, name, description).")
    public ResponseEntity<Dto> createCategory(@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            description = "Name and description of the category to be created.", content = @Content(schema =@Schema(implementation = CategoryInfoDto.class)))
                                                  @RequestBody CategoryInfoDto categoryInfo){
        return ResponseEntity.ok(categoryService.createCategory(categoryInfo));
    }

    /**
     * Updates an existing category.
     *
     * @param categoryId ID of the category to be updated.
     * @param categoryInfo JSON object which contains the name and description of the category to be updated.
     * @return Status code, a message describing the outcome of the operation, and basic information of the updated category (id, name, description).
     */
    @PutMapping(value = "/{categoryId}")
    @Operation(summary = "Update an existing category.", description = "Updates an existing category in the database. Response contains a status code, " +
            "a message describing the outcome of the operation and the updated category's data (id, name, description).")
    public ResponseEntity<Dto> updateCategory(@Parameter(description = "ID of the category to be updated", required = true)
                                                  @PathVariable("categoryId") Integer categoryId, @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            description = "Name and description of the category to be updated.", content = @Content(schema =@Schema(implementation = CategoryInfoDto.class)))
    @RequestBody CategoryInfoDto categoryInfo){
        return ResponseEntity.ok(categoryService.updateCategory(categoryId, categoryInfo));
    }

    /**
     * Deletes an existing category.
     *
     * @param categoryId ID of the category to be deleted.
     * @return Status code and a message describing the outcome of the operation.
     */
    @DeleteMapping(value = "/{categoryId}")
    @Operation(summary = "Delete an existing category.", description = "Deletes an existing category from the database. Response contains a status code " +
            "and message describing the outcome of the operation.")
    public ResponseEntity<Dto> deleteCategory(@Parameter(description = "ID of the category to be deleted", required = true) @PathVariable("categoryId") Integer categoryId){
        return ResponseEntity.ok((categoryService.deleteCategory(categoryId)));
    }

}

