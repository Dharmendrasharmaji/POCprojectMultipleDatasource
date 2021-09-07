package com.poc.project.entity.student;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

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
//@Table(name = "Student_POC")
public class StudentEntity {

    @Id
    @NotEmpty
    @NotNull
    @Email
    private String studentEmail;
    @NotEmpty
    @NotNull
    @Size(min = 3,max = 20)
    private String studentName;
    @NotNull
    @Min(value = 18)
    @Max(value = 30)
    private int studentAge;
    @NotEmpty
    @NotNull
    @Size(min = 1,max = 10)
    private String studentBatch;
    @NotNull
    private Date studentAdmissionDate;
    @NotEmpty
    @NotNull
    @Size(min = 10,max = 10,message = "Phone number should be exact 10 characters.")
    private String studentPhone;
    @NotEmpty
    @NotNull
    @Size(min = 3,max = 50)
    private String studentAddress;
}
