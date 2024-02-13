package com.example.SchoolSystem.school.timetable.converter;

import com.example.SchoolSystem.school.printer.timetable.TimetableExcel;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.Timetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.lesson.LessonConverter;
import com.example.SchoolSystem.school.freePeriod.FreePeriodConverter;
import com.example.SchoolSystem.school.timetable.TimetableDto;

public class TimetableConverter {

    public static TimetableDto toDto(Timetable timetable){
        return new TimetableDto(
                timetable.getId(),
                LessonConverter.toDtoFromEntity(timetable.getLessons()),
                FreePeriodConverter.toDto(timetable.getFreePeriods())
        );
    }

    public static TimetableExcel toExcel(Timetable timetable){
        return new TimetableExcel(timetable.getId(),LessonConverter.toDtoFromEntity(timetable.getLessons()));
    }
}
