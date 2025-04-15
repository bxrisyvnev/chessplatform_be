package org.example.chessplatformbe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Admin extends User {

    @Column(nullable = false)
    private double monthlySalary;

    @Column(nullable = false)
    private LocalDate contractStartDate;

    @Column(nullable = false)
    private LocalDate contractEndDate;

    @Column(nullable = false)
    private String address;
}
