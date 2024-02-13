package com.example.SchoolSystem.school.timetable.timetablePlainObjects.teacher;


import com.example.SchoolSystem.school.teacher.Teacher;

import java.util.List;

public class TeacherConverter {
    public static TeacherTimetable toTimetable(Teacher teacher){
        TeacherTimetable teacherTimetable = new TeacherTimetable(teacher.getId(), teacher.getFullName(), teacher.getHoursPerWeek());
        teacher.getTeachingSubjects().forEach(subject -> teacherTimetable.addTeachingSubject(subject.getName()));
        return teacherTimetable;
    }

    public static List<TeacherTimetable> toTimetable(List<Teacher> teachers){
        return teachers.stream().map(TeacherConverter::toTimetable).toList();
    }

}
