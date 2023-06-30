package com.Anushka.EmployeeManagementWebApp.controller;

import com.Anushka.EmployeeManagementWebApp.dto.SignInInput;
import com.Anushka.EmployeeManagementWebApp.dto.SignInOutput;
import com.Anushka.EmployeeManagementWebApp.dto.SignUpInput;
import com.Anushka.EmployeeManagementWebApp.dto.SignUpOutput;
import com.Anushka.EmployeeManagementWebApp.model.Employee;
import com.Anushka.EmployeeManagementWebApp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    //Create Employee
    @PostMapping(value = "/SignUp/HrEmail/{HrEmail}")
    public SignUpOutput SignUp(@RequestBody SignUpInput signUpDto, @PathVariable String HrEmail){
        return employeeService.SignUp(signUpDto, HrEmail);
    }

    @PostMapping(value = "/SignIn")
    public SignInOutput SignIn(@RequestBody SignInInput signInDto){
        return employeeService.SignIn(signInDto);
    }

    //Get Employee By Employee
    @GetMapping(value = "/getEmployee/employeeEmail/{employeeEmail}")
    public Employee getEmployee(@PathVariable String employeeEmail){
        return employeeService.getEmployee(employeeEmail);
    }

    //Get Employee Details By Hr
    @GetMapping(value = "/getEmployee/HrEmail/{hrEmail}/empEmail/{empEmail}")
    public Employee getEmployeeForHr(@PathVariable String hrEmail, @PathVariable String empEmail){
        return employeeService.getEmployeeForHr(hrEmail,empEmail);
    }

    //Update Employee JobRole and Salary By Hr
    @PutMapping(value = "/update/hrEmail/{hrEmail}/empEmail/{empEmail}")
    public String updateEmployeeByHr(@RequestBody String empData, @PathVariable String hrEmail, @PathVariable String empEmail){
        return employeeService.updateEmployee(empData,hrEmail,empEmail);
    }

    //Change Password By Employee
    @PatchMapping(value = "/changePassword/{empEmail}")
    public String changePasswordByEmployee(@RequestBody String empPassword, @PathVariable String empEmail){
        return employeeService.changePasswordByEmployee(empPassword,empEmail);
    }

    @DeleteMapping(value = "/delete/hrEmail/{hrEmail}/{empEmail}")
    public String deleteEmployeeByHr(@PathVariable String hrEmail, @PathVariable String empEmail){
        return employeeService.deleteEmployeeByHr(hrEmail,empEmail);
    }
}
