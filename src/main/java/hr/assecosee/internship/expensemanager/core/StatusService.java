package hr.assecosee.internship.expensemanager.core;

import hr.assecosee.internship.expensemanager.database.entity.User;
import hr.assecosee.internship.expensemanager.database.repository.UserRepository;
import hr.assecosee.internship.expensemanager.dto.StatusDto;
import hr.assecosee.internship.expensemanager.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class StatusService {
    private final UserRepository userRepository;

    @Autowired
    public StatusService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public ResponseEntity<StatusDto> status(){
        Map<String, Object> status = new HashMap<>();
        status.put("code", 0);
        status.put("message", "No error!");
        StatusDto statusDto = new StatusDto();
        statusDto.setStatus(status);
        return ResponseEntity.ok(statusDto);
    }
}
