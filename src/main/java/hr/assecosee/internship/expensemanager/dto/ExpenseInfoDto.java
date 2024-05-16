package hr.assecosee.internship.expensemanager.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class ExpenseInfoDto implements Dto{
    private Integer userId;
    private Integer categoryId;
    private String description;
    private Double amount;
    private Date time;
}
