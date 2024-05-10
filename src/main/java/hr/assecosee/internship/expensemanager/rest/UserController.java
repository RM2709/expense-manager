package hr.assecosee.internship.expensemanager.rest;
import hr.assecosee.internship.expensemanager.core.UserService;
import hr.assecosee.internship.expensemanager.database.entity.User;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@RestController
@Log4j
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUser(@PathVariable Integer userId) {
        Map<String, Object> responseBody = new HashMap<>();
        Map<String, Object> status = new HashMap<>();
        Optional<User> userOptional = userService.findById(userId);
        if(userOptional.isPresent()){
            responseBody.put("userId", userOptional.get().getUserId());
            responseBody.put("fullName", userOptional.get().getFirstName() + " " + userOptional.get().getLastName());
            responseBody.put("firstName", userOptional.get().getFirstName());
            responseBody.put("lastName", userOptional.get().getLastName());
            responseBody.put("email", userOptional.get().getEmail());
            status.put("code", 0);
            status.put("message", "No error!");
        } else{
            status.put("code", 1);
            status.put("message", "User with id="+userId+" not found!");
        }
        responseBody.put("status", status);
        return ResponseEntity.ok(responseBody);
    }

}

