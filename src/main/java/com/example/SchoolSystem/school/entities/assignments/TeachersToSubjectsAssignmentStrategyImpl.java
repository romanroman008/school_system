package com.example.SchoolSystem.school.entities.assignments;


import com.example.SchoolSystem.school.entities.person.teacher.Teacher;
import com.example.SchoolSystem.school.entities.person.teacher.service.ITeacherService;
import com.example.SchoolSystem.school.entities.schoolSubject.SchoolSubject;
import com.example.SchoolSystem.school.entities.schoolSubject.service.ISchoolSubjectService;
import com.example.SchoolSystem.school.web.assignments.IAssignment;
import com.example.SchoolSystem.school.web.assignments.TeachersToSubjectsAssignmentRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;


import java.util.*;


@Service
public class TeachersToSubjectsAssignmentStrategyImpl implements IAssignmentStrategy {


    private final ISchoolSubjectService schoolSubjectService;
    private final ITeacherService teacherService;


    private List<Teacher> teachers;
    private List<SchoolSubject> subjects;


    private Map<Long, String> invalidData;
    private Map<String, List<String>> successfulAssignments;


    public TeachersToSubjectsAssignmentStrategyImpl(
            ISchoolSubjectService schoolSubjectService,
            ITeacherService teacherService) {

        this.schoolSubjectService = schoolSubjectService;
        this.teacherService = teacherService;
    }


    public Map<String, List<String>> assign(List<? extends IAssignment> assignments) {

        invalidData = new HashMap<>();
        successfulAssignments = new HashMap<>();

        teachers = teacherService.findAll();
        subjects = schoolSubjectService.findAll();


        assignments.stream().map(assignment -> (TeachersToSubjectsAssignmentRequest) assignment).forEach(assignment -> {

            Optional<Teacher> teacher = findTeacher(assignment.teacherId());
            Optional<SchoolSubject> schoolSubject = findSchoolSubject(assignment.subjectId());


            if (schoolSubject.isPresent() && teacher.isPresent()) {
                teacher.get().addTeachingSubject(schoolSubject.get());
                addAssignmentAsSuccessful(teacher.get(),schoolSubject.get());
            }
        });

        if (!invalidData.isEmpty())
            throw new EntityNotFoundException(String.format("Could not assign subjects. Invalid elements list: %s", invalidData));

        teacherService.flush();
        return successfulAssignments;
    }

    private void addAssignmentAsSuccessful(Teacher teacher, SchoolSubject schoolSubject){
        successfulAssignments.computeIfPresent(
                teacher.getFullName(),
                (key, value) -> {
                    value.add(schoolSubject.getName());
                    return value;
                });
        successfulAssignments.putIfAbsent(teacher.getFullName(), new ArrayList<>(List.of(schoolSubject.getName())));
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
