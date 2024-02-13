package com.example.SchoolSystem.school.schoolClass.factory;


import com.example.SchoolSystem.school.AppConfig;
import com.example.SchoolSystem.school.student.Student;
import com.example.SchoolSystem.school.schoolClass.Grade;
import com.example.SchoolSystem.school.schoolClass.SchoolClass;
import com.example.SchoolSystem.school.schoolClass.factory.strategy.SchoolClassCreatingStrategy;
import com.example.SchoolSystem.school.exceptions.NotEnoughSchoolClassPlaces;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class SchoolClassFactorImpl implements ISchoolClassFactory {

    private List<SchoolClass> classes;
    private List<Student> unassignedStudents;
    private SchoolClassCreatingStrategy strategy;

    private Map<Grade, List<SchoolClass>> classesSortedByGrades;

    private Map<Grade, List<Student>> unassignedStudentsSortedByGrades;



    private final AppConfig appConfig;


    @Override
    public List<SchoolClass> create(List<Student> students, List<SchoolClass> existingClasses,@Autowired SchoolClassCreatingStrategy strategy) {
        this.unassignedStudents = students;
        this.classes = new ArrayList<>(existingClasses);
        this.strategy = strategy;
        initialize();
        createNewClassesOrAddStudentsToExistingClasses();
        return classes;
    }

    private void createNewClassesOrAddStudentsToExistingClasses(){
        unassignedStudentsSortedByGrades.forEach((grade, studentList) -> {
            if(!studentList.isEmpty()) {
                if (checkIfYearbookClassesAreAlreadyCreated(grade))
                    addStudentsToExistingClasses(studentList);
                else
                    classes.addAll(strategy.create(studentList));
            }
        });

    }



    private boolean checkIfYearbookClassesAreAlreadyCreated(Grade grade) {
        return !classesSortedByGrades.get(grade).isEmpty();
    }

    private void initialize() {
        classesSortedByGrades = new HashMap<Grade, List<SchoolClass>>();
        unassignedStudentsSortedByGrades = new HashMap<Grade, List<Student>>();

        Arrays.stream(Grade.values()).forEach(grade -> {
            classesSortedByGrades.put(grade, new ArrayList<>(findClassesWithGivenGrade(grade)));
            unassignedStudentsSortedByGrades.put(grade, new ArrayList<>(findStudentsForGivenGrade(grade)));
        });

    }

    private List<SchoolClass> findClassesWithGivenGrade(Grade grade) {
        return classes.stream().filter(schoolClass -> schoolClass.getGrade().equals(grade)).toList();
    }

    private List<Student> findStudentsForGivenGrade(Grade grade) {
        return unassignedStudents.stream().filter(student -> student.getGrade().equals(grade)).toList();
    }

    private void addStudentsToExistingClasses(List<Student> students) {
        Grade grade = students.stream().findAny().get().getGrade();
        List<SchoolClass> classes = classesSortedByGrades.get(grade);
        checkIfClassesHasEnoughPlaces(students.size(), classes);

        students.forEach(student -> getSchoolClassWithLeastNumberOfStudents(classes).addStudent(student));
    }

    private SchoolClass getSchoolClassWithLeastNumberOfStudents(List<SchoolClass> classes) {
        return classes.stream().min(Comparator.comparing(schoolClass -> schoolClass.getStudents().size())).get();
    }

    private void checkIfClassesHasEnoughPlaces(int studentsAmount, List<SchoolClass> classes) {
        int totalStudents = (int) classes
                .stream()
                .mapToLong(schoolClass -> schoolClass.getStudents().size())
                .sum();
        int maxCapacity = classes.size() * appConfig.getMaxStudentsPerClass();
        if(maxCapacity - totalStudents <= studentsAmount)
            throw new NotEnoughSchoolClassPlaces();

    }


}
