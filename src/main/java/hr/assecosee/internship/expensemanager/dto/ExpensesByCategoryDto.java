package hr.assecosee.internship.expensemanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExpensesByCategoryDto {

    private StatusDto status;
    private CategoryDto category;
    private List<ExpenseDto> expenses;
    public ExpensesByCategoryDto(){
        expenses = new ArrayList<>();
    }
}
