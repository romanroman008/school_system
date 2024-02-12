package com.example.SchoolSystem.school.web.controllers;

import com.example.SchoolSystem.school.entities.assignments.IAssignmentService;
import com.example.SchoolSystem.school.entities.assignments.TeachersToSubjectsAssignmentStrategyImpl;
import com.example.SchoolSystem.school.entities.schoolSubject.service.ISchoolSubjectAssignmentService;
import com.example.SchoolSystem.school.exceptions.NotEnoughTeachersException;
import com.example.SchoolSystem.school.timetable.assigner.IAutomaticTeacherToClassAssigner;
import com.example.SchoolSystem.school.web.assignments.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/assignment")
public class AssignmentController {

    @Autowired
    IAssignmentService assignmentService;

    @Autowired
    IAutomaticTeacherToClassAssigner automaticTeacherToClassAssigner;

    @PostMapping(value = "/students/to/classes", headers = "X-API-VERSION=1")
    public ResponseEntity<Object> assignStudentsToSchoolClasses(@RequestBody List<StudentToSchoolClassAssignmentRequest> requests) {
        return assign(requests);
    }

    @PostMapping(value = "/subjects/to/grades", headers = "X-API-VERSION=1")
    public ResponseEntity<Object> assignSubjectsToGrades(@RequestBody List<SubjectsToGradesAssignmentRequest> requests) {
        return assign(requests);
    }

    @PostMapping(value = "/teachers/to/classes", headers = "X-API-VERSION=1")
    public ResponseEntity<Object> assignTeachersToSchoolClasses(@RequestBody List<TeacherToSchoolClassAssignmentRequest> requests) {
        return assign(requests);
    }

    @PostMapping(value = "/teachers/to/subjects", headers = "X-API-VERSION=1")
    public ResponseEntity<Object> assignTeachersToSubjects(@RequestBody List<TeachersToSubjectsAssignmentRequest> requests) {
        return assign(requests);
    }

    @PostMapping(value = "/teachers/to/classes/auto", headers = "X-API-VERSION=1")
    public ResponseEntity<Object> assignTeachersAutomatically(){
        automaticTeacherToClassAssigner.assign();
        return  new ResponseEntity<>("ok", HttpStatus.OK);

    }

    private ResponseEntity<Object> assign(List<? extends IAssignment> requests) {
        Map<String, List<String>> assigned = assignmentService.assign(requests);
        return new ResponseEntity<>(assigned, HttpStatus.OK);
    }




}
