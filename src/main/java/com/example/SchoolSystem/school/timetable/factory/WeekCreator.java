package com.example.SchoolSystem.school.timetable.factory;

import com.example.SchoolSystem.school.AppConfig;
import com.example.SchoolSystem.school.exceptions.SchoolClassHasToManyLessonsPerWeekException;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.lesson.LessonTimetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.schoolClass.ClassTimetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.subject.SubjectTimetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.teacher.TeacherTimetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.time.Day;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.time.Hour;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.time.Week;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
class WeekCreator {

    private List<ClassTimetable> classes;

    private Week week;

    private final AppConfig appConfig;

    private List<LessonTimetable> lessons;

    private List<LessonTimetable> notAddedLessons;


    public Week create(List<TeacherTimetable> teachers, List<ClassTimetable> classes) {

        initialize(teachers, classes);

        createLessons();

        sortLessonsByHoursPerWeek();

        tryToAddEveryLessonToTimetable();

        return week;
    }

    private void initialize(List<TeacherTimetable> teachers, List<ClassTimetable> classes) {
        this.classes = new ArrayList<>(classes);
        week = new Week(teachers, classes, appConfig.getMaxHoursPerDay())   ;
    }


    private void createLessons() {
        lessons = new LinkedList<>();

        classes.forEach(schoolClass -> {

            schoolClass.getAllSubjects().forEach(subject -> {

                for (int i = 0; i < subject.getHoursPerWeek(); i++) {
                    lessons.add(createLesson(schoolClass, subject));
                }
            });
        });
    }

    private void sortLessonsByHoursPerWeek() {
        lessons = lessons
                .stream()
                .sorted(Comparator.comparingInt(lesson -> lesson.getSubject().getHoursPerWeek()))
                .toList();
    }


    private void tryToAddEveryLessonToTimetable() {
        List<LessonTimetable> notAddedLessons = new ArrayList<>();

        lessons.forEach(lesson -> {
            boolean lessonAdded = false;
            List<Day> daysChecked = new ArrayList<>();
            Day day;

            while (daysChecked.size() < 5 && lessonAdded == false) {

                day = week.getDayWithLeastAmountOfHoursExceptGivenDays(lesson.getSchoolClass(), daysChecked);

                lessonAdded = tryToAddLessonToDay(lesson, day);

                daysChecked.add(day);
            }
            if (lessonAdded == false) {
                notAddedLessons.add(lesson);
            }
        });

        if (!notAddedLessons.isEmpty())
            throw new SchoolClassHasToManyLessonsPerWeekException("Amount of max lessons per day is too small to fit all subjects lessons");
    }

    private boolean tryToAddLessonToDay(LessonTimetable lesson, Day day) {
        if (checkIfThereIsAnAvailableHourInDay(day, lesson.getTeacher(), lesson.getSchoolClass())) {
            Hour hour = getFirstAvailableHourInDay(day, lesson.getTeacher(), lesson.getSchoolClass());
            lesson.changeLessonNumber(hour.getNumber(), hour.getDay());
            hour.addLesson(lesson);
            return true;
        }
        return false;
    }

    private boolean checkIfThereIsAnAvailableHourInDay(Day day, TeacherTimetable teacher, ClassTimetable schoolClass) {
        return week.getDay(day.getName()).getHours()
                .stream()
                .filter(hour -> hour.getAvailableClasses().contains(schoolClass))
                .anyMatch(hour -> hour.getAvailableTeachers().contains(teacher));
    }

    private Hour getFirstAvailableHourInDay(Day day, TeacherTimetable teacher, ClassTimetable classTimetable) {
        return week.getDay(day.getName()).getHours()
                .stream()
                .filter(hour -> hour.getAvailableTeachers().contains(teacher))
                .filter(hour -> hour.getAvailableClasses().contains(classTimetable))
                .min(Comparator.comparingInt(Hour::getNumber))
                .orElseThrow(NoSuchElementException::new);
    }


    private LessonTimetable createLesson(ClassTimetable classTimetable, SubjectTimetable subject) {
        TeacherTimetable teacher = classTimetable.getTeacher(subject);
        return new LessonTimetable(classTimetable, subject, teacher);

    }



}
