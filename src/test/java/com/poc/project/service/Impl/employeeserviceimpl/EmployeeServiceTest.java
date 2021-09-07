package com.poc.project.service.Impl.employeeserviceimpl;

import com.poc.project.entity.employee.EmployeeEntity;
import com.poc.project.exceptions.EmployeeAlreadyExistsException;
import com.poc.project.exceptions.EmployeeNotFoundException;
import com.poc.project.exceptions.NoEmployeeDataFoundException;
import com.poc.project.repository.employee.EmployeeRepository;
import com.poc.project.service.Impl.employeeserviceimpl.EmployeeServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeServiceImpl employeeService;

    private EmployeeEntity employeeEntity;
    private List<EmployeeEntity> empList;

    @BeforeEach
    void setup(){
        empList=List.of(new EmployeeEntity("abc@gmail.com","abc",12,"engineer","ajmer",new Date(2020,05,05),"BOB","saving","123434545"),
                new EmployeeEntity("xyz@gmail.com","xyz",12,"manager","bijaynagar",new Date(2020,05,05),"BOB","saving","123434545"));
        employeeEntity= EmployeeEntity.builder()
                .employeeEmail("abc@gmail.com")
                .employeeAddress("ajmer")
                .employeeAge(12)
                .employeeBankAccName("BOB")
                .employeeBankAccNumber("2323233")
                .employeeBankAccType("saving")
                .employeeDesignation("engineer")
                .employeeJoinedAt(new Date(2021, 04,04))
                .employeeName("abc")
                .build();
    }

    @AfterEach
    void tearDown(){
        employeeEntity=null;
        empList=null;
    }

    @Test
    void getAllEmployeesExists(){
        when(employeeRepository.findAll()).thenReturn(empList);
        assertEquals(empList,employeeService.getAllEmployees());
        verify(employeeRepository,times(1)).findAll();
    }

    @Test
    void getAllEmployeesIfNotExistsThenThrowException(){
        when(employeeRepository.findAll()).thenReturn(List.of());
       assertThrows(NoEmployeeDataFoundException.class,()->employeeService.getAllEmployees());
    }

    @Test
    void getEmployeeDataBygivenEmployeeEmailId(){
        when(employeeRepository.findByEmployeeEmail(any())).thenReturn(Optional.of(employeeEntity));
        assertEquals(employeeEntity,employeeService.getEmployeeByEmail("abc@gmail.com"));
        verify(employeeRepository,times(1)).findByEmployeeEmail(any());

    }

    @Test
    void givenEmployeeToSaveIfSavedReturnTrue(){
        when(employeeRepository.findByEmployeeEmail(any())).thenReturn(Optional.empty());
        when(employeeRepository.save(employeeEntity)).thenReturn(employeeEntity);
        assertEquals(true,employeeService.addNewEmployee(employeeEntity));
        verify(employeeRepository,times(1)).save(any());
        verify(employeeRepository,times(1)).findByEmployeeEmail(any());

    }

    @Test
    void givenEmployeeToSaveIfAlreadyExistsThrowException(){
        when(employeeRepository.findByEmployeeEmail("abc@gmail.com")).thenReturn(Optional.of(employeeEntity));
        assertThrows(EmployeeAlreadyExistsException.class,()->employeeService.addNewEmployee(employeeEntity));
        verify(employeeRepository,times(0)).save(any());
        verify(employeeRepository,times(1)).findByEmployeeEmail(any());
    }

    @Test
    void givenEmployeeEmailToDeleteIfEmployeeExists(){
        when(employeeRepository.findByEmployeeEmail(employeeEntity.getEmployeeEmail())).thenReturn(Optional.of(employeeEntity));
        assertEquals(true,employeeService.deleteEmployeeByEmail("abc@gmail.com"));
        verify(employeeRepository,times(1)).findByEmployeeEmail(any());
        verify(employeeRepository,times(1)).delete(any());
    }

    @Test
    void givenEmployeeEmailToDeleteExistingEmployeeIfNotThrowException(){
        when(employeeRepository.findByEmployeeEmail("abc@gmail.com")).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class,()->employeeService.deleteEmployeeByEmail("abc@gmail.com"));
        verify(employeeRepository,times(1)).findByEmployeeEmail(any());
        verify(employeeRepository,times(0)).deleteByEmployeeEmail(any());
    }

    @Test
    void givenEmployeeToUpdateExistingEmployeeWithGivenEmail(){
        when(employeeRepository.findByEmployeeEmail("abc@gmail.com")).thenReturn(Optional.of(employeeEntity));
        assertEquals(true,employeeService.updateEmployeeByEmail(employeeEntity,"abc@gmail.com"));
        verify(employeeRepository,times(1)).findByEmployeeEmail(any());
        verify(employeeRepository,times(1)).save(any());
    }

    @Test
    void givenEmployeeToUpdateExistingEmployeeWithGivenEmailIfDoesNotExistThrowException(){
        when(employeeRepository.findByEmployeeEmail("abc@gmail.com")).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class,()->employeeService.updateEmployeeByEmail(employeeEntity,"abc@gmail.com"));
        verify(employeeRepository,times(1)).findByEmployeeEmail(any());
        verify(employeeRepository,times(0)).deleteByEmployeeEmail(any());
    }

}
