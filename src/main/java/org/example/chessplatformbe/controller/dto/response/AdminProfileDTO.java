package org.example.chessplatformbe.controller.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdminProfileDTO extends UserProfileDTO {
    private double monthlySalary;
    private String contractStartDate;
    private String contractEndDate;
    private String address;
}
