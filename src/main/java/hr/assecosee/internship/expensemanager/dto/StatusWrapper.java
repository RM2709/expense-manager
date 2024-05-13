package hr.assecosee.internship.expensemanager.dto;

import lombok.Data;

@Data
public class StatusWrapper implements Dto{
    private StatusDto status;

    public StatusWrapper(StatusDto status) {
        this.status = status;
    }
}
