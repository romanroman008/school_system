package com.example.SchoolSystem.school.schoolClass.service;


import com.example.SchoolSystem.school.schoolClass.SchoolClass;

import java.util.List;

public interface ISchoolClassService {
    List<SchoolClass> create(int strategyNumber);
    SchoolClass add(SchoolClass schoolClass);
    List<SchoolClass> addAll(List<SchoolClass> classes);


    SchoolClass findById(Long id);
    List<SchoolClass> findAll();
    List<SchoolClass> findWithAvailableCapacity();
    List<SchoolClass> findWithoutTeachersAssigned();
    List<SchoolClass> findWithTeachersAssigned();
    List<SchoolClass> findAllNotGraduated();




    void update (SchoolClass schoolClass);
    void updateAll(List<SchoolClass> classes);
    void updateSchoolClasses(List<SchoolClass> classes);
    void flush();
    void increaseAllClassesGrades();



    void remove(Long id);




}
