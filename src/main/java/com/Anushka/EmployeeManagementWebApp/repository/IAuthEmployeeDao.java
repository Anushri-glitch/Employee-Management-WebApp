package com.Anushka.EmployeeManagementWebApp.repository;

import com.Anushka.EmployeeManagementWebApp.model.AuthTokenEmployee;
import com.Anushka.EmployeeManagementWebApp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthEmployeeDao extends JpaRepository<AuthTokenEmployee,Integer> {

    AuthTokenEmployee findByEmployee(Employee employee);
}
