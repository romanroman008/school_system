package com.example.SchoolSystem.school.entities.assignments;

import com.example.SchoolSystem.school.entities.person.student.Student;
import com.example.SchoolSystem.school.entities.person.student.service.IStudentService;
import com.example.SchoolSystem.school.entities.schoolClass.SchoolClass;
import com.example.SchoolSystem.school.entities.schoolClass.service.ISchoolClassService;
import com.example.SchoolSystem.school.web.assignments.IAssignment;
import com.example.SchoolSystem.school.web.assignments.StudentToSchoolClassAssignmentRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.*;


public class StudentsToSchoolClassesAssignmentStrategyImpl implements IAssignmentStrategy {

    private final ISchoolClassService schoolClassService;
    private final IStudentService studentService;


    private List<SchoolClass> classes;
    private List<Student> students;


    private Map<Long, String> invalidData;
    private Map<String, List<String>> successfulAssignments;

    public StudentsToSchoolClassesAssignmentStrategyImpl(
            ISchoolClassService schoolClassService,
            IStudentService studentService) {

        this.schoolClassService = schoolClassService;
        this.studentService = studentService;
    }

    public Map<String, List<String>> assign(List<? extends IAssignment> assignments) {
        students = studentService.getStudentsUnassignedToClasses();
        classes = schoolClassService.findWithAvailableCapacity();
        successfulAssignments = new HashMap<>();
        invalidData = new HashMap<>();


        assignments.stream().map(assignment -> (StudentToSchoolClassAssignmentRequest) assignment).forEach(assignment -> {

            Optional<Student> student = findStudent(assignment.studentId());
            Optional<SchoolClass> schoolClass = findSchoolClass(assignment.schoolClassId());


            if (student.isPresent() && schoolClass.isPresent()) {
                schoolClass.get().addStudent(student.get());
                addAssignmentAsSuccessful(schoolClass.get(), student.get());
            }
        });

        if (invalidData.isEmpty()){
            schoolClassService.flush();
            return successfulAssignments;
        }

        throw new EntityNotFoundException(String.format("Could not assign students. Invalid elements list: %s", invalidData));




    }

    private void addAssignmentAsSuccessful(SchoolClass schoolClass, Student student) {

        successfulAssignments.computeIfPresent(schoolClass.getTotalGrade(),
                (key, values) -> {
                    values.add(student.getFullName());
                    return values;
                });
        successfulAssignments.putIfAbsent(
                student.getFullName(),
                new ArrayList<>(List.of(schoolClass.getGrade().toString()))
        );
    }


    private Optional<SchoolClass> findSchoolClass(Long schoolClassId) {
        Optional<SchoolClass> schoolClass = classes
                .stream()
                .filter(s -> s.getId().equals(schoolClassId))
                .findFirst();

        if (schoolClass.isEmpty()) {
            invalidData.put(schoolClassId, "School class");
        }
        return schoolClass;
    }

    private Optional<Student> findStudent(Long studentId) {
        Optional<Student> student = students
                .stream()
                .filter(s -> s.getId().equals(studentId))
                .findFirst();
        if (student.isEmpty())
            invalidData.put(studentId, "student");
        return student;
    }


}
