package com.example.SchoolSystem.school.timetable.timetablePlainObjects.time;


import com.example.SchoolSystem.school.timetable.timetablePlainObjects.lesson.LessonTimetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.schoolClass.ClassTimetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.teacher.TeacherTimetable;
import lombok.Getter;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;
import java.util.*;

public class Day {


    @Getter
    private DayName name;

    private int MAX_HOURS_PER_DAY;
    @Getter
    private List<Hour> hours = new LinkedList<>();
    Set<TeacherTimetable> teachers;
    Set<ClassTimetable> classes;


    public Day(DayName name,Set<TeacherTimetable> teachers, Set<ClassTimetable> classes, int maxHoursPerDay) {
        this.name = name;
        this.teachers = teachers;
        this.classes = classes;
        MAX_HOURS_PER_DAY = maxHoursPerDay;
        initializeHours();
    }



    private void initializeHours() {
        for (int i = 0; i < MAX_HOURS_PER_DAY; i++) {
            createHour(i);
        }
    }

    private void createHour(int number) {
        hours.add(new Hour(number, name, new HashSet<>(teachers), new HashSet<>(classes)));
    }


    private boolean checkIfTeacherIsAvailableInThisDay(TeacherTimetable teacherTimetable) {
        return hours
                .stream()
                .anyMatch(hour -> hour.checkIfTeacherIsAvailable(teacherTimetable));
    }

    public int getFirstLessonNumber(ClassTimetable schoolClass) {
        return hours
                .stream()
                .flatMap(hour -> hour.getLessons().stream())
                .filter(lesson -> lesson.getSchoolClass().equals(schoolClass))
                .map(LessonTimetable::getNumber)
                .min(Integer::compare)
                .orElseThrow(() -> new NoSuchElementException("First lesson does not exist"));
    }

    public int getLastLessonNumber(ClassTimetable schoolClass) {
        return hours
                .stream()
                .flatMap(hour -> hour.getLessons().stream())
                .filter(lesson -> lesson.getSchoolClass().equals(schoolClass))
                .map(LessonTimetable::getNumber)
                .max(Integer::compare)
                .orElseThrow(() -> new NoSuchElementException("Last lesson does not exist"));
    }


    public List<LessonTimetable> getFirstLesson(ClassTimetable schoolClass) {
        Optional<LessonTimetable> earliestLesson = hours
                .stream()
                .filter(hour -> !hour.checkIfDoesNotContainsConcreteSchoolClass(schoolClass))
                .flatMap(hour -> hour.getLesson(schoolClass).stream())
                .min(Comparator.comparingInt(LessonTimetable::getNumber));

        return earliestLesson.map(Collections::singletonList).orElse(Collections.emptyList());
    }


    public List<LessonTimetable> getLastLesson(ClassTimetable schoolClass) {
        Optional<LessonTimetable> lastLesson = hours
                .stream()
                .filter(hour -> !hour.checkIfDoesNotContainsConcreteSchoolClass(schoolClass))
                .flatMap(hour -> hour.getLesson(schoolClass).stream())
                .max(Comparator.comparingInt(LessonTimetable::getNumber));

        return lastLesson.map(Collections::singletonList).orElse(Collections.emptyList());
    }

    public int getLessonAmountOfGivenSchoolClass(ClassTimetable schoolClass) {
        return (int) hours
                .stream()
                .mapToLong(hour -> hour.getLesson(schoolClass).size())
                .sum();
    }


    public boolean checkIfLessonCanBeAdded(LessonTimetable lessonTimetable) {
        try {
            getAvailableHourNumber(lessonTimetable);
            return true;

        } catch (NoSuchElementException e) {
            return false;
        }

    }

    public Hour getHour(int hourNumber){
        return hours.get(hourNumber);
    }

    public int getAvailableHourNumber(LessonTimetable lessonTimetable) {
        try {
            if (checkIfTeacherIsAvailableInThisDay(lessonTimetable.getTeacher())) {
                int firstLessonNumber = getFirstLessonNumber(lessonTimetable.getSchoolClass());
                int lastLessonNumber = getLastLessonNumber(lessonTimetable.getSchoolClass());
                int destinationNumber;

                if (firstLessonNumber > 0) {
                    destinationNumber = firstLessonNumber - 1;
                    if (hours.get(destinationNumber).checkIfTeacherIsAvailable(lessonTimetable.getTeacher()))
                        return destinationNumber;

                } else if (lastLessonNumber < MAX_HOURS_PER_DAY - 1) {
                    destinationNumber = lastLessonNumber + 1;
                    if (hours.get(destinationNumber).checkIfTeacherIsAvailable(lessonTimetable.getTeacher()))
                        return destinationNumber;
                }
            }

        } catch (NoSuchElementException e) {
            return 0;
        }

        throw new NoSuchElementException("Day is filled with lessons");
    }

    public void addLessonToGivenHour(LessonTimetable lessonTimetable, int hourNumber) {
        if (hourNumber < 0 || hourNumber > MAX_HOURS_PER_DAY - 1)
            throw new InvalidParameterException();

        hours.get(hourNumber).addLesson(lessonTimetable);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Day day = (Day) o;
        return Objects.equals(name, day.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name.toString();
    }


}
