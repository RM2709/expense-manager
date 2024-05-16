package hr.assecosee.internship.expensemanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusDto implements Dto {
    private Integer code;
    private String message;

    public StatusDto(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
