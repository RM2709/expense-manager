package hr.assecosee.internship.expensemanager.util;

import hr.assecosee.internship.expensemanager.database.entity.User;
import hr.assecosee.internship.expensemanager.dto.StatusDto;
import hr.assecosee.internship.expensemanager.dto.UserDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {

    @Test
    void testConvertUser(){
        User testUser = new User();
        testUser.setUserId(15);
        testUser.setFirstName("Test");
        testUser.setLastName("Test");
        testUser.setEmail("Test");
        UserDto desiredResult = new UserDto();
        desiredResult.setStatus(new StatusDto(0, "No error!"));
        desiredResult.setUserId(15);
        desiredResult.setFirstName("Test");
        desiredResult.setLastName("Test");
        desiredResult.setFullName("Test Test");
        desiredResult.setEmail("Test");
        UserDto result = UserMapper.getUserDto(testUser);
        assertEquals(desiredResult, result, "User to UserDto conversion unsuccessful");
    }
}
