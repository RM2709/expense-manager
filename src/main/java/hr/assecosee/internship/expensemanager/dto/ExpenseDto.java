package hr.assecosee.internship.expensemanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.sql.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExpenseDto implements Dto{
    private StatusDto status;
    private Integer expenseId;
    private String userFullName;
    private String categoryName;
    private String description;
    private Double amount;
    private Date time;

    public ExpenseDto(){}

    public ExpenseDto(String categoryName, String description, Double amount, Date time) {
        this.categoryName = categoryName;
        this.description = description;
        this.amount = amount;
        this.time = time;
    }

    public ExpenseDto(Integer expenseId, String userFullName, String description, Double amount, Date time) {
        this.expenseId = expenseId;
        this.userFullName = userFullName;
        this.description = description;
        this.amount = amount;
        this.time = time;
    }

    public ExpenseDto(Integer expenseId, String categoryName, String userFullName, String description, Double amount, Date time) {
        this.expenseId = expenseId;
        this.userFullName = userFullName;
        this.description = description;
        this.amount = amount;
        this.time = time;
    }
}
