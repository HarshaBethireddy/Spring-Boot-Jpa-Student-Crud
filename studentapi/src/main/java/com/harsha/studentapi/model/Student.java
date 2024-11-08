package com.harsha.studentapi.model;

import com.harsha.studentapi.dto.request.StudentRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "students")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Name is required")
    //@Max(value = 20, message = "Name should be only 20 characters long")
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "Email is required")
    @Email
    private String email;

    @Column(name = "phone_no", nullable = false)
    @NotBlank(message = "Phone no. is required")
    private String phoneNo;

    @Column(name = "joining_date", nullable = false)
    private LocalDate joiningDate;

    @Column(name = "relieving_date")
    private LocalDate relievingDate;

    public static Student fromRequest(StudentRequest request) {
        Student student = new Student();
        student.setName(request.name());
        student.setEmail(request.email());
        student.setPhoneNo(request.phoneNo());
        student.setJoiningDate(request.joiningDate());
        student.setRelievingDate(request.relievingDate());
        return student;
    }
}
