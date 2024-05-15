package hr.assecosee.internship.expensemanager.dto;

import lombok.Data;

@Data
public class ExpenseInfoDto implements Dto{
    private Integer userId;
    private Integer categoryId;
    private String description;
    private Double amount;
    private Object time;
}
