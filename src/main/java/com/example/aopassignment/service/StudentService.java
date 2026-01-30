package com.example.aopassignment.service;

import com.example.aopassignment.model.Student;

import java.util.List;

public interface StudentService {
    List<Student> getAllStudents();
    Student getStudentById(Integer id);
    Student addStudent(Student student);
    Student updateStudent(Integer id, Student studentDetails);
    void deleteStudent(Integer id);
}