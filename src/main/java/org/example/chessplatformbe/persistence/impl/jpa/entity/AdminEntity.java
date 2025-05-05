package org.example.chessplatformbe.persistence.impl.jpa.entity;

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
public class AdminEntity extends UserEntity {

    @Column(nullable = false)
    private double monthlySalary;

    @Column(nullable = false)
    private LocalDate contractStartDate;

    @Column(nullable = false)
    private LocalDate contractEndDate;

    @Column(nullable = false)
    private String address;
}
