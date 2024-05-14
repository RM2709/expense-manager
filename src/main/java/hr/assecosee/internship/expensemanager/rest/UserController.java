package hr.assecosee.internship.expensemanager.rest;
import hr.assecosee.internship.expensemanager.core.UserService;
import hr.assecosee.internship.expensemanager.dto.UserInfoDto;
import hr.assecosee.internship.expensemanager.dto.Dto;
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
    public ResponseEntity<Dto> getUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PostMapping("")
    public ResponseEntity<Dto> createUser(@RequestBody UserInfoDto userInfo){
        return ResponseEntity.ok(userService.createUser(userInfo));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Dto> updateUser(@PathVariable Integer userId, @RequestBody UserInfoDto userInfo){
        return ResponseEntity.ok(userService.updateUser(userId, userInfo));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Dto> deleteUser(@PathVariable Integer userId){
        return ResponseEntity.ok((userService.deleteUser(userId)));
    }

}

