package com.poc.project.service.Impl.studentserviceimpl;

import com.poc.project.entity.student.StudentEntity;
import com.poc.project.exceptions.EmployeeAlreadyExistsException;
import com.poc.project.exceptions.NoStudentDataException;
import com.poc.project.exceptions.StudentNotFoundException;
import com.poc.project.repository.student.StudentRepository;
import com.poc.project.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Override
    public List<StudentEntity> getAllStudents() {
        List<StudentEntity> allStudents = studentRepository.findAll();
        if (allStudents.isEmpty()){
            throw new NoStudentDataException("Students data are not present.");
        }
        return allStudents;
    }

    @Override
    public StudentEntity getStudentByEmail(String email) {
        Optional<StudentEntity> byemailId = studentRepository.findByStudentEmail(email);
        return byemailId.orElseThrow(()->new StudentNotFoundException("Student with given email doesn't exist."));
    }

    @Override
    public boolean addStudent(StudentEntity studentEntity) {
        boolean check = studentRepository.existsByStudentEmail(studentEntity.getStudentEmail());
        if(check){
            throw new EmployeeAlreadyExistsException("Student with given email is already exist.");
        }
        studentRepository.save(studentEntity);
        return true;
    }

    @Override
    public boolean deleteStudent(String email) {
        boolean exist = studentRepository.existsByStudentEmail(email);
        if(!exist){
            throw new StudentNotFoundException("Student with given email doesn't exist.");
        }
        studentRepository.delete(studentRepository.findByStudentEmail(email).get());
        return true;
    }

    @Override
    public boolean updateStudent(StudentEntity studentEntity, String email) {
        Optional<StudentEntity> byStudentEmail = studentRepository.findByStudentEmail(email);
        if (byStudentEmail.isEmpty()){
            throw new StudentNotFoundException("Student with given email doesn't exist.");
        }
        studentRepository.save(studentEntity);
        return true;
    }
}
