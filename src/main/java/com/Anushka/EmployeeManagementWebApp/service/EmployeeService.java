package com.Anushka.EmployeeManagementWebApp.service;

import com.Anushka.EmployeeManagementWebApp.dto.SignInInput;
import com.Anushka.EmployeeManagementWebApp.dto.SignInOutput;
import com.Anushka.EmployeeManagementWebApp.dto.SignUpInput;
import com.Anushka.EmployeeManagementWebApp.dto.SignUpOutput;
import com.Anushka.EmployeeManagementWebApp.model.AuthTokenEmployee;
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

@Service
public class EmployeeService {

    @Autowired
    IEmployeeDao employeeDao;

    @Autowired
    AuthService authService;

    @Autowired
    IHrDao hrDao;

    public SignUpOutput SignUp(SignUpInput signUpDto, String hrEmail) {
        //Check Hr is valid or not
        Hr hr = hrDao.findFirstByHrEmail(hrEmail);

        if(hr == null){
            throw new IllegalStateException("Hr is not Existed!!!");
        }

        //Check Employee is Registered or not
        Employee employee = employeeDao.findFirstByEmployeeEmail(signUpDto.getEmail());

        if(employee != null){
            throw new IllegalStateException("Employee Already Registered!!!");
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
        String employeeName = signUpDto.getFirstName() + signUpDto.getLastName();
        employee = new Employee(employeeName,signUpDto.getContact(), signUpDto.getEmail(), signUpDto.getJobRole(),
                signUpDto.getSalary(), signUpDto.getAge(), encryptedPassword);
        employeeDao.save(employee);

        //Token creation and saving
        AuthTokenEmployee token = new AuthTokenEmployee(employee);
        authService.saveToken(token);

        return new SignUpOutput("Employee registered as - "+ employee.getEmployeeName() , " Employee Created Successfully!!!");
    }

    private String encryptPassword(String password) throws NoSuchAlgorithmException{
        MessageDigest md5 = MessageDigest.getInstance("md5");
        md5.update(password.getBytes());
        byte[] digested = md5.digest();

        return DatatypeConverter.printHexBinary(digested);
    }

    public SignInOutput SignIn(SignInInput signInDto) {

        //Check Email
        Employee employee = employeeDao.findFirstByEmployeeEmail(signInDto.getSignInMail());
        if(employee == null){
            throw new IllegalStateException("employee is not Exist!!!");
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
        boolean isPasswordValid = encryptedPassword.equals(employee.getPassword());
        if(!isPasswordValid){
            throw new IllegalStateException("Employee Password is Wrong...Else Employee is not valid!!");
        }

        //Figure Out the token
        AuthTokenEmployee authTokenEmployee = authService.getToken(employee);

        //Setup output
        return new SignInOutput("Authentication Successful!!!" + employee.getEmployeeName(), authTokenEmployee.getEmployeeToken());
    }

    public Employee getEmployee(String employeeEmail) {
        Employee employee = employeeDao.findFirstByEmployeeEmail(employeeEmail);

        //verify that Read Operation Performed by Employee
        boolean isEmailValid = employee.getEmployeeEmail().equals(employeeEmail);
        if(!isEmailValid){
            throw new IllegalStateException("Please check that you are Employee");
        }

        return employee;
    }

    public Employee getEmployeeForHr(String hrEmail, String empEmail) {

        //Check hr is Valid or not
        Hr hr = hrDao.findFirstByHrEmail(hrEmail);
        boolean isValidHr = hr.getHrEmail().equals(hrEmail);
        if(!isValidHr){
            throw new IllegalStateException("Please Make sure that you are Hr!!!");
        }

        return getEmployee(empEmail);
    }

    public String updateEmployee(String empData, String hrEmail, String empEmail) {
        //Check hr is Valid or not
        Hr hr = hrDao.findFirstByHrEmail(hrEmail);
        boolean isValidHr = hr.getHrEmail().equals(hrEmail);
        if(!isValidHr){
            throw new IllegalStateException("Please Make sure that you are Hr!!!");
        }

        Employee oldEmployee = getEmployee(empEmail);

        //Convert String Data into JsonObject
        JSONObject jsonEmployee = new JSONObject(empData);
        oldEmployee.setJobRole(jsonEmployee.getString("jobRole"));
        oldEmployee.setSalary(jsonEmployee.getInt("salary"));

        employeeDao.save(oldEmployee);

        return oldEmployee.getEmployeeName() + " is Updated!!";
    }

    public String changePasswordByEmployee(String empPassword, String empEmail) {

        Employee emp = employeeDao.findFirstByEmployeeEmail(empEmail);
        if(emp == null){
            throw new IllegalStateException("Email is incorrect..Make Sure that you are Right Employee");
        }

        JSONObject empJson = new JSONObject(empPassword);
        emp.setPassword(empJson.getString("password"));

        return emp.getEmployeeEmail() + " Your Password is Changed!!!";
    }

    public String deleteEmployeeByHr(String hrEmail, String empEmail) {

        Hr hr = hrDao.findFirstByHrEmail(hrEmail);
        if(hr == null){
            throw new IllegalStateException("Hr is not Exist!!");
        }

        Employee employee = getEmployee(empEmail);
        if(employee == null){
            throw new IllegalStateException("Employee Already Deleted!!");
        }
        employeeDao.delete(employee);
        return employee.getEmployeeName() + " Is Removed!!...Thank You..";
    }
}
