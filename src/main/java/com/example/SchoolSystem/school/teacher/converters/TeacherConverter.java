package com.example.SchoolSystem.school.web.dto.teacher.converters;

import com.example.SchoolSystem.school.AppConfig;
import com.example.SchoolSystem.school.person.Address;
import com.example.SchoolSystem.school.person.PersonInformation;
import com.example.SchoolSystem.school.teacher.Teacher;
import com.example.SchoolSystem.school.schoolClass.SchoolClass;
import com.example.SchoolSystem.school.schoolSubject.SchoolSubject;
import com.example.SchoolSystem.school.teacher.TeacherDto;
import com.example.SchoolSystem.school.teacher.TeacherRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class TeacherConverter {


    private final AppConfig appConfig;

    public List<Teacher> fromRequest(List<TeacherRequest> requests){
        return requests.stream().map(this::fromRequest).toList();
    }
    public Teacher fromRequest(TeacherRequest request){
        return new Teacher(createPersonInformation(request),createAddress(request), request.getSalary(), request.getHoursPerWeek());
    }
    private Address createAddress(TeacherRequest request) {
        return new Address.Builder().setCity(request.getCity())
                .setStreet(request.getStreet())
                .setFlatNumber(request.getFlatNumber())
                .setHouseNumber(request.getHouseNumber())
                .build();
    }

    private PersonInformation createPersonInformation(TeacherRequest request) {
        return new PersonInformation.Builder()
                .setFirstName(request.getFirstName())
                .setLastName(request.getLastName())
                .setIDNumber(request.getIDNumber())
                .setBirthday(LocalDate.parse(request.getBirthday(), appConfig.getFormatter()))
                .setPhone(request.getPhone())
                .setEmail(request.getEmail())
                .build();

    }

    public List<TeacherDto> toDto(List<Teacher> teachers){
        return teachers.stream().map(this::toDto).toList();
    }
    public TeacherDto toDto(Teacher teacher){
        return new TeacherDto(
                teacher.getId(),
                teacher.getPersonInformation().getFirstName(),
                teacher.getPersonInformation().getLastName(),
                getSchoolSubjectsNames(teacher.getTeachingSubjects()),
                getSchoolClassesNames(teacher.getTeachingClasses())
        );
    }

    private List<String> getSchoolSubjectsNames(Set<SchoolSubject> subjects){
        return subjects.stream().map(SchoolSubject::getName).toList();
    }

    private List<String> getSchoolClassesNames(Set<SchoolClass> classes){
        return classes.stream().map(SchoolClass::getTotalGrade).toList();
    }

}
