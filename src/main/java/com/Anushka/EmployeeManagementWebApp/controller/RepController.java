package com.Anushka.EmployeeManagementWebApp.controller;

import com.Anushka.EmployeeManagementWebApp.model.Rep;
import com.Anushka.EmployeeManagementWebApp.service.RepService;
import org.springframework.web.bind.annotation.*;

@RestController
public class RepController {

    RepService repService;

    @PostMapping(value = "Rep/{empEmail}")
    public String createAttendence(@PathVariable String empEmail){
        return repService.addAttendence(empEmail);
    }

    @GetMapping(value = "Rep/HrEmail/{hrEmail}/Employee/{empEmail}")
    public Rep getAttendence(@PathVariable String hrEmail, @PathVariable String empEmail){
        return repService.getReporting(hrEmail,empEmail);
    }
}
