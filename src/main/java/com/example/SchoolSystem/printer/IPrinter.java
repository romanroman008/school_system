package com.example.SchoolSystem.printer;

import com.example.SchoolSystem.printer.timetable.TimetableExcel;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.Timetable;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IPrinter {

    void print(TimetableExcel timetable) throws IOException;


}
