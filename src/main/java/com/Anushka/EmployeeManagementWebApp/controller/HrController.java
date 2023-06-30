package com.Anushka.EmployeeManagementWebApp.controller;

import com.Anushka.EmployeeManagementWebApp.dto.SignInInput;
import com.Anushka.EmployeeManagementWebApp.dto.SignInOutput;
import com.Anushka.EmployeeManagementWebApp.dto.SignUpOutput;
import com.Anushka.EmployeeManagementWebApp.dto.signUpHrInput;
import com.Anushka.EmployeeManagementWebApp.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HrController {

    @Autowired
    HrService hrService;

    @PostMapping(value = "/HrSignUp")
    public SignUpOutput SignUpHr(@RequestBody signUpHrInput signUpDto){
        return hrService.signUp(signUpDto);
    }

    @PostMapping(value = "/HrSignIn")
    public SignInOutput SignInHr(@RequestBody SignInInput signInDto){
        return hrService.signIn(signInDto);
    }

}
