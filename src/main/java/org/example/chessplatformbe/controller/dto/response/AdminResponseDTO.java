package org.example.chessplatformbe.controller.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Data
@EqualsAndHashCode(callSuper = true)
@Getter
public class AdminResponseDTO extends UserResponseDTO {

    private double monthlySalary;

    private String contractStartDate;

    private String contractEndDate;

    private String address;
}
