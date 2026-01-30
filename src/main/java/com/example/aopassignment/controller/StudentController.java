package com.example.aopassignment.controller;

import com.example.aopassignment.exception.ResourceNotFoundException;
import com.example.aopassignment.model.Student;
import com.example.aopassignment.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "*")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Student> getAllStudents() {
        logger.info("Received request to get all students");
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Integer id) {
        logger.info("Received request to get student by id: {}", id);
        Optional<Student> student = studentService.getStudentById(id);
        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        logger.info("Received request to add new student: {}", student);
        return studentService.addStudent(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Integer id, @RequestBody Student studentDetails) {
        logger.info("Received request to update student with id: {}", id);
        try {
            Student updatedStudent = studentService.updateStudent(id, studentDetails);
            return ResponseEntity.ok(updatedStudent);
        } catch (ResourceNotFoundException e) {
            logger.warn("Student not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Integer id) {
        logger.info("Received request to delete student with id: {}", id);
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            logger.warn("Student not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
}
