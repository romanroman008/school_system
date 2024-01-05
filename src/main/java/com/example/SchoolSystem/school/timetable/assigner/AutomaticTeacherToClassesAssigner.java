package com.example.SchoolSystem.school.timetable.assigner;

import com.example.SchoolSystem.school.entities.person.teacher.service.ITeacherService;
import com.example.SchoolSystem.school.entities.schoolSubject.SchoolSubject;
import com.example.SchoolSystem.school.entities.schoolSubject.service.ISchoolSubjectService;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.teacher.TeacherConverter;
import com.example.SchoolSystem.school.entities.person.teacher.Teacher;
import com.example.SchoolSystem.school.entities.schoolClass.SchoolClass;
import com.example.SchoolSystem.school.entities.schoolClass.service.ISchoolClassService;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.teacher.TeacherTimetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.schoolClass.ClassTimetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.schoolClass.SchoolClassConverter;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.subject.SubjectTimetable;
import com.example.SchoolSystem.school.timetable.validator.ITimetableValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class AutomaticTeacherToClassesAssigner implements IAutomaticTeacherToClassAssigner{

    private List<TeacherTimetable> timetableTeachers;
    private List<ClassTimetable> timetableClasses;


    private List<SchoolClass> classes;
    private List<Teacher> teachers;
    private List<SchoolSubject> subjects;


    private final ISchoolClassService schoolClassService;
    private final ITeacherService teacherService;
    private ISchoolSubjectService schoolSubjectService;


    private final ITimetableValidator timetableValidator;

    public AutomaticTeacherToClassesAssigner(@Autowired ISchoolClassService schoolClassService,
                                             @Autowired ITeacherService teacherService,
                                             @Autowired ISchoolSubjectService schoolSubjectService,
                                             @Autowired ITimetableValidator timetableValidator) {
        this.schoolClassService = schoolClassService;
        this.teacherService = teacherService;
        this.schoolSubjectService = schoolSubjectService;
        this.timetableValidator = timetableValidator;
    }

    private void initialize() {
        teachers = teacherService.findAll();
        classes = schoolClassService.findWithoutTeachersAssigned();
        subjects = schoolSubjectService.findAll();
        timetableClasses = SchoolClassConverter.toTimetable(classes);
        timetableTeachers = TeacherConverter.toTimetable(teachers);

    }

    public void assign() {
        initialize();

        timetableValidator.validate(teacherService.findAll(), schoolClassService.findAllNotGraduated());

        assignTeachersToClasses();
    }


    private void assignTeachersToClasses() {
        timetableClasses
                .forEach(schoolClass -> {
                    schoolClass
                            .getSubjectsWithoutAssignedTeachers()
                            .forEach(subject -> {
                                TeacherTimetable teacherTimetable = findTeacherForCertainSubject(subject)
                                        .assignHours(subject.getHoursPerWeek());
                                assignTeacherToClass(teacherTimetable.getId(), schoolClass.getId(), subject.getId());

                            });
                });
        schoolClassService.flush();
    }

    private void assignTeacherToClass(Long teacherId, Long schoolClassId, Long subjectId) {
        Teacher teacher = teachers
                .stream()
                .filter(t -> t.getId().equals(teacherId))
                .findFirst()
                .orElseThrow();
        SchoolClass schoolClass = classes
                .stream()
                .filter(c -> c.getId().equals(schoolClassId))
                .findFirst()
                .orElseThrow();
        SchoolSubject schoolSubject = subjects
                .stream()
                .filter(s -> s.getId().equals(subjectId))
                .findFirst()
                .orElseThrow();

        schoolClass.assignTeacherToSubject(schoolSubject, teacher);
    }

    private TeacherTimetable findTeacherForCertainSubject(SubjectTimetable subject) {
        return timetableTeachers
                .stream()
                .filter(teacher -> teacher.containsCertainSubject(subject.getName()))
                .filter(t -> t.checkIfHasSufficientAmountOfHours(subject.getHoursPerWeek()))
                .max(Comparator.comparingInt(TeacherTimetable::getHoursAvailable))
                .orElseThrow(() -> new NoSuchElementException(String.format("Teacher with sufficient amount of hours for subject %s not found", subject)));

    }

}
