package com.example.SchoolSystem.school.timetable.timetablePlainObjects.schoolClass;


import com.example.SchoolSystem.school.schoolClass.Grade;
import com.example.SchoolSystem.school.schoolClass.SchoolClass;
import com.example.SchoolSystem.school.schoolClass.SubjectToTeacherAssignment;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.subject.SubjectConverter;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.subject.SubjectTimetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.teacher.TeacherConverter;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.teacher.TeacherTimetable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchoolClassConverter {

    public static ClassTimetable toTimetable(SchoolClass schoolClass){

        return new ClassTimetable(
                schoolClass.getId(),
                schoolClass.getTotalGrade(),
                schoolClass.getGrade(),
                schoolClass.getAlphabeticalGrade(),
                convertSubjectWithAssignedTeachers(schoolClass.getSubjectsWithTeachers(),schoolClass.getGrade()));
    }

    public static List<ClassTimetable> toTimetable(List<SchoolClass> classes){
        return classes.stream().map(SchoolClassConverter::toTimetable).toList();
    }

    private static Map<SubjectTimetable,TeacherTimetable> convertSubjectWithAssignedTeachers(List<SubjectToTeacherAssignment> subjectsWithAssignedTeachers, Grade grade)
    {

        Map<SubjectTimetable, TeacherTimetable> converted = new HashMap<>();

        subjectsWithAssignedTeachers.forEach(assignment -> {
            if(assignment.isAssigned()){
                converted.put(SubjectConverter.toTimetableObject(assignment.getSchoolSubject(),grade),TeacherConverter.toTimetable(assignment.getTeacher()));
            }
            else{
                converted.put(SubjectConverter.toTimetableObject(assignment.getSchoolSubject(),grade),null);
            }
        });



        return converted;
    }



}
