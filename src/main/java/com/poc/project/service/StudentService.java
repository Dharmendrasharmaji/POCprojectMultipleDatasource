package com.poc.project.service;

import com.poc.project.entity.student.StudentEntity;

import java.util.List;

public interface StudentService {

    List<StudentEntity> getAllStudents();
    StudentEntity getStudentByEmail(String email);
    boolean addStudent(StudentEntity studentEntity);
    boolean deleteStudent(String email);
    boolean updateStudent(StudentEntity studentEntity,String email);

}
