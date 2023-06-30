package com.Anushka.EmployeeManagementWebApp.service;

import com.Anushka.EmployeeManagementWebApp.model.AuthTokenEmployee;
import com.Anushka.EmployeeManagementWebApp.model.AuthTokenHr;
import com.Anushka.EmployeeManagementWebApp.model.Employee;
import com.Anushka.EmployeeManagementWebApp.model.Hr;
import com.Anushka.EmployeeManagementWebApp.repository.IAuthEmployeeDao;
import com.Anushka.EmployeeManagementWebApp.repository.IAuthHrDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    IAuthHrDao authHrDao;

    @Autowired
    IAuthEmployeeDao authEmployeeDao;

    public void saveToken(AuthTokenEmployee token) {
        authEmployeeDao.save(token);
    }

    public AuthTokenEmployee getToken(Employee employee) {
        return authEmployeeDao.findByEmployee(employee);
    }


    public void saveHrToken(AuthTokenHr token) {
        authHrDao.save(token);
    }

    public AuthTokenHr getHrToken(Hr hr) {
        return authHrDao.findByHr(hr);
    }
}
