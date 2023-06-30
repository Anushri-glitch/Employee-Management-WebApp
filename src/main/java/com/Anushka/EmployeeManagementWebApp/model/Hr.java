package com.Anushka.EmployeeManagementWebApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table
public class Hr {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer hrId;
    private String hrName;

    @Pattern(regexp = "^[a-z0-9]{3,}@[admin]{3,5}[.]{1}[com]{1,3}$")
    private String hrEmail;
    private String hrPassword;
    private Integer age;
    private String hrPhone;

    public Hr(String hrName, String hrEmail, String hrPassword, Integer age, String hrPhone) {
        this.hrName = hrName;
        this.hrEmail = hrEmail;
        this.hrPassword = hrPassword;
        this.age = age;
        this.hrPhone = hrPhone;
    }
}
