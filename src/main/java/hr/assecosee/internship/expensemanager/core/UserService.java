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
import java.util.regex.Pattern;

/**
 * Business logic concerning users.
 */
@Service
public class UserService {

    private final EncryptionService encryptionService;
    private final UserRepository userRepository;

    private static final Logger logger = LogManager.getLogger(ExpenseManagerApplication.class);

    @Autowired
    public UserService(EncryptionService encryptionService, UserRepository userRepository){
        this.encryptionService = encryptionService;
        this.userRepository = userRepository;
    }

    /**
     * Retrieves an user.
     *
     * @param userId ID of the user to be retrieved.
     * @return UserDto object containing a Status code, a message describing the outcome of the operation, and basic information of the retrieved user (id, full name, first name, last name, email).
     */
    public UserDto getUser(Integer userId) throws ExpenseManagerException, UnrecoverableKeyException, NoSuchPaddingException, IllegalBlockSizeException, CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, BadPaddingException, InvalidKeyException {
        logger.info("Method getUser called with id " + userId);
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            logger.info("User retrieved.");
            userOptional.get().setFirstName(encryptionService.decrypt(userOptional.get().getFirstName()));
            userOptional.get().setLastName(encryptionService.decrypt(userOptional.get().getLastName()));
            userOptional.get().setEmail(encryptionService.decrypt(userOptional.get().getEmail()));
            return UserMapper.getUserDto(userOptional.get());
        } else{
            logger.error("User not found, throwing exception.");
            throw new ExpenseManagerException(1, "User with id="+userId+" not found!");
        }
    }

    /**
     * Creates a new user.
     *
     * @param userInfo JSON object which contains the first name, last name, and email of the user to be created.
     * @return Status code, a message describing the outcome of the operation, and basic information of the created user (id, full name, first name, last name, email).
     */
    public UserDto createUser(UserInfoDto userInfo) throws ExpenseManagerException, UnrecoverableKeyException, IllegalBlockSizeException, NoSuchPaddingException, CertificateException, BadPaddingException, NoSuchAlgorithmException, KeyStoreException, IOException, InvalidKeyException {
        logger.info("Method createUser called.");
        User newUser = new User();
        if(userInfo.getFirstName()==null){
            userInfo.setFirstName("");
        }
        if(userInfo.getLastName()==null){
            userInfo.setLastName("");
        }
        if(emailNotValid(userInfo.getEmail())){
            logger.error("Email not the right format, throwing exception.");
            throw new ExpenseManagerException(401, "Email has to be in 'xxxxx@yyyy.zzz' format!");
        }
        newUser.setFirstName(encryptionService.encrypt(userInfo.getFirstName()));
        newUser.setLastName(encryptionService.encrypt(userInfo.getLastName()));
        newUser.setEmail(encryptionService.encrypt(userInfo.getEmail()));
        newUser = userRepository.save(newUser);
        logger.info("User created with id " + newUser.getUserId());
        newUser.setFirstName(userInfo.getFirstName());
        newUser.setLastName(userInfo.getLastName());
        newUser.setEmail(userInfo.getEmail());
        return UserMapper.getUserDto(newUser);
    }

    private boolean emailNotValid(String email) {
        String pattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return !Pattern.compile(pattern)
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
    public UserDto updateUser(Integer userId, UserInfoDto userInfo) throws ExpenseManagerException, UnrecoverableKeyException, IllegalBlockSizeException, NoSuchPaddingException, CertificateException, BadPaddingException, NoSuchAlgorithmException, KeyStoreException, IOException, InvalidKeyException {
        logger.info("Method updateUser called with id " + userId);
        if(emailNotValid(userInfo.getEmail())){
            logger.error("Email not the right format, throwing exception.");
            throw new ExpenseManagerException(401, "Email has to be in 'xxxxx@yyyy.zzz' format!");
        }
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            User updatedUser = userOptional.get();
            updatedUser.setFirstName(encryptionService.encrypt(userInfo.getFirstName()));
            updatedUser.setLastName(encryptionService.encrypt(userInfo.getLastName()));
            updatedUser.setEmail(encryptionService.encrypt(userInfo.getEmail()));
            userRepository.save(updatedUser);
            logger.info("User updated.");
            updatedUser.setFirstName(userInfo.getFirstName());
            updatedUser.setLastName(userInfo.getLastName());
            updatedUser.setEmail(userInfo.getEmail());
            return UserMapper.getUserDto(updatedUser);
        } else {
            logger.error("User not found, throwing exception.");
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
        logger.info("Method deleteUser called with id " + userId);
        if(userRepository.findById(userId).isPresent()){
            userRepository.deleteById(userId);
            logger.info("User deleted.");
            return new Response(0, "No error!");
        } else{
            logger.error("User not found, throwing exception.");
            throw new ExpenseManagerException(1, "User with id="+userId+" does not exist!");
        }
    }

}
