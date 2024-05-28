package hr.assecosee.internship.expensemanager.rest;
import hr.assecosee.internship.expensemanager.core.UserService;
import hr.assecosee.internship.expensemanager.dto.Response;
import hr.assecosee.internship.expensemanager.dto.UserDto;
import hr.assecosee.internship.expensemanager.dto.UserInfoDto;
import hr.assecosee.internship.expensemanager.core.exception.ExpenseManagerException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

/**
 * REST mapping concerning users.
 */
@RestController
@Log4j
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    /**
     * Retrieves an user.
     *
     * @param userId ID of the user to be retrieved.
     * @return JSON object containing a Status code, a message describing the outcome of the operation, and basic information of the retrieved user (id, full name, first name, last name, email).
     */
    @GetMapping(value = "/{userId}")
    @Operation(summary = "Retrieve an user.",
            description = "Retrieves an existing user from the database. Response contains a status code, a message describing the outcome of the operation " +
                    "and the retrieved user's data (id, name, email).")
    public ResponseEntity<UserDto> getUser(@Parameter(description = "ID of the user to be retrieved", required = true) @PathVariable("userId") Integer userId) throws ExpenseManagerException, UnrecoverableKeyException, NoSuchPaddingException, IllegalBlockSizeException, CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, BadPaddingException, InvalidKeyException {
        return ResponseEntity.ok(userService.getUser(userId));
    }


    /**
     * Creates a new user.
     *
     * @param userInfo JSON object which contains the first name, last name, and email of the user to be created.
     * @return Status code, a message describing the outcome of the operation, and basic information of the created user (id, full name, first name, last name, email).
     */
    @PostMapping("")
    @Operation(summary = "Create a new user.",
            description = "Creates a new user and inserts it into the database. Response contains a status code, a message describing the outcome of the operation " +
                    "and the newly created user's data (id, name, email).")
    public ResponseEntity<UserDto> createUser(@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            description = "First name, last name, and email of the user to be created.",
            content = @Content(schema =@Schema(implementation = UserInfoDto.class))) @RequestBody UserInfoDto userInfo) throws ExpenseManagerException, UnrecoverableKeyException, IllegalBlockSizeException, NoSuchPaddingException, CertificateException, BadPaddingException, NoSuchAlgorithmException, KeyStoreException, IOException, InvalidKeyException {
        return ResponseEntity.ok(userService.createUser(userInfo));
    }

    /**
     * Updates an existing user.
     *
     * @param userId ID of the user to be updated.
     * @param userInfo JSON object which contains the first name, last name, and email of the user to be updated.
     * @return Status code, a message describing the outcome of the operation, and basic information of the updated user (id, full name, first name, last name, email).
     */
    @PutMapping(value = "/{userId}")
    @Operation(summary = "Update an existing user.",
            description = "Updates an existing user in the database. Response contains a status code, a message describing the outcome of the operation " +
                    "and the updated user's data (id, name, email).")
    public ResponseEntity<UserDto> updateUser(@Parameter(description = "ID of the user to be updated", required = true)
                                              @PathVariable("userId") Integer userId,@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            description = "First name, last name, and email of the user to be updated.",
            content = @Content(schema =@Schema(implementation = UserInfoDto.class))) @RequestBody UserInfoDto userInfo) throws ExpenseManagerException, UnrecoverableKeyException, IllegalBlockSizeException, NoSuchPaddingException, CertificateException, BadPaddingException, NoSuchAlgorithmException, KeyStoreException, IOException, InvalidKeyException {
        return ResponseEntity.ok(userService.updateUser(userId, userInfo));
    }

    /**
     * Deletes an existing user.
     *
     * @param userId ID of the user to be deleted.
     * @return Status code and a message describing the outcome of the operation.
     */
    @DeleteMapping(value = "/{userId}")
    @Operation(summary = "Delete an existing user.",
            description = "Deletes an existing user from the database. Response contains a status code, a message describing the outcome of the operation.")
    public ResponseEntity<Response> deleteUser(@Parameter(description = "ID of the user to be deleted", required = true) @PathVariable("userId") Integer userId) throws ExpenseManagerException {
        return ResponseEntity.ok((userService.deleteUser(userId)));
    }

}

