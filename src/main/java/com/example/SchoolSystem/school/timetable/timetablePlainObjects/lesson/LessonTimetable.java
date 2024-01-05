package com.example.SchoolSystem.school.timetable.timetablePlainObjects.lesson;


import com.example.SchoolSystem.school.timetable.timetablePlainObjects.time.DayName;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.schoolClass.ClassTimetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.subject.SubjectTimetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.teacher.TeacherTimetable;
import lombok.Getter;

import java.util.Comparator;
import java.util.Objects;
import java.util.UUID;

@Getter
public class LessonTimetable {

    private final UUID id;
    private int number;
    private DayName day;
    private final ClassTimetable schoolClass;
    private final SubjectTimetable subject;
    private final TeacherTimetable teacher;

    public LessonTimetable(ClassTimetable schoolClass, SubjectTimetable subject, TeacherTimetable teacher) {
        this.id = UUID.randomUUID();
        this.schoolClass = schoolClass;
        this.subject = subject;
        this.teacher = teacher;
    }

    public void changeLessonNumber(int number, DayName day){ //todo walidacja
        this.number = number;
        this.day = day;
    }


    @Override
    public String toString() {
        return day.toString() + " " + number + " " + subject + " " + getSchoolClass().toString() + " " + getTeacher().getName();
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LessonTimetable lessonTimetable = (LessonTimetable) o;
        return Objects.equals(id, lessonTimetable.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public static class LessonComparator implements Comparator<LessonTimetable> {
        @Override
        public int compare(LessonTimetable lessonTimetable1, LessonTimetable lessonTimetable2){

            int classComparison = lessonTimetable1.getSchoolClass().compareTo(lessonTimetable2.getSchoolClass());

            if(classComparison != 0)
                return classComparison;


            int dayComparison = lessonTimetable1.getDay().compareTo(lessonTimetable2.getDay());

            if(dayComparison != 0){
                return dayComparison;
            }

            return Integer.compare(lessonTimetable1.getNumber(), lessonTimetable2.getNumber());
        }

    }

}
