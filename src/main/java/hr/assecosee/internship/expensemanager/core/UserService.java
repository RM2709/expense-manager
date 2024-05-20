package hr.assecosee.internship.expensemanager.core;

import hr.assecosee.internship.expensemanager.database.entity.User;
import hr.assecosee.internship.expensemanager.database.repository.UserRepository;
import hr.assecosee.internship.expensemanager.dto.*;
import hr.assecosee.internship.expensemanager.util.ConvertUserDto;
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
    public Dto getUser(Integer userId){
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            return ConvertUserDto.getUserDto(userOptional.get());
        } else{
            return new StatusWrapper(new StatusDto(1, "User with id="+userId+" not found!"));
        }
    }

    /**
     * Creates a new user.
     *
     * @param userInfo JSON object which contains the first name, last name, and email of the user to be created.
     * @return Status code, a message describing the outcome of the operation, and basic information of the created user (id, full name, first name, last name, email).
     */
    public Dto createUser(UserInfoDto userInfo) {
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
        return ConvertUserDto.getUserDto(newUser);
    }

    /**
     * Updates an existing user.
     *
     * @param userId ID of the user to be updated.
     * @param userInfo JSON object which contains the first name, last name, and email of the user to be updated.
     * @return Status code, a message describing the outcome of the operation, and basic information of the updated user (id, full name, first name, last name, email).
     */
    public Dto updateUser(Integer userId, UserInfoDto userInfo) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            User updatedUser = userOptional.get();
            updatedUser.setFirstName(userInfo.getFirstName());
            updatedUser.setLastName(userInfo.getLastName());
            updatedUser.setEmail(userInfo.getEmail());
            userRepository.save(updatedUser);
            return ConvertUserDto.getUserDto(updatedUser);
        } else {
            return new StatusWrapper(new StatusDto(1, "User with id="+userId+" does not exist!"));
        }
    }

    /**
     * Deletes an existing user.
     *
     * @param userId ID of the user to be deleted.
     * @return Status code and a message describing the outcome of the operation.
     */
    public Dto deleteUser(Integer userId) {
        if(userRepository.findById(userId).isPresent()){
            userRepository.deleteById(userId);
            return new StatusWrapper(new StatusDto(0, "No error!"));
        } else{
            return new StatusWrapper(new StatusDto(1, "User with id="+userId+" does not exist!"));
        }
    }

}
