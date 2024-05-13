package hr.assecosee.internship.expensemanager.dto;

import lombok.Data;

@Data
public class ExpenseDto extends StatusDto{
    private Integer expenseId;
    private String userFullName;
    private String categoryName;
    private String description;
    private Double amount;
    private Object time;
}
