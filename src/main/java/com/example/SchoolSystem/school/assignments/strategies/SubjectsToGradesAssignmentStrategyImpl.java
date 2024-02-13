package com.example.SchoolSystem.school.assignments.strategies;

import com.example.SchoolSystem.school.assignments.IAssignment;
import com.example.SchoolSystem.school.assignments.requests.SubjectsToGradesAssignmentRequest;
import com.example.SchoolSystem.school.schoolClass.Grade;
import com.example.SchoolSystem.school.schoolClass.SchoolClass;
import com.example.SchoolSystem.school.schoolClass.service.ISchoolClassService;
import com.example.SchoolSystem.school.schoolSubject.SchoolSubject;
import com.example.SchoolSystem.school.schoolSubject.service.ISchoolSubjectService;
import jakarta.persistence.EntityNotFoundException;

import java.security.InvalidParameterException;
import java.util.*;

public class SubjectsToGradesAssignmentStrategyImpl implements IAssignmentStrategy {


    private final ISchoolClassService schoolClassService;
    private final ISchoolSubjectService schoolSubjectService;

    private List<SchoolClass> classes;
    Map<Grade, List<SchoolSubject>> gradesWithSubjects;



    private final Map<String, List<String>> successfulAssignments = new HashMap<>();


    public SubjectsToGradesAssignmentStrategyImpl(ISchoolClassService schoolClassService,
                                                  ISchoolSubjectService schoolSubjectService) {
        this.schoolClassService = schoolClassService;
        this.schoolSubjectService = schoolSubjectService;
    }

    public Map<String, List<String>> assign(List<? extends IAssignment> assignments) {
        Map<String, List<String>> successfulAssignments = new HashMap<>();
        classes = schoolClassService.findAll();


        if (classes.isEmpty())
            throw new EntityNotFoundException("There are not any school classes in database");


        gradesWithSubjects = convert(assignments);

        assignSubjectsToClasses();


        schoolClassService.flush();
        return successfulAssignments;

    }

    private void assignSubjectsToClasses() {
        gradesWithSubjects.forEach((grade, subjects) -> {
            classes
                    .stream()
                    .filter(schoolClass -> schoolClass.getGrade().equals(grade))
                    .forEach(schoolClass -> {
                        schoolClass.addSubjects(subjects);
                        addAssignmentAsSuccessful(schoolClass,subjects);
                    });

        });
    }


    private void addAssignmentAsSuccessful(SchoolClass schoolClass, List<SchoolSubject> subjects){

        List<String> subjectNames = subjects.stream().map(SchoolSubject::getName).toList();
        successfulAssignments.put(schoolClass.getTotalGrade(),subjectNames);
    }

    private Map<Grade, List<SchoolSubject>> convert(List<? extends IAssignment> assignments) {
        Map<Grade, List<SchoolSubject>> gradesWithSubjects = new HashMap<>();
        List<SchoolSubject> subjects = schoolSubjectService.findAll();

        assignments
                .stream()
                .map(assignment -> (SubjectsToGradesAssignmentRequest) assignment)
                .forEach(assignment -> {
            Grade grade = getGradeFromInteger(assignment.grade());
            List<SchoolSubject> convertedSubjects = getSubjectsFromNames(subjects, assignment.subjects());
            gradesWithSubjects.put(grade, convertedSubjects);
        });
        return gradesWithSubjects;
    }

    private List<SchoolSubject> getSubjectsFromNames(List<SchoolSubject> subjects, List<String> names) {
        List<SchoolSubject> convertedSubjects = new ArrayList<>();
        List<String> invalidNames = new ArrayList<>();
        names.forEach(name -> {
            Optional<SchoolSubject> subject = subjects
                    .stream()
                    .filter(s -> s.getName().equals(name))
                    .findFirst();
            subject.ifPresentOrElse(convertedSubjects::add, () -> invalidNames.add(name));
        });
        if (!invalidNames.isEmpty())
            throw new EntityNotFoundException(String.format("Subjects not found: %s", invalidNames));
        return convertedSubjects;
    }

    private Grade getGradeFromInteger(int gradeNumber) {
        Optional<Grade> grade = Grade.valueOf(gradeNumber);
        if (grade.isEmpty())
            throw new InvalidParameterException("Grade beyond the range");
        return grade.get();
    }

}
