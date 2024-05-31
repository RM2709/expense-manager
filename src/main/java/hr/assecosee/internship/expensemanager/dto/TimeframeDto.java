package hr.assecosee.internship.expensemanager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class TimeframeDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CET")
    private Timestamp expenseFrom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CET")
    private Timestamp expenseTo;

    public TimeframeDto(){}

    public TimeframeDto(Timestamp periodStart, Timestamp periodEnd) {
        this.expenseFrom=periodStart;
        this.expenseTo=periodEnd;
    }
}
