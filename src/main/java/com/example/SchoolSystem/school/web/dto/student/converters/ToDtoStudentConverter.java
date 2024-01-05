package com.example.SchoolSystem.school.web.dto.student.converters;

import com.example.SchoolSystem.school.entities.person.student.Student;
import com.example.SchoolSystem.school.entities.schoolClass.SchoolClass;
import com.example.SchoolSystem.school.web.dto.student.StudentDto;

import java.util.List;

public class ToDtoStudentConverter {

    public static StudentDto convert(Student student){
        return new StudentDto(
                student.getId(),
                student.getPersonInformation().getFirstName(),
                student.getPersonInformation().getLastName(),
                student.getSchoolClass()
        );
    }



    public static List<StudentDto> convert(List<Student> students){
        return students
                .stream()
                .map(ToDtoStudentConverter::convert).toList();
    }



}
