package com.example.SchoolSystem.school.entities.schoolClass.factory;


import com.example.SchoolSystem.school.entities.person.student.Student;
import com.example.SchoolSystem.school.entities.schoolClass.SchoolClass;
import com.example.SchoolSystem.school.entities.schoolClass.strategy.SchoolClassCreatingStrategy;

import java.util.List;

public interface ISchoolClassFactory {
   List<SchoolClass> create(List<Student> students, List<SchoolClass> classes, SchoolClassCreatingStrategy strategy);
}
