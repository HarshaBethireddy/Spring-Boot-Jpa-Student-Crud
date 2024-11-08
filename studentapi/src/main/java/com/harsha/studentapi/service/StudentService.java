package com.harsha.studentapi.service;

import com.harsha.studentapi.dto.enums.DegreeStatus;
import com.harsha.studentapi.dto.request.StudentRequest;
import com.harsha.studentapi.dto.response.StudentResponse;
import com.harsha.studentapi.exception.custom.StudentNotFoundException;
import com.harsha.studentapi.model.Student;
import com.harsha.studentapi.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepo;


    public StudentResponse addStudent(StudentRequest student) {
        Student savedStudent = studentRepo.save(Student.fromRequest(student));
        return convertToStudentResponse(savedStudent);
    }

    public List<StudentResponse> getAllStudents() {
        return studentRepo.findAll().stream().map(this::convertToStudentResponse).collect(Collectors.toList());
    }

    public StudentResponse getStudentById(Long id) {
        return studentRepo.findById(id).map(this::convertToStudentResponse)
                .orElseThrow(() -> new StudentNotFoundException("Student with id: " + id + " not found"));
    }

    public List<StudentResponse> getStudentsByName(String name) {
        return studentRepo.findByName(name).stream().map(this::convertToStudentResponse).collect(Collectors.toList());
    }

    public List<StudentResponse> getStudentsByDegreeStatus(DegreeStatus status) {
        return studentRepo.findAll().stream().map(this::convertToStudentResponse).filter(student -> student.degreeStatus().equals(status)).collect(Collectors.toList());
    }

    public StudentResponse updateStudent(Long id, StudentRequest student) {
        return studentRepo.findById(id).map(existingStudent -> {
            existingStudent.setName(student.name());
            existingStudent.setEmail(student.email());
            existingStudent.setPhoneNo(student.phoneNo());
            existingStudent.setJoiningDate(student.joiningDate());
            existingStudent.setRelievingDate(student.relievingDate());
            return convertToStudentResponse(studentRepo.save(existingStudent));
        }).orElseThrow(() -> new StudentNotFoundException("Student with id: " + id + " not found"));
    }

    public void deleteStudent(Long id) {
        if(studentRepo.existsById(id)) {
            studentRepo.deleteById(id);
        } else {
            throw new StudentNotFoundException("Student with id: " + id + " not found");
        }
    }

    public StudentResponse convertToStudentResponse(Student student) {
        DegreeStatus degreeStatus = determineDegreeStatus(student.getJoiningDate(), student.getRelievingDate());
        return new StudentResponse(student.getId(), student.getName(), student.getEmail(), student.getPhoneNo(), degreeStatus);
    }

    private DegreeStatus determineDegreeStatus(LocalDate joiningDate, LocalDate relievingDate) {
        int yearsDifference = relievingDate != null ? relievingDate.getYear() - joiningDate.getYear() : LocalDate.now().getYear() - joiningDate.getYear();

        if(relievingDate == null) {
            if(yearsDifference > 8){
                return DegreeStatus.FAILED;
            } else {
                return DegreeStatus.IN_PROGRESS;
            }
        } else {
            if(yearsDifference <= 8){
                return DegreeStatus.COMPLETED;
            } else {
                return DegreeStatus.FAILED;
            }
        }
    }
}
