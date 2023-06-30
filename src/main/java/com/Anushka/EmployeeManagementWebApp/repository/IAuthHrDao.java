package com.Anushka.EmployeeManagementWebApp.repository;

import com.Anushka.EmployeeManagementWebApp.model.AuthTokenHr;
import com.Anushka.EmployeeManagementWebApp.model.Hr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthHrDao extends JpaRepository<AuthTokenHr,Integer> {
    AuthTokenHr findByHr(Hr hr);
}
