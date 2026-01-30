package com.example.aopassignment.service;

import com.example.aopassignment.exception.ResourceNotFoundException;
import com.example.aopassignment.model.Student;
import com.example.aopassignment.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        logger.info("Fetching all students");
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Integer id) {
        logger.info("Fetching student by id: {}", id);
        return studentRepository.findById(id);
    }

    public Student addStudent(Student student) {
        logger.info("Adding new student: {}", student);
        return studentRepository.save(student);
    }

    public Student updateStudent(Integer id, Student studentDetails) {
        logger.info("Updating student with id: {}", id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found for this id :: " + id));
        student.setName(studentDetails.getName());
        student.setEmail(studentDetails.getEmail());
        student.setDepartment(studentDetails.getDepartment());
        Student updatedStudent = studentRepository.save(student);
        logger.info("Updated student: {}", updatedStudent);
        return updatedStudent;
    }

    public void deleteStudent(Integer id) {
        logger.info("Deleting student with id: {}", id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found for this id :: " + id));
        studentRepository.delete(student);
        logger.info("Deleted student with id: {}", id);
    }
}
