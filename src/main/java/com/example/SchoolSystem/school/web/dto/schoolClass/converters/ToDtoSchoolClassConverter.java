package com.example.SchoolSystem.school.web.dto.schoolClass.converters;

import com.example.SchoolSystem.school.entities.person.student.Student;
import com.example.SchoolSystem.school.entities.schoolClass.SchoolClass;
import com.example.SchoolSystem.school.entities.schoolClass.SubjectToTeacherAssignment;
import com.example.SchoolSystem.school.web.dto.schoolClass.SchoolClassDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToDtoSchoolClassConverter {

    public static SchoolClassDto convert(SchoolClass schoolClass){
         return new SchoolClassDto(
                 schoolClass.getId(),
                 schoolClass.getTotalGrade(),
                 schoolClass.getStudents().stream().map(Student::getFullName).toList(),
                 fromObjectsToString(schoolClass.getSubjectsWithAssignedTeachers())
         );
    }

    public static List<SchoolClassDto> convert(List<SchoolClass> classes){
        return classes.stream().map(ToDtoSchoolClassConverter::convert).toList();
    }

    private static Map<String,String> fromObjectsToString(List<SubjectToTeacherAssignment> assignments){
        Map<String, String> converted = new HashMap<>();
        assignments.forEach((assignment) -> {
            if(assignment.isAssigned()){
                converted.put(assignment.getSchoolSubject().getName(), assignment.getTeacher().getFullName());
            }
            else{
                converted.put(assignment.getSchoolSubject().getName(), "Not assigned");
            }
        });
        return converted;
    }
}
