package com.example.SchoolSystem.school.assignments.requests;

import com.example.SchoolSystem.school.assignments.IAssignment;

public record TeacherToSchoolClassAssignmentRequest(Long teacherId, Long schoolClassId, Long schoolSubjectId) implements IAssignment {

}
