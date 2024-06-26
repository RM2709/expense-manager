package hr.assecosee.internship.expensemanager.core;

import hr.assecosee.internship.expensemanager.database.entity.User;
import hr.assecosee.internship.expensemanager.database.repository.UserRepository;
import hr.assecosee.internship.expensemanager.dto.Response;
import hr.assecosee.internship.expensemanager.dto.UserInfoDto;
import hr.assecosee.internship.expensemanager.util.UserMapper;
import hr.assecosee.internship.expensemanager.core.exception.ExpenseManagerException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    EncryptionService encryptionService;

    @InjectMocks
    UserService userService;


    @Test
    void getUser_UserIdIs15_UserRetrieved() throws ExpenseManagerException, UnrecoverableKeyException, NoSuchPaddingException, IllegalBlockSizeException, CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, BadPaddingException, InvalidKeyException {
        Optional<User> testUser = Optional.of(new User());
        testUser.get().setUserId(15);
        testUser.get().setFirstName("Test");
        testUser.get().setLastName("Test");
        testUser.get().setEmail("Test");
        Mockito.when(userRepository.findById(15)).thenReturn(testUser);
        Mockito.when(encryptionService.decrypt("Test")).thenReturn("Test");
        assertEquals(UserMapper.getUserDto(testUser.get()), userService.getUser(15), "Unable to retrieve user with ID 15");
    }

    @Test
    void getUser_UserIdIs12_UserNotRetrieved(){
        assertThrows(ExpenseManagerException.class, () -> userService.getUser(12), "User retrieved when wrong ID was given");
    }

    @Test
    void createUser_UserInfoProvided_UserCreated() throws ExpenseManagerException, UnrecoverableKeyException, IllegalBlockSizeException, NoSuchPaddingException, CertificateException, BadPaddingException, NoSuchAlgorithmException, KeyStoreException, IOException, InvalidKeyException {
        UserInfoDto userInfo = new UserInfoDto();
        userInfo.setFirstName("Test");
        userInfo.setLastName("Test");
        userInfo.setEmail("Test@email.com");
        userInfo.setBudget(100.0);
        userInfo.setBudgetDays(6);
        User newUser = new User();
        newUser.setFirstName("Test");
        newUser.setLastName("Test");
        newUser.setEmail("Test@email.com");
        newUser.setBudget(100.0);
        newUser.setBudgetDays(6);
        User createdUser = new User();
        createdUser.setUserId(15);
        createdUser.setFirstName("Test");
        createdUser.setLastName("Test");
        createdUser.setEmail("Test@email.com");
        createdUser.setBudget(100.0);
        createdUser.setBudgetDays(6);
        Mockito.when(userRepository.save(newUser)).thenReturn(createdUser);
        Mockito.when(encryptionService.encrypt("Test")).thenReturn("Test");
        Mockito.when(encryptionService.encrypt("Test@email.com")).thenReturn("Test@email.com");
        assertEquals(UserMapper.getUserDto(createdUser), userService.createUser(userInfo), "User not created successfully");
    }

    @Test
    void updateUser_WrongUserInfoProvided_UserNotUpdated(){
        UserInfoDto userInfo = new UserInfoDto();
        userInfo.setFirstName("Test");
        userInfo.setLastName("Test");
        userInfo.setEmail("Test@email.com");
        Mockito.when(userRepository.findById(15)).thenReturn(Optional.empty());
        assertThrows(ExpenseManagerException.class, () -> userService.updateUser(15, userInfo), "User updated successfully when wrong info was provided");
    }

    @Test
    void deleteUser_UserIdProvided_UserDeleted() throws ExpenseManagerException {
        User deletedUser = new User();
        deletedUser.setUserId(15);
        Mockito.when(userRepository.findById(15)).thenReturn(Optional.of(deletedUser));
        assertEquals(new Response(0, "No error!"), userService.deleteUser(15), "User not deleted properly");
    }

}
