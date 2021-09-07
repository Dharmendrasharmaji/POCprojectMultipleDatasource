package com.poc.project.repository.employee;

import com.poc.project.entity.employee.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity,String> {

    Optional<EmployeeEntity> findByEmployeeEmail(String email);
    void deleteByEmployeeEmail(String email);
}
