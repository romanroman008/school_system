package com.example.SchoolSystem.school.assignments.requests;


import com.example.SchoolSystem.school.assignments.IAssignment;

public record TeachersToSubjectsAssignmentRequest (Long teacherId , Long subjectId) implements IAssignment {

}
