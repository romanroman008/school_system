package com.example.SchoolSystem.school.entities.schoolSubject.service;

import com.example.SchoolSystem.school.web.assignments.TeachersToSubjectsAssignmentRequest;
import com.example.SchoolSystem.school.web.assignments.SubjectsToGradesAssignmentRequest;

import java.util.List;
import java.util.Map;

public interface ISchoolSubjectAssignmentService {

    List<SubjectsToGradesAssignmentRequest> assignToClasses(List<SubjectsToGradesAssignmentRequest> assignments);

    Map<String,List<String>> assignToTeachers(List<TeachersToSubjectsAssignmentRequest> assignments);
}
