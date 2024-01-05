package com.example.SchoolSystem.school.entities.schoolClass.strategy;


import com.example.SchoolSystem.school.entities.person.student.Student;
import com.example.SchoolSystem.school.entities.schoolClass.AlphabeticalGrade;
import com.example.SchoolSystem.school.entities.schoolClass.Grade;
import com.example.SchoolSystem.school.entities.schoolClass.SchoolClass;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class BirthdayStrategy implements SchoolClassCreatingStrategy{
    @Override
    public List<SchoolClass> create(List<Student> students) {
        List<SchoolClass> classes = new ArrayList<>();
        List<Student> sorted = students
                .stream()
                .sorted(Comparator.comparing(student -> student.getPersonInformation().getBirthday()))
                .toList();
        int classAmount = AlphabeticalGrade.values().length;
        int averageStudentsPerClass = students.size() / classAmount;
        int rest = students.size() % classAmount;


        List<Student> classStudents;
        int firstStudentIndex = 0;
        int lastStudentIndex;

        for (int i = 0; i < classAmount; i++) {
            AlphabeticalGrade alphGrade = AlphabeticalGrade.valueOf(i).get();
            lastStudentIndex = averageStudentsPerClass;

            if (rest > 0) {
                lastStudentIndex++;
                rest--;
            }
            classStudents = sorted.subList(firstStudentIndex, lastStudentIndex);
            firstStudentIndex = lastStudentIndex;

            SchoolClass schoolClass = new SchoolClass(Grade.I,alphGrade,  classStudents);
            classes.add(schoolClass);
        }

        return classes;
    }
}
