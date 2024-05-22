package hr.assecosee.internship.expensemanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private StatusDto status;
    private Integer userId;
    private String fullName;
    private String firstName;
    private String lastName;
    private String email;
}
