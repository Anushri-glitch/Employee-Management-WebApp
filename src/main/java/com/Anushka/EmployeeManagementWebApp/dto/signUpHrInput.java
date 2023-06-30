package com.Anushka.EmployeeManagementWebApp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class signUpHrInput {
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String password;
    private String contact;

    public signUpHrInput(String firstName, String lastName, Integer age, String email, String password, String contact) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.contact = contact;
    }
}
