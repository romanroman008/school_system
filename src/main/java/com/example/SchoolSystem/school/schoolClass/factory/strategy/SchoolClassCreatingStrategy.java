package com.example.SchoolSystem.school.schoolClass.factory.strategy;


import com.example.SchoolSystem.school.student.Student;
import com.example.SchoolSystem.school.schoolClass.SchoolClass;

import java.util.List;

public interface SchoolClassCreatingStrategy {
    List<SchoolClass> create(List<Student> students);
}
