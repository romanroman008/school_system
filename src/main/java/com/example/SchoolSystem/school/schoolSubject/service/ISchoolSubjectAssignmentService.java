package com.example.SchoolSystem.school.schoolSubject.service;

import com.example.SchoolSystem.school.assignments.requests.TeachersToSubjectsAssignmentRequest;
import com.example.SchoolSystem.school.assignments.requests.SubjectsToGradesAssignmentRequest;

import java.util.List;
import java.util.Map;

public interface ISchoolSubjectAssignmentService {

    List<SubjectsToGradesAssignmentRequest> assignToClasses(List<SubjectsToGradesAssignmentRequest> assignments);

    Map<String,List<String>> assignToTeachers(List<TeachersToSubjectsAssignmentRequest> assignments);
}
