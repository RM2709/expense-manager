package hr.assecosee.internship.expensemanager.core;

import hr.assecosee.internship.expensemanager.database.entity.User;
import hr.assecosee.internship.expensemanager.database.repository.UserRepository;
import hr.assecosee.internship.expensemanager.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Dto getUser(Integer userId){
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            return getUserDto(userOptional.get());
        } else{
            return new StatusWrapper(new StatusDto(1, "User with id="+userId+" not found!"));
        }
    }

    public Dto createUser(UserInfoDto userInfo) {
        User newUser = new User();
        newUser.setFirstName(userInfo.getFirstName());
        newUser.setLastName(userInfo.getLastName());
        newUser.setEmail(userInfo.getEmail());
        newUser = userRepository.save(newUser);
        return getUserDto(newUser);
    }

    public Dto updateUser(Integer userId, UserInfoDto userInfo) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            User updatedUser = userOptional.get();
            updatedUser.setFirstName(userInfo.getFirstName());
            updatedUser.setLastName(userInfo.getLastName());
            updatedUser.setEmail(userInfo.getEmail());
            userRepository.save(updatedUser);
            return getUserDto(updatedUser);
        } else {
            return new StatusWrapper(new StatusDto(1, "User with id="+userId+" does not exist!"));
        }
    }

    private static UserDto getUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setStatus(new StatusDto(0, "No error!"));
        userDto.setUserId(user.getUserId());
        userDto.setFullName(user.getFirstName() + " " + user.getLastName());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    public Dto deleteUser(Integer userId) {
        if(userRepository.findById(userId).isPresent()){
            userRepository.deleteById(userId);
            return new StatusWrapper(new StatusDto(0, "No error!"));
        } else{
            return new StatusWrapper(new StatusDto(1, "User with id="+userId+" does not exist!"));
        }

    }
}
