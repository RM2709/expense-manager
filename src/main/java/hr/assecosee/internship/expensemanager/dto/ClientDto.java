package hr.assecosee.internship.expensemanager.dto;

import lombok.Data;

@Data
public class ClientDto {
    Integer clientId;
    String clientSecret;

    public ClientDto(Integer clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }
}
