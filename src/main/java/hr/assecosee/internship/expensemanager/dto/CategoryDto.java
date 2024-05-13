package hr.assecosee.internship.expensemanager.dto;

import lombok.Data;

@Data
public class CategoryDto extends StatusDto{
    private Integer categoryId;
    private String name;
    private String description;
}
