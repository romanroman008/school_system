package com.example.SchoolSystem.school.printer;

import com.example.SchoolSystem.school.printer.timetable.TimetableExcel;

import java.io.IOException;

public interface IPrinter {

    void print(TimetableExcel timetable) throws IOException;


}
