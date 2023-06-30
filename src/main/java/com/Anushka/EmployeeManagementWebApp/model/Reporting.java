package com.Anushka.EmployeeManagementWebApp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table
public class Reporting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer reportId;
    private Integer attendence;
    private String performance;
    private String name;

}
