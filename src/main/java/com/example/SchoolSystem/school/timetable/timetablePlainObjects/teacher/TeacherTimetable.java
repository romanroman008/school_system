package com.example.SchoolSystem.school.timetable.timetablePlainObjects.teacher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TeacherTimetable {
    private Long id;
    private String name;
    private final int MAX_HOURS_PER_WEEK;
    private int hoursAvailable;
    private List<String> teachingSubjects =  new ArrayList<>();

    public TeacherTimetable(long id, String name, int MAX_HOURS_PER_WEEK) {
        this.id = id;
        this.name = name;
        this.MAX_HOURS_PER_WEEK = MAX_HOURS_PER_WEEK;
        this.hoursAvailable = MAX_HOURS_PER_WEEK;
    }

    public void resetHours(){
        hoursAvailable = MAX_HOURS_PER_WEEK;
    }

    public String getName() {
        return name;
    }

    public int getMAX_HOURS_PER_WEEK() {
        return MAX_HOURS_PER_WEEK;
    }

    public TeacherTimetable assignHours(int amount){
        if(hoursAvailable - amount >= 0){
            hoursAvailable -= amount;
        }
        return this;
    }

    public boolean checkIfHasSufficientAmountOfHours(int amount){
        if(hoursAvailable - amount >= 0){
            return true;
        }
        return false;
    }

    public int getHoursAvailable() {
        return hoursAvailable;
    }

    public Long getId() {
        return id;
    }

    public void addTeachingSubject(String subject){
        teachingSubjects.add(subject);
    }
    public void addTeachingSubjects(List<String> subjects){
        teachingSubjects.addAll(subjects);
    }

    public List<String> getTeachingSubjects() {
        return Collections.unmodifiableList(teachingSubjects);
    }

    public boolean containsCertainSubject(String subject){
        return teachingSubjects.contains(subject);
    }

    public TeacherTimetable copy(){
        TeacherTimetable teacher = new TeacherTimetable(this.id, this.name, this.MAX_HOURS_PER_WEEK);
        teacher.addTeachingSubjects(this.teachingSubjects);
        return teacher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeacherTimetable that = (TeacherTimetable) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return name;
    }



}
