package com.example.SchoolSystem.school.schoolClass.factory.strategy;


import com.example.SchoolSystem.school.student.Student;
import com.example.SchoolSystem.school.schoolClass.AlphabeticalGrade;
import com.example.SchoolSystem.school.schoolClass.Grade;
import com.example.SchoolSystem.school.schoolClass.SchoolClass;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Primary
public class AlphabeticalStrategy implements SchoolClassCreatingStrategy {
    @Override
    public List<SchoolClass> create(List<Student> students){
        List<SchoolClass> classes = new ArrayList<>();
        Grade grade = students.get(0).getGrade();
        List<Student> sorted = students
                .stream()
                .sorted(Comparator.comparing(Student::getFullName))
                .toList();
        Map<AlphabeticalGrade, Integer> gradesWitHStudentsAmount = calculateStudentsPerEachClass(students);

        AtomicInteger firstStudentIndex = new AtomicInteger();
        AtomicInteger lastStudentIndex = new AtomicInteger();

        gradesWitHStudentsAmount.forEach((alphGrade,amount) ->{
            lastStudentIndex.addAndGet(amount);
            List<Student> classStudents = new ArrayList<>(sorted.subList(firstStudentIndex.get(), lastStudentIndex.get()));
            SchoolClass schoolClass = new SchoolClass(grade,alphGrade,classStudents);
            classes.add(new SchoolClass(grade,alphGrade,classStudents));
            setStudentsClass(classStudents,schoolClass);
            firstStudentIndex.addAndGet(amount);

        });

        return classes;

    }

    private void setStudentsClass(List<Student> students, SchoolClass schoolClass){
        students.forEach(student -> student.setSchoolClass(schoolClass));
    }



    private Map<AlphabeticalGrade,Integer> calculateStudentsPerEachClass(List<Student> students){
        int classAmount = AlphabeticalGrade.values().length;
        int averageStudentsPerClass = students.size() / classAmount;
        int rest = students.size() % classAmount;

        Map<AlphabeticalGrade,Integer> gradesWitHStudentsAmount = new HashMap<>();
        for (int i = 1; i < AlphabeticalGrade.values().length + 1; i++) {
            gradesWitHStudentsAmount.put(AlphabeticalGrade.valueOf(i).get(),averageStudentsPerClass);
            if(rest > 0){
                gradesWitHStudentsAmount.put(AlphabeticalGrade.valueOf(i).get(),averageStudentsPerClass + 1);
                rest--;
            }
        }

        return gradesWitHStudentsAmount;
    }



}
