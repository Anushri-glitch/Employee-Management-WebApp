package com.Anushka.EmployeeManagementWebApp.repository;

import com.Anushka.EmployeeManagementWebApp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeDao extends JpaRepository<Employee,Integer> {
    Employee findFirstByEmployeeEmail(String email);
}
