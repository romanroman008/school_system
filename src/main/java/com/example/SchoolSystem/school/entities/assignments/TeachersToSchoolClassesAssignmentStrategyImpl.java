package com.example.SchoolSystem.school.entities.assignments;

import com.example.SchoolSystem.school.entities.person.teacher.Teacher;
import com.example.SchoolSystem.school.entities.person.teacher.service.ITeacherService;
import com.example.SchoolSystem.school.entities.schoolClass.SchoolClass;
import com.example.SchoolSystem.school.entities.schoolClass.service.ISchoolClassService;
import com.example.SchoolSystem.school.entities.schoolSubject.SchoolSubject;
import com.example.SchoolSystem.school.entities.schoolSubject.service.ISchoolSubjectService;
import com.example.SchoolSystem.school.web.assignments.IAssignment;
import com.example.SchoolSystem.school.web.assignments.TeacherToSchoolClassAssignmentRequest;
import jakarta.persistence.EntityNotFoundException;

import java.util.*;

public class TeachersToSchoolClassesAssignmentStrategyImpl implements IAssignmentStrategy {

    private final ISchoolClassService schoolClassService;
    private final ISchoolSubjectService schoolSubjectService;
    private final ITeacherService teacherService;


    private List<SchoolClass> classes;
    private List<SchoolSubject> subjects;
    private List<Teacher> teachers;


    private Map<Long, String> invalidData;
    private Map<String, List<String>> successfulAssignments;

    public TeachersToSchoolClassesAssignmentStrategyImpl(
            ISchoolClassService schoolClassService,
            ISchoolSubjectService schoolSubjectService,
            ITeacherService teacherService) {

        this.schoolClassService = schoolClassService;
        this.schoolSubjectService = schoolSubjectService;
        this.teacherService = teacherService;


    }

    @Override
    public Map<String, List<String>> assign(List<? extends IAssignment> assignments) {
        teachers = teacherService.findAll();
        classes = schoolClassService.findAll();
        subjects = schoolSubjectService.findAll();

        invalidData = new HashMap<>();
        successfulAssignments = new HashMap<>();

        assignments.stream().map(assignment -> (TeacherToSchoolClassAssignmentRequest) assignment).forEach(assignment -> {


            Optional<Teacher> teacher = findTeacher(assignment.teacherId());
            Optional<SchoolClass> schoolClass = findSchoolClass(assignment.schoolClassId());
            Optional<SchoolSubject> schoolSubject = findSchoolSubject(assignment.schoolSubjectId());



            if (schoolClass.isPresent() && teacher.isPresent() && schoolSubject.isPresent()) {
                schoolClass.get().assignTeacherToSubject(schoolSubject.get(), teacher.get());
                addAssignmentAsSuccessful(schoolSubject.get(), schoolClass.get(), teacher.get());
            }


        });

        if (!invalidData.isEmpty())
            throw new EntityNotFoundException(String.format("Could not assign teachers. Invalid elements list: %s", invalidData));

        schoolClassService.flush();
        return successfulAssignments;
    }

    private void addAssignmentAsSuccessful(SchoolSubject schoolSubject, SchoolClass schoolClass, Teacher teacher){

        successfulAssignments.computeIfPresent(schoolClass.getTotalGrade(),
                (key, values) -> {
                    values.add(teacher.getFullName());
                    return values;
                });
        successfulAssignments.put(
                schoolClass.getGrade().toString(),
                new ArrayList<>(List.of(teacher.getFullName()))
        );
    }

    private Optional<SchoolSubject> findSchoolSubject(Long subjectId){
        Optional<SchoolSubject> schoolSubject = subjects
                .stream()
                .filter(s -> s.getId().equals(subjectId))
                .findFirst();

        if (schoolSubject.isEmpty()) {
            invalidData.put(subjectId, "School subject");
        }
        return schoolSubject;
    }

    private Optional<SchoolClass> findSchoolClass(Long schoolClassId){
        Optional<SchoolClass> schoolClass = classes
                .stream()
                .filter(s -> s.getId().equals(schoolClassId))
                .findFirst();

        if (schoolClass.isEmpty()) {
            invalidData.put(schoolClassId, "School class");
        }
        return schoolClass;
    }

    private Optional<Teacher> findTeacher(Long teacherId){
        Optional<Teacher> teacher = teachers
                .stream()
                .filter(t -> t.getId().equals(teacherId))
                .findFirst();
        if (teacher.isEmpty()) {
            invalidData.put(teacherId, "Teacher");
        }
        return teacher;
    }

}
