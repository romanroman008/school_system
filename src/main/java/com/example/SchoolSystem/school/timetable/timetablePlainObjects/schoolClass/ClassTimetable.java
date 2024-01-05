package com.example.SchoolSystem.school.timetable.timetablePlainObjects.schoolClass;


import com.example.SchoolSystem.school.entities.schoolClass.AlphabeticalGrade;
import com.example.SchoolSystem.school.entities.schoolClass.Grade;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.subject.SubjectTimetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.teacher.TeacherTimetable;
import lombok.Getter;

import java.util.*;

@Getter
public class ClassTimetable implements Comparable<ClassTimetable> {


    private Long id;
    private final String totalGrade;
    private final Grade grade;
    private final AlphabeticalGrade alphabeticalGrade;

    private Map<SubjectTimetable, TeacherTimetable> subjectsWithAssignedTeachers = new HashMap<>();


    public ClassTimetable(Long id, String totalGrade, Grade grade, AlphabeticalGrade alphabeticalGrade, Map<SubjectTimetable,TeacherTimetable> subjectsWithAssignedTeachers) {
        this.id = id;
        this.totalGrade = totalGrade;
        this.grade = grade;
        this.alphabeticalGrade = alphabeticalGrade;
        this.subjectsWithAssignedTeachers = subjectsWithAssignedTeachers;
    }


    public SubjectTimetable getTeachingSubjectOfGivenTeacher(TeacherTimetable teacher) {

        return subjectsWithAssignedTeachers
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() != null)
                .filter(entry -> entry.getValue().equals(teacher))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow();

    }


    public TeacherTimetable getTeacher(SubjectTimetable subject) {
        return subjectsWithAssignedTeachers.get(subject); //todo care
    }

    public List<SubjectTimetable> getSubjectsWithoutAssignedTeachers() {
        return subjectsWithAssignedTeachers
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() == null)
                .map(Map.Entry::getKey)
                .toList();
    }


    public List<SubjectTimetable> getAllSubjects() {
        return subjectsWithAssignedTeachers
                .keySet()
                .stream()
                .toList();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassTimetable that = (ClassTimetable) o;
        return Objects.equals(totalGrade, that.totalGrade) && grade == that.grade;
    }


    @Override
    public int hashCode() {
        return Objects.hash(totalGrade, grade);
    }

    @Override
    public String toString() {
        return totalGrade;
    }

    @Override
    public int compareTo(ClassTimetable o) {
        if (this == o) return 0;
        if (this.grade == o.grade && this.totalGrade.equalsIgnoreCase(o.getTotalGrade()))
            return 0;
        int gradeComparison = grade.compareTo(o.grade);

        if (gradeComparison != 0)
            return gradeComparison;

        return totalGrade.compareTo(o.totalGrade);
    }

    public static class ClassTimetableComparator implements Comparator<ClassTimetable> {

        @Override
        public int compare(ClassTimetable o1, ClassTimetable o2) {
            int gradeComparison = o1.getGrade().compareTo(o2.getGrade());

            if (gradeComparison != 0) {
                return gradeComparison;
            }

            return o1.getTotalGrade().compareTo(o2.getTotalGrade());
        }
    }
}
