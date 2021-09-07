package com.poc.project.entity.employee;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.naming.Name;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Builder
@Table(name = "Employee_POC")
public class EmployeeEntity {

    @Id
    @NotNull
    @NotEmpty
    @Email
    private String employeeEmail;
    @NotEmpty
    @NotNull
    @Size(min = 3, max = 20)
    private String employeeName;
    @NotNull
    @Min(value = 18)
    @Max(value = 80)
    private int employeeAge;
    @NotNull
    @NotEmpty
    @Size(min = 1,max = 15)
    private String employeeDesignation;
    @NotNull
    @NotEmpty
    @Size(min = 3)
    private String employeeAddress;
    @NotNull
    private Date employeeJoinedAt;
    @NotNull
    @NotEmpty
    @Size(min = 1,max = 15)
    private String employeeBankAccName;
    @NotNull
    @NotEmpty
    @Size(max = 15)
    private String employeeBankAccType;
    @NotNull
    @NotEmpty
    @Size(max = 20)
    private String employeeBankAccNumber;
}
