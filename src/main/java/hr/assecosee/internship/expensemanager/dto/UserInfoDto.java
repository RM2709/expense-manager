package hr.assecosee.internship.expensemanager.dto;

import lombok.Data;

@Data
public class UserInfoDto {
    private String firstName;
    private String lastName;
    private String email;
    private Double budget;
    private Integer budgetDays;
}
