package hr.assecosee.internship.expensemanager.core;

import hr.assecosee.internship.expensemanager.database.entity.User;
import hr.assecosee.internship.expensemanager.database.repository.UserRepository;
import hr.assecosee.internship.expensemanager.dto.StatusDto;
import hr.assecosee.internship.expensemanager.dto.UserDto;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public ResponseEntity<StatusDto> getUser(Integer userId){
        Optional<User> userOptional = userRepository.findById(userId);
        Map<String, Object> status = new HashMap<>();
        if(userOptional.isPresent()){
            status.put("code", 0);
            status.put("message", "No error!");
            UserDto userDto = new UserDto();
            userDto.setStatus(status);
            userDto.setUserId(userOptional.get().getUserId());
            userDto.setFullName(userOptional.get().getFirstName() + " " + userOptional.get().getLastName());
            userDto.setFirstName(userOptional.get().getFirstName());
            userDto.setLastName(userOptional.get().getLastName());
            userDto.setEmail(userOptional.get().getEmail());
            return ResponseEntity.ok(userDto);
        } else{
            StatusDto userDto = new StatusDto();
            status.put("code", 1);
            status.put("message", "User with id="+userId+" not found!");
            userDto.setStatus(status);
            return ResponseEntity.ok(userDto);
        }
    }
}
