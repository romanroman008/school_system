package com.example.SchoolSystem.school.web.assignments;



public record TeachersToSubjectsAssignmentRequest (Long teacherId ,Long subjectId) implements IAssignment{

}
