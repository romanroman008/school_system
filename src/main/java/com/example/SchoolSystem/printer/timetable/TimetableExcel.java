package com.example.SchoolSystem.printer.timetable;

import com.example.SchoolSystem.school.entities.lesson.Lesson;
import com.example.SchoolSystem.school.web.dto.lesson.LessonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

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
