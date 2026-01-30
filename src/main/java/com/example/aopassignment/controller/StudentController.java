package com.example.aopassignment.controller;

import com.example.aopassignment.model.Student;
import com.example.aopassignment.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "*")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents(); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Integer id) {
        logger.info("Received request to get student by id: {}", id);
        Student student = studentService.getStudentById(id); 
        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        logger.info("Received request to add new student: {}", student);
        return studentService.addStudent(student);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Integer id, @RequestBody Student studentDetails) { 
        logger.info("Received request to update student with id: {}", id);
       
        return studentService.updateStudent(id, studentDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Integer id) {
        logger.info("Received request to delete student with id: {}", id);
        
        studentService.deleteStudent(id);
    }
}
