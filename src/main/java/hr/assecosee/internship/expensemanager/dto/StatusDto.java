package hr.assecosee.internship.expensemanager.dto;

import lombok.Data;

import java.util.Map;

@Data
public class StatusDto implements Dto {
    private Integer code;
    private String message;

    public StatusDto(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
