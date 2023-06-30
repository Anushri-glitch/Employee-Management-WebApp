package com.Anushka.EmployeeManagementWebApp.repository;

import com.Anushka.EmployeeManagementWebApp.model.Hr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHrDao extends JpaRepository<Hr, Integer> {
    Hr findFirstByHrEmail(String hrEmail);
}
