package com.example.SchoolSystem.school.database.timetable;


import com.example.SchoolSystem.school.entities.lesson.Lesson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TimetableRepositoryImpl{

    List<Lesson> lessons = new ArrayList<>();



    public void add(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public List<Lesson> getAllLessons(){
        return Collections.unmodifiableList(lessons);
    }

    public List<Lesson> getLessonsByClass(String schoolClass){
        return lessons
                .stream()
                .filter(lesson -> lesson.getSchoolClass().equals(schoolClass))
                .toList();
    }

    public List<Lesson> getLessonsByTeacher(String teacher){
        return lessons
                .stream()
                .filter(lesson -> lesson.getTeacher().equals(teacher))
                .toList();
    }
}
