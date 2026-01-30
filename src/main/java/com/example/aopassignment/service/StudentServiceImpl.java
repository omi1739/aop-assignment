package com.example.aopassignment.service;

import com.example.aopassignment.model.Student;
import com.example.aopassignment.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Integer id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public Student addStudent(Student student) { 
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Integer id, Student studentDetails) {
        Student student = studentRepository.findById(id).get(); 
        student.setName(studentDetails.getName());
        student.setEmail(studentDetails.getEmail());
        student.setDepartment(studentDetails.getDepartment());
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Integer id) {
        Student student = studentRepository.findById(id).get(); 
        studentRepository.delete(student);
    }
}
