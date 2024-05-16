package hr.assecosee.internship.expensemanager.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class TimeframeDto implements Dto{
    private Date expenseFrom;
    private Date expenseTo;
}
