package com.Anushka.EmployeeManagementWebApp.repository;

import com.Anushka.EmployeeManagementWebApp.model.Reporting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReportingDao extends JpaRepository<Reporting,Integer> {
}
