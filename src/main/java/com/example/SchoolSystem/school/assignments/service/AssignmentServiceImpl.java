package com.example.SchoolSystem.school.assignments.service;

import com.example.SchoolSystem.school.assignments.*;
import com.example.SchoolSystem.school.assignments.strategies.*;
import com.example.SchoolSystem.school.teacher.service.ITeacherService;
import com.example.SchoolSystem.school.student.service.IStudentService;
import com.example.SchoolSystem.school.schoolClass.service.ISchoolClassService;
import com.example.SchoolSystem.school.schoolSubject.service.ISchoolSubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements IAssignmentService{

    private final ISchoolSubjectService schoolSubjectService;
    private final ISchoolClassService schoolClassService;
    private final IStudentService studentService;
    private final ITeacherService teacherService;

    IAssignmentStrategy strategy;

    @Override
    public Map<String, List<String>> assign(List<? extends IAssignment> assignments) {
        if(assignments.isEmpty())
            throw new IndexOutOfBoundsException("Given list of assignments is empty");
        strategy = chooseProperStrategy(assignments.get(0));
        return strategy.assign(assignments);
    }

    private IAssignmentStrategy chooseProperStrategy(Object object){

        String assignmentClassName = object.getClass().getSimpleName();

        switch (assignmentClassName) {
            case "StudentToSchoolClassAssignmentRequest" ->
            {
                return new StudentsToSchoolClassesAssignmentStrategyImpl(schoolClassService, studentService);
            }
            case "SubjectsToGradesAssignmentRequest" ->
            {
                return new SubjectsToGradesAssignmentStrategyImpl(schoolClassService, schoolSubjectService);
            }
            case "TeachersToSubjectsAssignmentRequest" ->
            {
                return new TeachersToSubjectsAssignmentStrategyImpl(schoolSubjectService, teacherService);
            }
            case "TeacherToSchoolClassAssignmentRequest" ->
            {
                return new TeachersToSchoolClassesAssignmentStrategyImpl(schoolClassService, schoolSubjectService, teacherService);
            }
            default ->
                    throw new IllegalArgumentException(String.format("Assignment %s is unknown (or unimplemented)", assignmentClassName));
        }

    }
}
