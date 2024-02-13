package com.example.SchoolSystem.school.assignments.requests;


import com.example.SchoolSystem.school.assignments.IAssignment;

public record StudentToSchoolClassAssignmentRequest(Long studentId, Long schoolClassId) implements IAssignment {


}
