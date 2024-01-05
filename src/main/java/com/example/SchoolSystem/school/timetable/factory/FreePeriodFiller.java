package com.example.SchoolSystem.school.timetable.factory;

import com.example.SchoolSystem.school.AppConfig;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.lesson.LessonTimetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.schoolClass.ClassTimetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.subject.SubjectTimetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.teacher.TeacherTimetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.time.Day;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.time.Hour;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.time.Week;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@Component
class FreePeriodFiller {

    private Week week;

    private final AppConfig appConfig;

    Week fill(Week week) {
        this.week = week;

        tryToFillFreePeriods();

        return week;

    }


    private void tryToFillFreePeriods() {

        int i = 0;
        while (i < appConfig.getMaxAttemptsToFillFreePeriods() && !week.findAllFreePeriods().isEmpty()) {
            tryToFillFreePeriodsOfSchoolClassWithinLessonsOfThisClass();
            tryToFillFreePeriodsOfSchoolClassWithinLessonsOfEveryClass();
            i++;
        }

    }

    private void tryToFillFreePeriodsOfSchoolClassWithinLessonsOfThisClass() {
        AtomicInteger amount = new AtomicInteger();
        week.classes.forEach(schoolClass -> {

            List<Hour> freePeriods = week.findFreePeriodsHoursOfGivenClass(schoolClass);
            amount.addAndGet(freePeriods.size());

            freePeriods.forEach(freePeriod -> {

                List<LessonTimetable> edgeLessonsContainingAvailableTeacher = findEdgeLessonsThatContainsAvailableTeacher(freePeriod.getAvailableTeachers(), schoolClass);
                if (edgeLessonsContainingAvailableTeacher.isEmpty())
                    return;

                LessonTimetable lesson = getLessonFromDayWithTheMostAmountOfHours(edgeLessonsContainingAvailableTeacher);
                week.moveLessonToGivenHour(lesson, freePeriod);

            });

        });

        System.out.println(amount);
    }


    private List<LessonTimetable> findEdgeLessonsThatContainsAvailableTeacher(Set<TeacherTimetable> availableTeachers, ClassTimetable schoolClass) {
        List<LessonTimetable> edgeLessonTimetables = week.findEdgeLessonsOfGivenSchoolClass(schoolClass);
        return edgeLessonTimetables
                .stream()
                .filter(lesson -> availableTeachers.contains(lesson.getTeacher()))
                .toList();
    }


    private List<TeacherTimetable> getEdgeLessonsTeacherOfGivenClassExceptTeachersAlreadyAvailable(Set<TeacherTimetable> teachersAlreadyAvailable, ClassTimetable schoolClass) {

        List<LessonTimetable> lessonTimetables = findEdgeLessonsForConcreteClass(schoolClass);
        List<TeacherTimetable> edgeLessonsTeachers = new ArrayList<>();
        lessonTimetables.forEach(lesson -> edgeLessonsTeachers.add(lesson.getTeacher()));

        edgeLessonsTeachers.removeAll(teachersAlreadyAvailable);

        return edgeLessonsTeachers;

    }


    private void tryToFillFreePeriodsOfSchoolClassWithinLessonsOfEveryClass() {
        AtomicInteger amount = new AtomicInteger();

        week.classes.forEach(schoolClass -> {

            List<Hour> freePeriods = week.findFreePeriodsHoursOfGivenClass(schoolClass);

            amount.addAndGet(freePeriods.size());

            freePeriods.forEach(freePeriod -> {

                List<TeacherTimetable> edgeLessonsTeachers = getEdgeLessonsTeacherOfGivenClassExceptTeachersAlreadyAvailable(freePeriod.getAvailableTeachers(), schoolClass);
                List<LessonTimetable> edgeTeachersLessonDuringFreePeriod = new ArrayList<>();
                edgeLessonsTeachers.forEach(teacher -> {
                    LessonTimetable lessonTimetable = getTeacherLessonAtGivenTime(freePeriod, teacher);
                    if (checkIfLessonCanBeMovedToAnotherHour(lessonTimetable)) {
                        edgeTeachersLessonDuringFreePeriod.add(lessonTimetable);
                    }
                });

                if (edgeTeachersLessonDuringFreePeriod.isEmpty())
                    return;

                List<LessonTimetable> edgeLessonsWithFreePeriodAvailableTeachers = getEdgeLessonsContainingTeachersOfGivenLessons(edgeTeachersLessonDuringFreePeriod);

                if (edgeLessonsWithFreePeriodAvailableTeachers.isEmpty())
                    return;

                LessonTimetable edgeLessonWithFreePeriodAvailableTeacher = getLessonFromDayWithTheMostAmountOfHours(edgeLessonsWithFreePeriodAvailableTeachers);
                moveLessonToDayWithTheLeastAmountOfHours(edgeLessonWithFreePeriodAvailableTeacher);

                SubjectTimetable subjectOfReleasedTeacher = schoolClass.getTeachingSubjectOfGivenTeacher(edgeLessonWithFreePeriodAvailableTeacher.getTeacher());
                LessonTimetable lessonTimetableToMove = getEdgeLessonWithConcreteSubjectFromDayWithTheMostAmountOfHours(subjectOfReleasedTeacher, schoolClass);
                week.moveLessonToGivenHour(lessonTimetableToMove, freePeriod);


            });


        });
        System.out.println(amount);
    }


    private LessonTimetable getTeacherLessonAtGivenTime(Hour hour, TeacherTimetable teacher) {
        return hour
                .getLessons()
                .stream()
                .filter(lesson -> lesson.getTeacher().equals(teacher))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }


    private List<LessonTimetable> findEdgeLessonsForConcreteClass(ClassTimetable schoolClass) {
        List<LessonTimetable> edgeLessonTimetables = new ArrayList<>();
        week.days.forEach(day -> {
            edgeLessonTimetables.addAll(day.getFirstLesson(schoolClass));
            edgeLessonTimetables.addAll(day.getLastLesson(schoolClass));
        });
        return edgeLessonTimetables;
    }


    private LessonTimetable getEdgeLessonWithConcreteSubjectFromDayWithTheMostAmountOfHours(SubjectTimetable subject, ClassTimetable schoolClass) {

        return week.findEdgeLessonsOfGivenSchoolClass(schoolClass)
                .stream()
                .filter(lesson -> lesson.getSubject().equals(subject))
                .max(Comparator.comparingInt(lesson -> week.getLessonDayLength(lesson)))
                .orElseThrow(() -> new NoSuchElementException("Edge lesson with concrete subject does not exist"));


    }


    private List<LessonTimetable> getEdgeLessonsContainingTeachersOfGivenLessons(List<LessonTimetable> lessons) {
        List<TeacherTimetable> teachersOfGivenLessons = lessons
                .stream()
                .map(LessonTimetable::getTeacher)
                .toList();

        return getAllEdgeLessons()
                .stream()
                .filter(lesson -> teachersOfGivenLessons.contains(lesson.getTeacher()))
                .toList();


    }

    private LessonTimetable getLessonFromDayWithTheLeastAmountOfHours(List<LessonTimetable> lessons) {
        return lessons
                .stream()
                .min(Comparator.comparingInt(lesson -> week.getLessonDayLength(lesson)))
                .orElseThrow(() -> new NoSuchElementException("Lesson from day with the most amount of hours is not found"));
    }

    private LessonTimetable getLessonFromDayWithTheMostAmountOfHours(List<LessonTimetable> lessons) {
        return lessons
                .stream()
                .max(Comparator.comparingInt(lesson -> week.getLessonDayLength(lesson)))
                .orElseThrow(() -> new NoSuchElementException("Lesson from day with the most amount of hours is not found"));
    }

    private void moveLessonToDayWithTheLeastAmountOfHours(LessonTimetable lessonTimetable) {
        List<Day> daysChecked = new ArrayList<>();
        boolean lessonMoved = false;

        while (daysChecked.size() < 5 && lessonMoved == false) {
            Day day = week.getDayWithLeastAmountOfHoursExceptGivenDays(lessonTimetable.getSchoolClass(), daysChecked);
            daysChecked.add(day);
            if (day.checkIfLessonCanBeAdded(lessonTimetable)) {
                int availableHourNumber = day.getAvailableHourNumber(lessonTimetable);
                Hour destinationHour = day.getHour(availableHourNumber);
                week.moveLessonToGivenHour(lessonTimetable, destinationHour);
                lessonMoved = true;
            }
        }

    }


    private List<LessonTimetable> getAllEdgeLessons() {
        List<LessonTimetable> edgeLessonTimetables = new ArrayList<>();
        week.classes.forEach(schoolClass -> {
            edgeLessonTimetables.addAll(week.findEdgeLessonsOfGivenSchoolClass(schoolClass));
        });
        return edgeLessonTimetables;
    }

    private boolean checkIfLessonCanBeMovedToAnotherHour(LessonTimetable lessonTimetable) {
        return week
                .days
                .stream()
                .anyMatch(day -> day.checkIfLessonCanBeAdded(lessonTimetable));

    }


}
