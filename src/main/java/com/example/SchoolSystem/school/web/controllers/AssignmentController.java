package com.example.SchoolSystem.school.web.controllers;

import com.example.SchoolSystem.school.entities.assignments.IAssignmentService;
import com.example.SchoolSystem.school.entities.assignments.TeachersToSubjectsAssignmentStrategyImpl;
import com.example.SchoolSystem.school.entities.schoolSubject.service.ISchoolSubjectAssignmentService;
import com.example.SchoolSystem.school.exceptions.NotEnoughTeachersException;
import com.example.SchoolSystem.school.timetable.assigner.IAutomaticTeacherToClassAssigner;
import com.example.SchoolSystem.school.web.assignments.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/assign/studentsToClasses")
    public ResponseEntity<Object> assignStudentsToSchoolClasses(@RequestBody List<StudentToSchoolClassAssignmentRequest> requests) {
        return assign(requests);
    }

    @PostMapping("/assign/subjectsToGrades")
    public ResponseEntity<Object> assignSubjectsToGrades(@RequestBody List<SubjectsToGradesAssignmentRequest> requests) {
        return assign(requests);
    }

    @PostMapping("/assign/teachersToClasses")
    public ResponseEntity<Object> assignTeachersToSchoolClasses(@RequestBody List<TeacherToSchoolClassAssignmentRequest> requests) {
        return assign(requests);
    }

    @PostMapping("/assign/teachersToSubjects")
    public ResponseEntity<Object> assignTeachersToSubjects(@RequestBody List<TeachersToSubjectsAssignmentRequest> requests) {
        return assign(requests);
    }

    @PostMapping("/assign/teachersToClasses/auto")
    public ResponseEntity<Object> assignTeachersAutomatically(){
        try {
            automaticTeacherToClassAssigner.assign();
            return  new ResponseEntity<>("ok", HttpStatus.OK);
        }catch (NotEnoughTeachersException e){
            return new ResponseEntity<>(e.getHireRecommendation(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    private ResponseEntity<Object> assign(List<? extends IAssignment> requests) {
        try {
            Map<String, List<String>> assigned = assignmentService.assign(requests);
            return new ResponseEntity<>(assigned, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(String.format("Unsuccesfull assigning. %s", e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(String.format("Unsuccesfull assigning. %s", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(String.format("Unsuccesfull assigning. %s", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
