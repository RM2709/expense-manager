package hr.assecosee.internship.expensemanager.dto;

import lombok.Data;

@Data
public class CategoryDto implements Dto{
    private StatusDto status;
    private Integer categoryId;
    private String name;
    private String description;
}
