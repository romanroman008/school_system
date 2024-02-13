package com.example.SchoolSystem.school.schoolClass.converters;

import com.example.SchoolSystem.school.student.Student;
import com.example.SchoolSystem.school.schoolClass.SchoolClass;
import com.example.SchoolSystem.school.schoolClass.SubjectToTeacherAssignment;
import com.example.SchoolSystem.school.schoolClass.SchoolClassDto;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SchoolClassConverter {

    public SchoolClassDto toDto(SchoolClass schoolClass){
         return new SchoolClassDto(
                 schoolClass.getId(),
                 schoolClass.getTotalGrade(),
                 schoolClass.getStudents().stream().map(Student::getFullName).toList(),
                 fromObjectsToString(schoolClass.getSubjectsWithAssignedTeachers())
         );
    }

    public List<SchoolClassDto> toDto(List<SchoolClass> classes){
        return classes.stream().map(this::toDto).toList();
    }

    private Map<String,String> fromObjectsToString(List<SubjectToTeacherAssignment> assignments){
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
