package com.Anushka.EmployeeManagementWebApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Rep {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer reportId;
    private Integer attendence;
    private String performance;
    private String name;

}
