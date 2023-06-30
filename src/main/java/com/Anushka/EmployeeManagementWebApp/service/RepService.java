package com.Anushka.EmployeeManagementWebApp.service;

import com.Anushka.EmployeeManagementWebApp.model.Employee;
import com.Anushka.EmployeeManagementWebApp.model.Hr;
import com.Anushka.EmployeeManagementWebApp.model.Rep;
import com.Anushka.EmployeeManagementWebApp.repository.IEmployeeDao;
import com.Anushka.EmployeeManagementWebApp.repository.IHrDao;
import com.Anushka.EmployeeManagementWebApp.repository.IRepDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepService {


    @Autowired
    IEmployeeDao employeeDao;

    @Autowired
    IRepDao repDao;

    @Autowired
    IHrDao hrDao;

    public String addAttendence(String empEmail) {

        int newAttendence =0;

        //Check that This Employee is existing or not
        Employee employee = employeeDao.findFirstByEmployeeEmail(empEmail);
        if(employee == null){
            throw new IllegalStateException("Employee is Invalid !!...");
        }
        newAttendence++;

        if(newAttendence == 30 ){
            newAttendence = 0;
        }
        Rep rep = new Rep();
        rep.setAttendence(newAttendence);

        //Set The Employee Performance According to Month
        if(newAttendence == 1){
            rep.setPerformance("Congrats!! Your First Day In This Month");
        }
        else if(newAttendence < 20 && newAttendence > 1){
            rep.setPerformance("Please Increase Your Attendence...Your Attendence is low" + newAttendence);
        }
        else if(newAttendence > 20 && newAttendence < 26){
            rep.setPerformance("Excellent Performance-- Continue Your work!!");
        }

        String empName = employee.getEmployeeName();
        rep.setName(empName);

        //Final Save In Repo
        repDao.save(rep);

        return "Your Attendence is Marked !! Thank You " + employee.getEmployeeName();

    }

    public Rep getReporting(String hrEmail, String empEmail) {

        Hr hr = hrDao.findFirstByHrEmail(hrEmail);
        Employee employee = employeeDao.findFirstByEmployeeEmail(empEmail);
        if(hr != null && employee != null){
            String name = employee.getEmployeeName();

            Rep rep = new Rep();
            if(rep.getName().equals(name)){
                return rep;
            }
        }
        return null;
    }
}
