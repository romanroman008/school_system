package com.example.SchoolSystem.school.web.dto.teacher.converters;

import com.example.SchoolSystem.school.entities.person.teacher.Teacher;
import com.example.SchoolSystem.school.entities.schoolClass.SchoolClass;
import com.example.SchoolSystem.school.entities.schoolSubject.SchoolSubject;
import com.example.SchoolSystem.school.web.dto.teacher.TeacherDto;

import java.util.List;
import java.util.Set;

public class ToDtoTeacherConverter {

    public static List<TeacherDto> convert(List<Teacher> teachers){
        return teachers.stream().map(ToDtoTeacherConverter::convert).toList();
    }
    public static TeacherDto convert(Teacher teacher){
        return new TeacherDto(
                teacher.getId(),
                teacher.getPersonInformation().getFirstName(),
                teacher.getPersonInformation().getLastName(),
                getSchoolSubjectsNames(teacher.getTeachingSubjects()),
                getSchoolClassesNames(teacher.getTeachingClasses())
        );
    }

    private static List<String> getSchoolSubjectsNames(Set<SchoolSubject> subjects){
        return subjects.stream().map(SchoolSubject::getName).toList();
    }

    private static List<String> getSchoolClassesNames(Set<SchoolClass> classes){
        return classes.stream().map(SchoolClass::getTotalGrade).toList();
    }
}
