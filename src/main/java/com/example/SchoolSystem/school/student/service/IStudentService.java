package com.example.SchoolSystem.school.student.service;

import com.example.SchoolSystem.school.student.Student;

import java.util.List;

public interface IStudentService {
    Student add(Student student);
    List<Student> addAll(List<Student> student);


    Student findById(Long id);
    List<Student> findAll();
    List<Student> getStudentsUnassignedToClasses();


    Student update(Long id,Student student);


    Student delete(Long id);





}
