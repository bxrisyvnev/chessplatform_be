/*
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
*/
use crate::user::user;
use chrono::NaiveDate;

#[derive(Debug, Clone)]
pub struct Admin {
    pub user: user,
    pub monthly_salary: f32,
    pub contract_start_date: NaiveDate,
    pub contract_end_date: NaiveDate,
    pub adress: &str,
}
