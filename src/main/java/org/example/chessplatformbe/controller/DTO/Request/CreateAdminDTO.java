package org.example.chessplatformbe.controller.DTO.Request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateAdminDTO extends CreateUserDTO {

    private double monthlySalary;

    private LocalDate contractStartDate;

    private LocalDate contractEndDate;

    private String address;
}
