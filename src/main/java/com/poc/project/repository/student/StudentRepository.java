package com.poc.project.repository.student;

import com.poc.project.entity.employee.EmployeeEntity;
import com.poc.project.entity.student.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity,String> {
    boolean existsByStudentEmail(String email);
    Optional<StudentEntity> findByStudentEmail(String email);
}
