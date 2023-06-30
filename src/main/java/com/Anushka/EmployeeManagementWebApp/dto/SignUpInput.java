package com.Anushka.EmployeeManagementWebApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpInput {

    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String jobRole;
    private Integer salary;
    private String password;
    private String contact;
}
