package com.example.SchoolSystem.school.timetable.timetablePlainObjects.time;



import com.example.SchoolSystem.school.timetable.timetablePlainObjects.lesson.FreePeriod;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.lesson.LessonTimetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.schoolClass.ClassTimetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.teacher.TeacherTimetable;
import org.springframework.stereotype.Component;

import java.util.*;



public class Week {

    public List<Day> days = new LinkedList<>();


   public final List<ClassTimetable> classes;


    public Week(List<TeacherTimetable> teachers, List<ClassTimetable> classes, int maxHoursPerWeek) {
        createDays(teachers, classes,maxHoursPerWeek);
        this.classes = new ArrayList<>(classes);
    }

    private void createDays(List<TeacherTimetable> teachers, List<ClassTimetable> classes, int maxHoursPerDay) {

        days.add(new Day(DayName.MONDAY,new HashSet<>(teachers),new HashSet<>(classes),maxHoursPerDay));
        days.add(new Day(DayName.TUESDAY, new HashSet<>(teachers),new HashSet<>(classes),maxHoursPerDay));
        days.add(new Day(DayName.WEDNESDAY,  new HashSet<>(teachers),new HashSet<>(classes),maxHoursPerDay));
        days.add(new Day(DayName.THURSDAY, new HashSet<>(teachers),new HashSet<>(classes),maxHoursPerDay));
        days.add(new Day(DayName.FRIDAY, new HashSet<>(teachers),new HashSet<>(classes),maxHoursPerDay));
    }


    public Day getDayWithLeastAmountOfHoursExceptGivenDays(ClassTimetable schoolClass, List<Day> givenDays) {
        return days
                    .stream()
                    .filter(day -> !givenDays.contains(day))
                    .min(Comparator.comparingInt(d -> d.getLessonAmountOfGivenSchoolClass(schoolClass)))
                    .orElseThrow();

    }


    public Day getDay(DayName dayName) {
        return days.stream().filter(day -> day.getName() == dayName).findFirst().get();
    }


    public Hour getLessonHour(LessonTimetable lessonTimetable) {
        try{

            List<LessonTimetable> lekcje = days
                    .stream()
                    .flatMap(day -> day.getHours().stream())
                    .flatMap(ha -> ha.getLessons().stream())
                    .toList();

            Hour h = days
                    .stream()
                    .flatMap(day -> day.getHours().stream())
                    .filter(hour -> hour.lessonTimetables.contains(lessonTimetable))
                    .findFirst()
                    .orElseThrow();


            return h;
        }catch (NoSuchElementException e){
            return null;
        }

    }

    public int getLessonDayLength(LessonTimetable lessonTimetable) {
        return days.stream()
                .filter(day -> day.getHours()
                        .stream()
                        .anyMatch(hour -> hour.getLessons().contains(lessonTimetable)))
                .findFirst()
                .get()
                .getLessonAmountOfGivenSchoolClass(lessonTimetable.getSchoolClass());

    }

    public void moveLessonToGivenHour(LessonTimetable lessonTimetable, Hour destinationHour){
        Hour previusHour = getLessonHour(lessonTimetable);
        previusHour.removeLesson(lessonTimetable);
        lessonTimetable.changeLessonNumber(destinationHour.getNumber(),destinationHour.getDay());
        destinationHour.addLesson(lessonTimetable);

    }



    public List<FreePeriod> findAllFreePeriods() {
        List<FreePeriod> freePeriods = new ArrayList<>();
        classes.forEach(schoolClass -> {
            freePeriods.addAll(findFreePeriodsOfGivenClass(schoolClass));
        });
        return freePeriods;
    }


    public List<Hour> findFreePeriodsHoursOfGivenClass(ClassTimetable schoolClass) {
        List<Hour> freePeriods = new ArrayList<>();

        days.forEach(day -> {
            if (day.getLessonAmountOfGivenSchoolClass(schoolClass) == 0)
                return;
            int lessonsStartIndex = day.getFirstLessonNumber(schoolClass);
            int lessonEndIndex = day.getLastLessonNumber(schoolClass);
            day.getHours().forEach(hour -> {
                int number = hour.getNumber();
                if (number > lessonsStartIndex &&
                        number < lessonEndIndex &&
                        hour.checkIfDoesNotContainsConcreteSchoolClass(schoolClass)
                ) {
                    freePeriods.add(hour);
                }
            });
        });

        return freePeriods;
    }

    public List<FreePeriod> findFreePeriodsOfGivenClass(ClassTimetable schoolClass) {
        List<FreePeriod> freePeriods = new ArrayList<>();

        days.forEach(day -> {
            if (day.getLessonAmountOfGivenSchoolClass(schoolClass) == 0)
                return;
            int lessonsStartIndex = day.getFirstLessonNumber(schoolClass);
            int lessonEndIndex = day.getLastLessonNumber(schoolClass);
            day.getHours().forEach(hour -> {
                int number = hour.getNumber();
                if (number > lessonsStartIndex &&
                        number < lessonEndIndex &&
                        hour.checkIfDoesNotContainsConcreteSchoolClass(schoolClass)
                ) {
                    freePeriods.add(createFreePeriod(number, hour, schoolClass));
                }
            });
        });

        return freePeriods;
    }

    private FreePeriod createFreePeriod(int number, Hour hour, ClassTimetable schoolClass) {
        String day = hour.getDay().toString();
        String schoolClassName = schoolClass.getTotalGrade();
        List<String> availableTeachers = hour.getAvailableTeachers()
                .stream()
                .map(TeacherTimetable::getName)
                .toList();

        return new FreePeriod(number, day, schoolClassName, availableTeachers);

    }


    public List<LessonTimetable> findEdgeLessonsOfGivenSchoolClass(ClassTimetable schoolClass) {

        List<LessonTimetable> edgeLessonTimetables = new ArrayList<>();
        days.forEach(day -> {
            edgeLessonTimetables.addAll(day.getFirstLesson(schoolClass));
            edgeLessonTimetables.addAll(day.getLastLesson(schoolClass));
        });
        return edgeLessonTimetables;
    }





}
