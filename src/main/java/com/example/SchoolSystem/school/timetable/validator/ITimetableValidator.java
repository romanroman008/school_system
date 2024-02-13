package com.example.SchoolSystem.school.timetable.validator;

import com.example.SchoolSystem.school.teacher.Teacher;
import com.example.SchoolSystem.school.schoolClass.SchoolClass;
import com.example.SchoolSystem.school.exceptions.NotEnoughTeachersException;

import java.util.List;

public interface ITimetableValidator {

    void validate(List<Teacher> teachers, List<SchoolClass> classes) throws NotEnoughTeachersException;
}
