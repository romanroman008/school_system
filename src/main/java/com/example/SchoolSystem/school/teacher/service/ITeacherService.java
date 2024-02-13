package com.example.SchoolSystem.school.teacher.service;

import com.example.SchoolSystem.school.teacher.Teacher;
import com.example.SchoolSystem.school.schoolSubject.SchoolSubject;

import java.util.List;

public interface ITeacherService {
    Teacher add(Teacher teacher);
    List<Teacher> addAll(List<Teacher> teachers);


    Teacher findById(Long id);
    List<Teacher> findAll();
    List<Teacher> findGivenSubjectTeachers(SchoolSubject schoolSubject);



    Teacher update(Long id,Teacher teacher);


    Teacher delete(Long id);
    void flush();

}
