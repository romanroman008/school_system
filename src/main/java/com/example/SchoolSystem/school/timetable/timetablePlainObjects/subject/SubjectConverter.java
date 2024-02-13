package com.example.SchoolSystem.school.timetable.timetablePlainObjects.subject;



import com.example.SchoolSystem.school.schoolClass.Grade;
import com.example.SchoolSystem.school.schoolSubject.SchoolSubject;

import java.util.List;

public class SubjectConverter {

    public static SubjectTimetable toTimetableObject(SchoolSubject subject, Grade grade){
        return new SubjectTimetable(
                subject.getId(),
                subject.getName(),
                subject.getHoursPerWeek(grade),
                grade);
    }

    public static List<SubjectTimetable> toTimetableObjectsList(List<SchoolSubject> subjects, Grade grade){
        return subjects.stream().map(subject -> toTimetableObject(subject,grade)).toList();
    }


}
