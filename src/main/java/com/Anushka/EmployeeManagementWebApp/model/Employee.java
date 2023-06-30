package com.Anushka.EmployeeManagementWebApp.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer employeeId;
    private String employeeName;
    private String employeePhone;
    private String employeeEmail;
    private String jobRole;
    private Integer salary;
    private Integer Age;
    private String password;

    public Employee(String employeeName, String employeePhone, String employeeEmail, String jobRole, Integer salary, Integer Age, String password){
        this.employeeName = employeeName;
        this.employeePhone = employeePhone;
        this.employeeEmail = employeeEmail;
        this.jobRole = jobRole;
        this.salary = salary;
        this.Age = Age;
        this.password = password;
    }
}
