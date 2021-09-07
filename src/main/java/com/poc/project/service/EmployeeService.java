package com.poc.project.service;

import com.poc.project.entity.employee.EmployeeEntity;
import com.poc.project.exceptions.EmployeeAlreadyExistsException;
import com.poc.project.exceptions.EmployeeNotFoundException;
import com.poc.project.exceptions.NoEmployeeDataFoundException;

import java.util.List;


public interface EmployeeService {

    List<EmployeeEntity> getAllEmployees() throws NoEmployeeDataFoundException;
    EmployeeEntity getEmployeeByEmail(String email) throws EmployeeNotFoundException;
    boolean addNewEmployee(EmployeeEntity employeeEntity) throws EmployeeAlreadyExistsException;
    boolean deleteEmployeeByEmail(String email) throws EmployeeNotFoundException;
    boolean updateEmployeeByEmail(EmployeeEntity employeeEntity,String email) throws EmployeeNotFoundException;

}
