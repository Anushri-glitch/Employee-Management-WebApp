package com.Anushka.EmployeeManagementWebApp.controller;

import com.Anushka.EmployeeManagementWebApp.model.Reporting;
import com.Anushka.EmployeeManagementWebApp.service.ReportingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RepController {

    ReportingService reportingService;

    @PostMapping(value = "Rep/{empEmail}")
    public String createAttendence(@PathVariable String empEmail){
        return reportingService.addAttendence(empEmail);
    }

    @GetMapping(value = "Rep/HrEmail/{hrEmail}/Employee/{empEmail}")
    public Reporting getAttendence(@PathVariable String hrEmail, @PathVariable String empEmail){
        return reportingService.getReporting(hrEmail,empEmail);
    }
}
