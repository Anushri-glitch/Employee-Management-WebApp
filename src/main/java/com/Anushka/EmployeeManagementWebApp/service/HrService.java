package com.Anushka.EmployeeManagementWebApp.service;

import com.Anushka.EmployeeManagementWebApp.dto.SignInInput;
import com.Anushka.EmployeeManagementWebApp.dto.SignInOutput;
import com.Anushka.EmployeeManagementWebApp.dto.SignUpOutput;
import com.Anushka.EmployeeManagementWebApp.dto.signUpHrInput;
import com.Anushka.EmployeeManagementWebApp.model.AuthTokenHr;
import com.Anushka.EmployeeManagementWebApp.model.Hr;
import com.Anushka.EmployeeManagementWebApp.repository.IHrDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class HrService {

    @Autowired
    IHrDao hrDao;

    @Autowired
    AuthService authService;

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
}
