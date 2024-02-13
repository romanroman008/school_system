package com.example.SchoolSystem.school.timetable.validator;

import com.example.SchoolSystem.school.teacher.Teacher;
import com.example.SchoolSystem.school.schoolClass.SchoolClass;
import com.example.SchoolSystem.school.exceptions.NotEnoughTeachersException;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.HireRecommendation;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.schoolClass.ClassTimetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.schoolClass.SchoolClassConverter;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.subject.SubjectTimetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.teacher.TeacherConverter;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.teacher.TeacherTimetable;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class TimetableValidatorImpl implements ITimetableValidator{

    List<TeacherTimetable> timetableTeachers;
    List<ClassTimetable> timetableClasses;
    List<SubjectTimetable> timetableSchoolSubjects;




    public void validate(List<Teacher> teachers, List<SchoolClass> classes) {
        initialize(teachers,classes);
        timetableClasses
                .forEach(schoolClass -> {
                    schoolClass.getSubjectsWithoutAssignedTeachers()
                            .forEach(subject -> {
                                TeacherTimetable teacher = findTeacherForConcreteSubject(subject);
                                teacher.assignHours(subject.getHoursPerWeek());
                            });
                });
    }

    private void initialize(List<Teacher> teachers, List<SchoolClass> classes) {

        timetableTeachers = new ArrayList<>(TeacherConverter.toTimetable(teachers));
        timetableClasses = new ArrayList<>(SchoolClassConverter.toTimetable(classes));
        timetableSchoolSubjects = new ArrayList<>(timetableClasses.stream().flatMap(schoolClass -> schoolClass.getAllSubjects().stream()).toList());
    }

    private TeacherTimetable findTeacherForConcreteSubject(SubjectTimetable subjectTimetable) {
        return timetableTeachers
                .stream()
                .filter(teacher -> teacher.containsCertainSubject(subjectTimetable.getName()))
                .filter(teacher -> teacher.checkIfHasSufficientAmountOfHours(subjectTimetable.getHoursPerWeek()))
                .findFirst()
                .orElseThrow(() -> new NotEnoughTeachersException(createHirerecommendation()));


    }

    private HireRecommendation createHirerecommendation(){
        return new HireRecommendation(timetableTeachers,timetableSchoolSubjects);
    }


}
