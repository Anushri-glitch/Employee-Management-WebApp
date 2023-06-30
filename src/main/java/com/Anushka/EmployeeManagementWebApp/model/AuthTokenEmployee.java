package com.Anushka.EmployeeManagementWebApp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;


@Data
@NoArgsConstructor
@Entity
@Table
public class AuthTokenEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer employeeTokenId;
    private String employeeToken;
    private LocalDate employeeTokenCreationDate;

    @OneToOne
    @JoinColumn(nullable = false, name = "employeeId")
    private Employee employee;

    public AuthTokenEmployee(Employee employee) {
        this.employee = employee;
        this.employeeTokenCreationDate = LocalDate.now();
        this.employeeToken = UUID.randomUUID().toString();
    }
}
