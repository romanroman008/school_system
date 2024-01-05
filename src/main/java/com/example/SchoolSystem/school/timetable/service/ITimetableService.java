package com.example.SchoolSystem.school.timetable.service;


import com.example.SchoolSystem.school.timetable.timetablePlainObjects.Timetable;

import java.io.IOException;
import java.util.List;

public interface ITimetableService {

    Timetable create();
    Timetable generateToExcel(Long id) throws IOException;

    Timetable findById(Long id);
    List<Timetable> findAll();


    void remove(Long id);






}
