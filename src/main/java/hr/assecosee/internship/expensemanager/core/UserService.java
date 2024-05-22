package hr.assecosee.internship.expensemanager.core;

import hr.assecosee.internship.expensemanager.database.entity.User;
import hr.assecosee.internship.expensemanager.database.repository.UserRepository;
import hr.assecosee.internship.expensemanager.dto.*;
import hr.assecosee.internship.expensemanager.util.UserMapper;
import hr.assecosee.internship.expensemanager.core.exception.ExpenseManagerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Business logic concerning users.
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * Retrieves an user.
     *
     * @param userId ID of the user to be retrieved.
     * @return UserDto object containing a Status code, a message describing the outcome of the operation, and basic information of the retrieved user (id, full name, first name, last name, email).
     */
    public UserDto getUser(Integer userId) throws ExpenseManagerException {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            return UserMapper.getUserDto(userOptional.get());
        } else{
            throw new ExpenseManagerException(1, "User with id="+userId+" not found!");
        }
    }

    /**
     * Creates a new user.
     *
     * @param userInfo JSON object which contains the first name, last name, and email of the user to be created.
     * @return Status code, a message describing the outcome of the operation, and basic information of the created user (id, full name, first name, last name, email).
     */
    public UserDto createUser(UserInfoDto userInfo) {
        User newUser = new User();
        if(userInfo.getFirstName()==null){
            userInfo.setFirstName("");
        }
        if(userInfo.getLastName()==null){
            userInfo.setLastName("");
        }
        newUser.setFirstName(userInfo.getFirstName());
        newUser.setLastName(userInfo.getLastName());
        newUser.setEmail(userInfo.getEmail());
        newUser = userRepository.save(newUser);
        return UserMapper.getUserDto(newUser);
    }

    /**
     * Updates an existing user.
     *
     * @param userId ID of the user to be updated.
     * @param userInfo JSON object which contains the first name, last name, and email of the user to be updated.
     * @return Status code, a message describing the outcome of the operation, and basic information of the updated user (id, full name, first name, last name, email).
     */
    public UserDto updateUser(Integer userId, UserInfoDto userInfo) throws ExpenseManagerException {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            User updatedUser = userOptional.get();
            updatedUser.setFirstName(userInfo.getFirstName());
            updatedUser.setLastName(userInfo.getLastName());
            updatedUser.setEmail(userInfo.getEmail());
            userRepository.save(updatedUser);
            return UserMapper.getUserDto(updatedUser);
        } else {
            throw new ExpenseManagerException(1, "User with id="+userId+" does not exist!");
        }
    }

    /**
     * Deletes an existing user.
     *
     * @param userId ID of the user to be deleted.
     * @return Status code and a message describing the outcome of the operation.
     */
    public Response deleteUser(Integer userId) throws ExpenseManagerException {
        if(userRepository.findById(userId).isPresent()){
            userRepository.deleteById(userId);
            return new Response(0, "No error!");
        } else{
            throw new ExpenseManagerException(1, "User with id="+userId+" does not exist!");
        }
    }

}
