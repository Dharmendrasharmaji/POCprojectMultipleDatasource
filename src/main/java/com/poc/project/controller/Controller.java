package com.poc.project.controller;

import com.poc.project.entity.employee.EmployeeEntity;
import com.poc.project.entity.student.StudentEntity;
import com.poc.project.exceptions.EmployeeNotFoundException;
import com.poc.project.service.EmployeeService;
import com.poc.project.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Validated
public class Controller {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private StudentService studentService;

//    Endpoints for Employee

    @GetMapping("/employees")
    public ResponseEntity<?> getAllEmployees() {
        List<EmployeeEntity> allEmployees = employeeService.getAllEmployees();
        return new ResponseEntity<>(allEmployees, HttpStatus.OK);
    }

    @GetMapping("/employee/{email}")
    public ResponseEntity<?> getEmployeeByEmail(@Email(message = "Given email must be well formed email address.") @PathVariable String email) {

        EmployeeEntity employeeByEmail = employeeService.getEmployeeByEmail(email.toLowerCase());
        if (employeeByEmail == null) {
            throw new EmployeeNotFoundException("No Employee exists in database with given email");
        }
        return new ResponseEntity<>(employeeByEmail, HttpStatus.OK);


    }

    @PostMapping("/employee")
    public ResponseEntity<?> addEmployee(@Valid @RequestBody EmployeeEntity employeeEntity) {
        if (employeeEntity == null) {
            return new ResponseEntity<>("Employee data wasn't provided", HttpStatus.BAD_REQUEST);
        } else {
            employeeEntity.setEmployeeEmail(employeeEntity.getEmployeeEmail().toLowerCase());
            boolean check = employeeService.addNewEmployee(employeeEntity);
            if (check)
                return new ResponseEntity<>("Employee is added successfully", HttpStatus.CREATED);
            else
                return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/employee/{email}")
    public ResponseEntity<?> deleteEmployeeByEmail(@Email(message = "Given email must be well formed email address.") @PathVariable String email) {
        boolean check = employeeService.deleteEmployeeByEmail(email.toLowerCase());
        if (check) {
            return new ResponseEntity<>("Employee is successfully deleted", HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/employee/{email}")
    public ResponseEntity updateEmployeeByEmail(@Email(message = "Given email must be well formed email address.")  @PathVariable String email, @Valid @RequestBody EmployeeEntity employeeEntity) {
        if (!email.equalsIgnoreCase(employeeEntity.getEmployeeEmail())) {
            return new ResponseEntity("Employee emailId should not be changed.", HttpStatus.BAD_REQUEST);
        }
        employeeEntity.setEmployeeEmail(email.toLowerCase());
        if (employeeService.updateEmployeeByEmail(employeeEntity, email.toLowerCase())) {
            return new ResponseEntity("Employee is updated successfully", HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
    }

//    Endpoints for Student

    @GetMapping("/students")
    public ResponseEntity<?> getAllStudents() {
        List<StudentEntity> allStudents = studentService.getAllStudents();
        return new ResponseEntity<>(allStudents, HttpStatus.OK);
    }

    @GetMapping("/student/{email}")
    public ResponseEntity<?> getStudentByEmail(@Email(message = "Given email must be well formed email address.") @PathVariable String email) {
        StudentEntity studentByEmail = studentService.getStudentByEmail(email.toLowerCase());
        return new ResponseEntity<>(studentByEmail, HttpStatus.OK);
    }

    @PostMapping("/student")
    public ResponseEntity addNewStudent(@Valid @RequestBody StudentEntity studentEntity) {
        if (studentEntity == null) {
            return new ResponseEntity<>("Insuffecient Data", HttpStatus.BAD_REQUEST);
        } else {
            studentEntity.setStudentEmail(studentEntity.getStudentEmail().toLowerCase());
            boolean flag = studentService.addStudent(studentEntity);
            if (!flag)
                return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
            return new ResponseEntity("Student is successfully Added.", HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/student/{email}")
    public ResponseEntity<?> deleteStudentByEmail(@Email(message = "Given email must be well formed email address.") @PathVariable String email) {
        boolean check = studentService.deleteStudent(email.toLowerCase());
        if (check) {
            return new ResponseEntity<>("Student is successfully deleted", HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/student/{email}")
    public ResponseEntity updateStudentByEmail(@Email(message = "Given email must be well formed email address.")  @PathVariable String email,@Valid @RequestBody StudentEntity studentEntity) {

        if (!email.equalsIgnoreCase(studentEntity.getStudentEmail())) {
            return new ResponseEntity("Student email is not matching with provided data.", HttpStatus.BAD_REQUEST);
        }
        studentEntity.setStudentEmail(email.toLowerCase());
        if (studentService.updateStudent(studentEntity, email.toLowerCase())) {
            return new ResponseEntity("Student is updated successfully", HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
    }

}
