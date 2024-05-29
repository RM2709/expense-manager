package hr.assecosee.internship.expensemanager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class BudgetDto {

    private StatusDto status;
    private Integer userId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CET")
    private Timestamp currentBudgetPeriodStart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CET")
    private Timestamp currentBudgetPeriodEnd ;
    private Double remainingAmount;
}
