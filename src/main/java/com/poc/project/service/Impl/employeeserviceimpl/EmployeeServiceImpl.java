package com.poc.project.service.Impl.employeeserviceimpl;

import com.poc.project.entity.employee.EmployeeEntity;
import com.poc.project.exceptions.EmployeeAlreadyExistsException;
import com.poc.project.exceptions.EmployeeNotFoundException;
import com.poc.project.exceptions.NoEmployeeDataFoundException;
import com.poc.project.repository.employee.EmployeeRepository;
import com.poc.project.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeEntity> getAllEmployees() throws NoEmployeeDataFoundException {
        List<EmployeeEntity> allEmployees = employeeRepository.findAll();
        if (allEmployees.isEmpty()){
            throw new NoEmployeeDataFoundException("Employees data are not present.");
        }
        return allEmployees;
    }

    @Override
    public EmployeeEntity getEmployeeByEmail(String email) throws EmployeeNotFoundException {
        Optional<EmployeeEntity> byemailId = employeeRepository.findByEmployeeEmail(email);
        return byemailId.orElseThrow(()->new EmployeeNotFoundException("Employee with given email doesn't exist."));
    }

    @Override
    public boolean addNewEmployee(EmployeeEntity employeeEntity) throws EmployeeAlreadyExistsException {
        Optional<EmployeeEntity> byEmployeeEmail = employeeRepository.findByEmployeeEmail(employeeEntity.getEmployeeEmail());
        if(byEmployeeEmail.isPresent()){
            throw new EmployeeAlreadyExistsException("Employee with given email is already exist.");
        }
        employeeRepository.save(employeeEntity);
        return true;
    }

    @Override
    public boolean deleteEmployeeByEmail(String email) throws EmployeeNotFoundException {
        Optional<EmployeeEntity> byEmployeeEmail = employeeRepository.findByEmployeeEmail(email);
        if(byEmployeeEmail.isEmpty()){
            throw new EmployeeNotFoundException("Employee with given email doesn't exist.");
        }
        employeeRepository.delete(byEmployeeEmail.get());
        return true;
    }

    @Override
    public boolean updateEmployeeByEmail(EmployeeEntity employeeEntity,String email) throws EmployeeNotFoundException {
        Optional<EmployeeEntity> byEmployeeEmail = employeeRepository.findByEmployeeEmail(email);
        if (byEmployeeEmail.isEmpty()){
            throw new EmployeeNotFoundException("Employee with given email doesn't exist.");
        }
        employeeRepository.save(employeeEntity);
        return true;
    }


}
