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
public class AuthTokenHr {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer hrTokenId;
    private String hrToken;
    private LocalDate hrTokenCreationDate;

    @OneToOne
    @JoinColumn(nullable = false, name = "hrId")
    private Hr hr;

    public AuthTokenHr(Hr hr) {
        this.hr = hr;
        this.hrTokenCreationDate = LocalDate.now();
        this.hrToken = UUID.randomUUID().toString();
    }
}
