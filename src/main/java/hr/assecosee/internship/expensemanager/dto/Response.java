package hr.assecosee.internship.expensemanager.dto;

import lombok.Data;

@Data
public class Response {
    private StatusDto status;

    public Response(Integer code, String message) {

        this.status = new StatusDto(code, message);
    }
}
