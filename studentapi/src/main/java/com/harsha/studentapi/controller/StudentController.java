package com.harsha.studentapi.controller;

import com.harsha.studentapi.dto.enums.DegreeStatus;
import com.harsha.studentapi.dto.request.StudentRequest;
import com.harsha.studentapi.dto.response.StudentResponse;
import com.harsha.studentapi.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentResponse> addStudent(@Valid @RequestBody StudentRequest request) {
        return new ResponseEntity<>(studentService.addStudent(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable Long id) {
        return new ResponseEntity<>(studentService.getStudentById(id), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<StudentResponse>> getStudentsByName(@RequestParam String name) {
        return new ResponseEntity<>(studentService.getStudentsByName(name), HttpStatus.OK);
    }

    @GetMapping("/search/{degreeStatus}")
    public ResponseEntity<List<StudentResponse>> getStudentsByDegreeStatus(@PathVariable String degreeStatus) {
        try {
            DegreeStatus status = DegreeStatus.valueOf(degreeStatus.toUpperCase());
            return new ResponseEntity<>(studentService.getStudentsByDegreeStatus(status), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentRequest request) {
        return new ResponseEntity<>(studentService.updateStudent(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
