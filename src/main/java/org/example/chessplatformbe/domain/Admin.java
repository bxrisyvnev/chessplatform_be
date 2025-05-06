package org.example.chessplatformbe.domain;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Admin extends User {

    private double monthlySalary;

    private LocalDate contractStartDate;

    private LocalDate contractEndDate;

    private String address;
}
