package hr.assecosee.internship.expensemanager.dto;

import lombok.Data;

@Data
public class UserDto implements Dto{

    private StatusDto status;
    private Integer userId;
    private String fullName;
    private String firstName;
    private String lastName;
    private String email;
}
