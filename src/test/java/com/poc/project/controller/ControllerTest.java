package com.poc.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.project.entity.employee.EmployeeEntity;
import com.poc.project.exceptions.EmployeeAlreadyExistsException;
import com.poc.project.exceptions.EmployeeNotFoundException;
import com.poc.project.exceptions.NoEmployeeDataFoundException;
import com.poc.project.repository.employee.EmployeeRepository;
import com.poc.project.service.EmployeeService;
import com.poc.project.service.StudentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.engine.TestExecutionResult;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = Controller.class)
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private StudentService studentService;

    @InjectMocks
    private Controller controller;

    @Autowired
    private ObjectMapper objectMapper;

    private EmployeeEntity employeeEntity;

    @BeforeEach
    void setup(){
        employeeEntity= EmployeeEntity.builder()
                .employeeEmail("abc@gmail.com")
                .employeeAddress("ajmer")
                .employeeAge(19)
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
    }

    @Test
    void getAllEmployeesExistInDatabase() throws Exception{
        when(employeeService.getAllEmployees()).thenReturn(List.of(employeeEntity));
        mockMvc.perform(get("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(List.of(employeeEntity).toString()))
                .andExpect(status().isOk());

    }

    @Test
    void getAllEmployeesIfNotPresentThenThrowAnException() throws Exception{
        when(employeeService.getAllEmployees()).thenThrow(new NoEmployeeDataFoundException("No data"));
        mockMvc.perform(get("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(List.of(employeeEntity).toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenEmployeeEmailAsIdToGetEmployeeData() throws Exception{
        when(employeeService.getEmployeeByEmail("abc@gmail.com")).thenReturn(employeeEntity);
        mockMvc.perform(get("/api/v1/employee/{email}","abc@gmail.com"))
                .andExpect(status().isOk());
    }

    @Test
    void givenEmployeeEmailAsIdWhichIsNotPresentInDBThenThrowAnException() throws Exception{
        when(employeeService.getEmployeeByEmail("abc@gmail.com")).thenThrow(new EmployeeNotFoundException("No Employee data is present with given ID."));
        mockMvc.perform(get("/api/v1/employee/{email}","abc@gmail.com"))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenEmployeeDetailsToSave() throws Exception{
        when(employeeService.addNewEmployee(employeeEntity)).thenReturn(true);
        mockMvc.perform(post("/api/v1/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeEntity)))
                .andExpect(status().isCreated());

    }

    @Test
    void givenEmployeeDetailToSaveButAlreadyPresentWithIDThenThrowException() throws Exception{
        when(employeeService.addNewEmployee(employeeEntity)).thenThrow(new EmployeeAlreadyExistsException("Employee is Already present."));
        mockMvc.perform(post("/api/v1/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeEntity)))
                .andExpect(status().isConflict());
    }

    @Test
    void givenEmployeeDetailsToUpdateWithEmployeeEmailAsID() throws Exception{
        when(employeeService.updateEmployeeByEmail(employeeEntity,"abc@gmail.com")).thenReturn(true);
        mockMvc.perform(put("/api/v1/employee/{email}","abc@gmail.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeEntity)))
                .andExpect(status().isAccepted());
    }

    @Test
    void givenEmployeeDetailsAlreadyPresentThenThrowAnException() throws Exception{
        when(employeeService.updateEmployeeByEmail(employeeEntity,"abc@gmail.com")).thenThrow(new EmployeeNotFoundException("Employee data is not present with given ID."));
        mockMvc.perform(put("/api/v1/employee/{email}","abc@gmail.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenEmployeeEmailAsIDToDeleteExistingEmployee() throws Exception{
        when(employeeService.deleteEmployeeByEmail("abc@gmail.com")).thenReturn(true);
        mockMvc.perform(delete("/api/v1/employee/{email}","abc@gmail.com"))
                .andExpect(status().isAccepted());
    }

    @Test
    void givenEmployeeEmailAsIDToDeleteNotAvailableEmployeeThenThrowAnException() throws Exception{
        when(employeeService.deleteEmployeeByEmail("abc@gmail.com")).thenThrow(new EmployeeNotFoundException("Employee is not present."));
        mockMvc.perform(delete("/api/v1/employee/{email}","abc@gmail.com"))
                .andExpect(status().isNotFound());
    }

}
