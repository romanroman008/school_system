package com.example.SchoolSystem.school.entities.schoolSubject.service;


import com.example.SchoolSystem.school.entities.schoolSubject.SchoolSubject;

import java.util.List;

public interface ISchoolSubjectService {

    SchoolSubject add(SchoolSubject subject);
    List<SchoolSubject> addAll(List<SchoolSubject> subjects);



    SchoolSubject findById(Long id);
    List<SchoolSubject> findAll();


    SchoolSubject update(Long id,SchoolSubject subject);


    SchoolSubject delete(Long id);




}
