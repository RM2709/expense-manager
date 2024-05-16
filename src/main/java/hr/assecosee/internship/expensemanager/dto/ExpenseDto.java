package hr.assecosee.internship.expensemanager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExpenseDto implements Dto{
    private StatusDto status;
    private Integer expenseId;
    private String userFullName;
    private String categoryName;
    private String description;
    private Double amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CET")
    private Timestamp time;

    public ExpenseDto(){}

    public ExpenseDto(String categoryName, String description, Double amount, Timestamp time) {
        this.categoryName = categoryName;
        this.description = description;
        this.amount = amount;
        this.time = time;
    }

    public ExpenseDto(Integer expenseId, String userFullName, String description, Double amount, Timestamp time) {
        this.expenseId = expenseId;
        this.userFullName = userFullName;
        this.description = description;
        this.amount = amount;
        this.time = time;
    }

    public ExpenseDto(Integer expenseId, String categoryName, String userFullName, String description, Double amount, Timestamp time) {
        this.expenseId = expenseId;
        this.userFullName = userFullName;
        this.description = description;
        this.amount = amount;
        this.time = time;
    }
}
