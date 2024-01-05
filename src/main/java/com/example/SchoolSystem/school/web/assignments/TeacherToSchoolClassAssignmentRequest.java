package com.example.SchoolSystem.school.web.assignments;

public record TeacherToSchoolClassAssignmentRequest(Long teacherId, Long schoolClassId, Long schoolSubjectId) implements IAssignment {

}
