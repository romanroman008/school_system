package com.example.SchoolSystem.school.schoolClass.factory;


import com.example.SchoolSystem.school.student.Student;
import com.example.SchoolSystem.school.schoolClass.SchoolClass;
import com.example.SchoolSystem.school.schoolClass.factory.strategy.SchoolClassCreatingStrategy;

import java.util.List;

public interface ISchoolClassFactory {
   List<SchoolClass> create(List<Student> students, List<SchoolClass> classes, SchoolClassCreatingStrategy strategy);
}
