package hr.assecosee.internship.expensemanager.dto;

import lombok.Data;

@Data
public class CategoryInfoDto implements Dto{
    private String name;
    private String description;
}
