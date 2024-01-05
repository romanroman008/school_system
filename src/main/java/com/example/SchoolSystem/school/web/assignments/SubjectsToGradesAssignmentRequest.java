package com.example.SchoolSystem.school.web.assignments;


import java.util.List;


public record SubjectsToGradesAssignmentRequest(int grade, List<String> subjects) implements IAssignment{
}
