package hr.assecosee.internship.expensemanager.core;

import hr.assecosee.internship.expensemanager.ExpenseManagerApplication;
import hr.assecosee.internship.expensemanager.database.entity.User;
import hr.assecosee.internship.expensemanager.database.repository.UserRepository;
import hr.assecosee.internship.expensemanager.dto.*;
import hr.assecosee.internship.expensemanager.util.UserMapper;
import hr.assecosee.internship.expensemanager.core.exception.ExpenseManagerException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Business logic concerning users.
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    private static final Logger logger = LogManager.getLogger(ExpenseManagerApplication.class);

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
            logger.info("Method getUser called with id " + userId + ". User retrieved.");
            return UserMapper.getUserDto(userOptional.get());
        } else{
            logger.error("Method getUser called with id " + userId + ". User not found, throwing exception.");
            throw new ExpenseManagerException(1, "User with id="+userId+" not found!");
        }
    }

    /**
     * Creates a new user.
     *
     * @param userInfo JSON object which contains the first name, last name, and email of the user to be created.
     * @return Status code, a message describing the outcome of the operation, and basic information of the created user (id, full name, first name, last name, email).
     */
    public UserDto createUser(UserInfoDto userInfo) throws ExpenseManagerException {
        User newUser = new User();
        if(userInfo.getFirstName()==null){
            userInfo.setFirstName("");
        }
        if(userInfo.getLastName()==null){
            userInfo.setLastName("");
        }
        if(!isEmailValid(userInfo.getEmail())){
            logger.error("Method createUser called. Email not the right format, throwing exception.");
            throw new ExpenseManagerException(401, "Email has to be in 'xxxxx@yyyy.zzz' format!");
        }
        newUser.setFirstName(userInfo.getFirstName());
        newUser.setLastName(userInfo.getLastName());
        newUser.setEmail(userInfo.getEmail());
        newUser = userRepository.save(newUser);
        logger.info("Method createUser called. User created with id " + newUser.getUserId());
        return UserMapper.getUserDto(newUser);
    }

    private boolean isEmailValid(String email) {
        String pattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(pattern)
                .matcher(email)
                .matches();
    }

    /**
     * Updates an existing user.
     *
     * @param userId ID of the user to be updated.
     * @param userInfo JSON object which contains the first name, last name, and email of the user to be updated.
     * @return Status code, a message describing the outcome of the operation, and basic information of the updated user (id, full name, first name, last name, email).
     */
    public UserDto updateUser(Integer userId, UserInfoDto userInfo) throws ExpenseManagerException {
        if(!isEmailValid(userInfo.getEmail())){
            logger.error("Method updateUser called. Email not the right format, throwing exception.");
            throw new ExpenseManagerException(401, "Email has to be in 'xxxxx@yyyy.zzz' format!");
        }
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            User updatedUser = userOptional.get();
            updatedUser.setFirstName(userInfo.getFirstName());
            updatedUser.setLastName(userInfo.getLastName());
            updatedUser.setEmail(userInfo.getEmail());
            userRepository.save(updatedUser);
            logger.info("Method updateUser called with id " + userId + ". User updated.");
            return UserMapper.getUserDto(updatedUser);
        } else {
            logger.error("Method updateUser called with id " + userId + ". User not found, throwing exception.");
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
            logger.info("Method deleteUser called with id " + userId + ". User deleted.");
            return new Response(0, "No error!");
        } else{
            logger.error("Method deleteUser called with id " + userId + ". User not found, throwing exception.");
            throw new ExpenseManagerException(1, "User with id="+userId+" does not exist!");
        }
    }

}
