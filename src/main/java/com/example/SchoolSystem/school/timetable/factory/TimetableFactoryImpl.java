package com.example.SchoolSystem.school.timetable.factory;


import com.example.SchoolSystem.school.teacher.Teacher;
import com.example.SchoolSystem.school.teacher.service.ITeacherService;
import com.example.SchoolSystem.school.schoolClass.SchoolClass;
import com.example.SchoolSystem.school.schoolClass.service.ISchoolClassService;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.Timetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.schoolClass.SchoolClassConverter;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.teacher.TeacherConverter;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.time.Week;
import com.example.SchoolSystem.school.timetable.validator.ITimetableValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class TimetableFactoryImpl implements ITimetableFactory {


    private final ITeacherService teacherService;
    private final ISchoolClassService schoolClassService;

    private final FreePeriodFiller freePeriodFiller;
    private final WeekCreator weekCreator;


    private final ITimetableValidator timetableValidator;

    private List<Teacher> teachers;
    private List<SchoolClass> classesWithTeachersAssigned;

    private Week week;


    public Timetable create() {

        teachers = teacherService.findAll();
        classesWithTeachersAssigned = schoolClassService.findWithTeachersAssigned();

        timetableValidator.validate(teachers, classesWithTeachersAssigned);

        week = createWeak();

        week = fillFreePeriods();

        return new Timetable(week);
    }

    private Week createWeak(){
        return weekCreator.create(
                TeacherConverter.toTimetable(teachers),
                SchoolClassConverter.toTimetable(classesWithTeachersAssigned)
        );
    }

    private Week fillFreePeriods(){

        return freePeriodFiller.fill(week);
    }




}
