package hr.assecosee.internship.expensemanager.rest;
import hr.assecosee.internship.expensemanager.core.UserService;
import hr.assecosee.internship.expensemanager.dto.StatusDto;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<StatusDto> getUser(@PathVariable Integer userId) {
        return userService.getUser(userId);
    }

}

