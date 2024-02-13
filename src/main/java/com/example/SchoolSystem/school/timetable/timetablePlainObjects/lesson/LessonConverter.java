package com.example.SchoolSystem.school.timetable.timetablePlainObjects.lesson;


import com.example.SchoolSystem.school.lesson.Lesson;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.schoolClass.ClassTimetable;
import com.example.SchoolSystem.school.lesson.LessonDto;

import java.util.List;

public class LessonConverter {

    public static List<LessonDto> toDtoFromEntity(List<Lesson> lessons){
        return lessons
                .stream()
                .map(LessonConverter::toDtoFromEntity)
                .toList();
    }

    public static LessonDto toDtoFromEntity(Lesson lesson){
        return new LessonDto(
                lesson.getId(),
                lesson.getSortingNumber(),
                lesson.getNumber(),
                lesson.getDay(),
                lesson.getSchoolClass(),
                lesson.getSubject(),
                lesson.getTeacher()
        );
    }

    public static Lesson toEntityFromTimetable(LessonTimetable lessonTimetable){
        return new Lesson(
                createSortingNumber(lessonTimetable),
                lessonTimetable.getNumber(),
                lessonTimetable.getDay().toString(),
                lessonTimetable.getSchoolClass().getTotalGrade(),
                lessonTimetable.getSubject().getName(),
                lessonTimetable.getTeacher().getName()
        );
    }

    public static List<Lesson> toEntityFromTimetable(List<LessonTimetable> lessonTimetables){
        return lessonTimetables
                .stream()
                .map(LessonConverter::toEntityFromTimetable)
                .toList();
    }

    private static long createSortingNumber(LessonTimetable lessonTimetable){
        long sortingNumber;
        ClassTimetable classTimetable = lessonTimetable.getSchoolClass();


        long gradeNumber = classTimetable.getGrade().getValue();
        long alphabeticalGradeNumber = classTimetable.getAlphabeticalGrade().getValue();
        long dayNumber = lessonTimetable.getDay().getValue();
        long lessonNumber = lessonTimetable.getNumber();

        sortingNumber = gradeNumber * 10000 + alphabeticalGradeNumber * 1000 + dayNumber * 100 + lessonNumber;
        return sortingNumber;
    }

}
