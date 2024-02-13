package com.example.SchoolSystem.school.assignments.requests;


import com.example.SchoolSystem.school.assignments.IAssignment;

import java.util.List;


public record SubjectsToGradesAssignmentRequest(int grade, List<String> subjects) implements IAssignment {
}
