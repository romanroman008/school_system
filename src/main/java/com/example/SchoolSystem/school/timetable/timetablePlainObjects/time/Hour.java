package com.example.SchoolSystem.school.timetable.timetablePlainObjects.time;


import com.example.SchoolSystem.school.timetable.timetablePlainObjects.lesson.LessonTimetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.schoolClass.ClassTimetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.teacher.TeacherTimetable;

import java.util.*;

public class Hour{

    DayName day;
    int number;
    Set<TeacherTimetable> availableTeachers = new HashSet<>();
    Set<ClassTimetable> availableClasses = new HashSet<>();
    List<LessonTimetable> lessonTimetables;



    public Hour(int number, DayName day, Set<TeacherTimetable> availableTeachers, Set<ClassTimetable> availableClasses) {
        this.number = number;
        this.day = day;
        this.availableTeachers = availableTeachers;
        this.availableClasses = availableClasses;
        this.lessonTimetables = new ArrayList<>();
    }

    public DayName getDay() {
        return day;
    }

    public int getNumber(){
        return number;
    }
    public Set<TeacherTimetable> getAvailableTeachers(){
        return availableTeachers;
    }

    public Set<ClassTimetable> getAvailableClasses(){
        return availableClasses;
    }

    public void addLesson(LessonTimetable lessonTimetable){
        availableClasses.remove(lessonTimetable.getSchoolClass());
        availableTeachers.remove(lessonTimetable.getTeacher());
        lessonTimetables.add(lessonTimetable);
    }

    public void removeLesson(LessonTimetable lessonTimetable) {
        availableClasses.add(lessonTimetable.getSchoolClass());
        availableTeachers.add(lessonTimetable.getTeacher());
        lessonTimetables.remove(lessonTimetable);
    }

   public boolean checkIfDoesNotContainsConcreteSchoolClass(ClassTimetable schoolClass){
        return lessonTimetables
                .stream()
                .noneMatch(lesson -> lesson.getSchoolClass().equals(schoolClass));
   }

   public boolean checkIfTeacherIsAvailable(TeacherTimetable teacher){
        return availableTeachers.contains(teacher);
   }



    public List<LessonTimetable> getLesson(ClassTimetable schoolClass){
        return lessonTimetables
                .stream()
                .filter(lesson -> lesson.getSchoolClass().equals(schoolClass)).toList();
    }

    public List<LessonTimetable> getLessons() {
        return lessonTimetables;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hour hour = (Hour) o;
        return number == hour.number && Objects.equals(day, hour.day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, number);
    }

    @Override
    public String toString() {
        return "Hour{" +
                "day='" + day + '\'' +
                ", number=" + number +
                ", availableTeachers=" + availableTeachers +
                ", availableClasses=" + availableClasses +
                ", lessons=" + lessonTimetables +
                '}';
    }
}
