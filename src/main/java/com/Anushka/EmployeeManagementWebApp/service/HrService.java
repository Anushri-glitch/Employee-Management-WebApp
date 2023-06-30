package com.Anushka.EmployeeManagementWebApp.service;

import com.Anushka.EmployeeManagementWebApp.dto.SignInInput;
import com.Anushka.EmployeeManagementWebApp.dto.SignInOutput;
import com.Anushka.EmployeeManagementWebApp.dto.SignUpOutput;
import com.Anushka.EmployeeManagementWebApp.dto.signUpHrInput;
import com.Anushka.EmployeeManagementWebApp.model.AuthTokenHr;
import com.Anushka.EmployeeManagementWebApp.model.Employee;
import com.Anushka.EmployeeManagementWebApp.model.Hr;
import com.Anushka.EmployeeManagementWebApp.repository.IEmployeeDao;
import com.Anushka.EmployeeManagementWebApp.repository.IHrDao;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class HrService {

    @Autowired
    IHrDao hrDao;

    @Autowired
    AuthService authService;

    @Autowired
    IEmployeeDao employeeDao;

    public SignUpOutput signUp(signUpHrInput signUpDto) {
        //Check Employee is Registered or not
         Hr hr = hrDao.findFirstByHrEmail(signUpDto.getEmail());

        if(hr != null){
            throw new IllegalStateException("Hr Already Registered!!!");
        }

        //Encryption
        String encryptedPassword = null;
        try{
            encryptedPassword = encryptPassword(signUpDto.getPassword());
        }
        catch(NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }

        //Set Employee
        String hrName = signUpDto.getFirstName() + signUpDto.getLastName();
        hr = new Hr(hrName, signUpDto.getEmail(), encryptedPassword, signUpDto.getAge(), signUpDto.getContact());
        hrDao.save(hr);

        //Token creation and saving
        AuthTokenHr token = new AuthTokenHr(hr);
        authService.saveHrToken(token);

        return new SignUpOutput("Hr registered as - "+ hr.getHrName() , " Hr Created Successfully!!!");
    }

    private String encryptPassword(String password) throws NoSuchAlgorithmException{
        MessageDigest md5 = MessageDigest.getInstance("md5");
        md5.update(password.getBytes());
        byte[] digested = md5.digest();

        return DatatypeConverter.printHexBinary(digested);
    }

    public SignInOutput signIn(SignInInput signInDto) {

        //Check Email
        Hr hr = hrDao.findFirstByHrEmail(signInDto.getSignInMail());
        if(hr == null){
            throw new IllegalStateException("Hr is not Exist!!!");
        }

        //Encrypt Password
        String encryptedPassword;
        try{
            encryptedPassword = encryptPassword(signInDto.getSignInPassword());
        }
        catch(NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }

        //Match it database encrypted password
        boolean isPasswordValid = encryptedPassword.equals(signInDto.getSignInPassword());
        if(!isPasswordValid){
            throw new IllegalStateException("Hr Password is Wrong...Else Hr is not valid!!");
        }

        //Figure Out the token
        AuthTokenHr authTokenHr = authService.getHrToken(hr);

        //Setup output
        return new SignInOutput("Authentication Successful!!!" + hr.getHrName(), authTokenHr.getHrToken());
    }

    public String storeEmployeeInHr(String employeeEmails, String hrEmail) {

        //Check Hr is Valid or not
        Hr hr = hrDao.findFirstByHrEmail(hrEmail);
        if(hr == null){
            throw new IllegalStateException("Hr is not Exist!!!");
        }

        //With the help of Employee Emails Make the List Of Employees and save in Hr Employees List
        List<Employee> employeeList = SetEmployeeList(employeeEmails);
        hr.setEmployeeList(employeeList);
        hrDao.save(hr);

        return hr.getHrName() + " Your EmployeeList is Added!!";
    }

    private List<Employee> SetEmployeeList(String employeeEmails) {

        JSONObject newObject = new JSONObject(employeeEmails);
        String newEmployeeList = newObject.getString("employeeEmails");
        String[] updatedEmpArray = newEmployeeList.split(",");
        List<Employee> employeeArray = new ArrayList<>();

        for(String mails : updatedEmpArray){
            Employee emp = employeeDao.findFirstByEmployeeEmail(mails);
            if(emp != null){
                employeeArray.add(emp);
            }
        }
        return employeeArray;
    }
}
