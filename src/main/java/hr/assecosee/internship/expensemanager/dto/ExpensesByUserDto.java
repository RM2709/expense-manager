package hr.assecosee.internship.expensemanager.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExpensesByUserDto {
    private StatusDto status;
    private UserDto user;
    private List<ExpenseDto> expenses;

    public ExpensesByUserDto(){
        expenses = new ArrayList<>();
    }

}
