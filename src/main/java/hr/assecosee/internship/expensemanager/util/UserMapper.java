package hr.assecosee.internship.expensemanager.util;

import hr.assecosee.internship.expensemanager.database.entity.User;
import hr.assecosee.internship.expensemanager.dto.StatusDto;
import hr.assecosee.internship.expensemanager.dto.UserDto;

/**
 * Helper class for converting a User object into a suitable UserDto object.
 */
public class UserMapper {
    /**
     * Converts a User object into a UserDto object.
     *
     * @param user User class object which is converted into a JSON object of the appropriate form.
     * @return UserDto object which is structured correctly (containing user id, full name, first name, last name, email, budget, and budget days) and ready to be returned in JSON form.
     */
    public static UserDto getUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setStatus(new StatusDto(0, "No error!"));
        userDto.setUserId(user.getUserId());
        userDto.setFullName(user.getFirstName() + " " + user.getLastName());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setBudget(user.getBudget());
        userDto.setBudgetDays(user.getBudgetDays());
        return userDto;
    }
}