package hr.assecosee.internship.expensemanager.dto;

import lombok.Data;

@Data
public class UserInfoDto implements Dto{
    private String firstName;
    private String lastName;
    private String email;
}
