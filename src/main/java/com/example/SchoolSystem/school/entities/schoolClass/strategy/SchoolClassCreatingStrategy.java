package com.example.SchoolSystem.school.entities.schoolClass.strategy;


import com.example.SchoolSystem.school.entities.person.student.Student;
import com.example.SchoolSystem.school.entities.schoolClass.SchoolClass;

import java.util.List;

public interface SchoolClassCreatingStrategy {
    List<SchoolClass> create(List<Student> students);
}
