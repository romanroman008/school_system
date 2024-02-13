package com.example.SchoolSystem.school.timetable.timetablePlainObjects.subject;



import com.example.SchoolSystem.school.schoolClass.Grade;
import lombok.Getter;

import java.util.Objects;

@Getter
public class SubjectTimetable {
    private final Long id;
    private final String name;
    private final int HOURS_PER_WEEK;
    private final Grade grade;



    public SubjectTimetable(Long id,String name, int hoursPerWeek, Grade grade){
        this.id = id;
        this.name = name;
        this.HOURS_PER_WEEK = hoursPerWeek;
        this.grade = grade;
    }


    public int getHoursPerWeek() {
         return HOURS_PER_WEEK;
    }



    @Override
    public String toString() {
        return name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectTimetable that = (SubjectTimetable) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
