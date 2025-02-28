package org.example.s3chessplatform.domain;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Admin extends User {
    private double monthlySalary;
    private LocalDate contractStartDate;
    private LocalDate contractEndDate;
    private String address;

    public Admin(String username, String password, int age, String displayName, String nationality,
                 double monthlySalary, LocalDate contractStartDate, LocalDate contractEndDate, String address) {
        super(username, password, age, displayName, nationality);
        this.monthlySalary = monthlySalary;
        this.contractStartDate = contractStartDate;
        this.contractEndDate = contractEndDate;
        this.address = address;
    }
}
