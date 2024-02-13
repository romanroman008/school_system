package com.example.SchoolSystem.school.printer.timetable;

import com.example.SchoolSystem.school.lesson.LessonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class TimetableExcel {
    Long id;
    List<LessonDto> lessons;

    public List<LessonDto> getLessonsOfGivenNumberAndClass(String schoolClass, int number) {
        return lessons
                .stream()
                .filter(lesson -> lesson.number() == number)
                .filter(lesson -> lesson.schoolClass().equals(schoolClass))
                .sorted()
                .toList();
    }

    public List<LessonDto> getConcreteLesson(String schoolClass, String day, int number) {
        return lessons.stream()
                .filter(lesson -> lesson.schoolClass().equals(schoolClass))
                .filter(lesson -> lesson.day().equals(day))
                .filter(lesson -> lesson.number() == number)
                .toList();
    }

    public List<String> getAllSchoolClasses(){
        return lessons.stream().map(LessonDto::schoolClass).distinct().toList();
    }
}
