package hr.assecosee.internship.expensemanager.core;

import hr.assecosee.internship.expensemanager.database.entity.User;
import hr.assecosee.internship.expensemanager.database.repository.UserRepository;
import hr.assecosee.internship.expensemanager.dto.StatusDto;
import hr.assecosee.internship.expensemanager.dto.StatusWrapper;
import hr.assecosee.internship.expensemanager.dto.UserDto;
import hr.assecosee.internship.expensemanager.dto.UserInfoDto;
import hr.assecosee.internship.expensemanager.util.ConvertUserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    public void getUser_UserIdIs15_UserRetrieved(){
        Optional<User> testUser = Optional.of(new User());
        testUser.get().setUserId(15);
        testUser.get().setFirstName("Test");
        testUser.get().setLastName("Test");
        testUser.get().setEmail("Test");
        Mockito.when(userRepository.findById(15)).thenReturn(testUser);
        assert userService.getUser(15).equals(ConvertUserDto.getUserDto(testUser.get())) : "Unable to retrieve user with ID 15";
    }

    @Test
    public void getUser_UserIdIs12_UserNotRetrieved(){
        StatusWrapper desiredResult = new StatusWrapper(new StatusDto(1, "User with id=12 not found!"));
        assert userService.getUser(12).equals(desiredResult) : "User with ID 12 exists";
    }

    @Test
    public void createUser_UserInfoProvided_UserCreated(){
        UserInfoDto userInfo = new UserInfoDto();
        userInfo.setFirstName("Test");
        userInfo.setLastName("Test");
        userInfo.setEmail("Test");
        User newUser = new User();
        newUser.setFirstName("Test");
        newUser.setLastName("Test");
        newUser.setEmail("Test");
        User createdUser = new User();
        createdUser.setUserId(15);
        createdUser.setFirstName("Test");
        createdUser.setLastName("Test");
        createdUser.setEmail("Test");
        Mockito.when(userRepository.save(newUser)).thenReturn(createdUser);
        assert userService.createUser(userInfo).equals(ConvertUserDto.getUserDto(createdUser)) : "User not created successfully";
    }

    @Test
    public void updateUser_WrongUserInfoProvided_UserNotUpdated(){
        UserInfoDto userInfo = new UserInfoDto();
        userInfo.setFirstName("Test");
        userInfo.setLastName("Test");
        userInfo.setEmail("Test");
        User updatedUser = new User();
        updatedUser.setUserId(15);
        updatedUser.setFirstName("Test");
        updatedUser.setLastName("Test");
        updatedUser.setEmail("Test");
        Mockito.when(userRepository.findById(15)).thenReturn(Optional.empty());
        assert !userService.updateUser(15, userInfo).equals(ConvertUserDto.getUserDto(updatedUser)) : "User updated successfully when wrong info was provided";
    }

    @Test
    public void deleteUser_UserIdProvided_UserDeleted(){
        User deletedUser = new User();
        deletedUser.setUserId(15);
        Mockito.when(userRepository.findById(15)).thenReturn(Optional.of(deletedUser));
        assert userService.deleteUser(15).equals(new StatusWrapper(new StatusDto(0, "No error!"))) : "User not deleted properly";
    }

}
