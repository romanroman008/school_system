package com.example.SchoolSystem.school.web.dto.teacher.converters;

import com.example.SchoolSystem.school.AppConfig;
import com.example.SchoolSystem.school.entities.person.Address;
import com.example.SchoolSystem.school.entities.person.PersonInformation;
import com.example.SchoolSystem.school.entities.person.teacher.Teacher;
import com.example.SchoolSystem.school.entities.schoolClass.SchoolClass;
import com.example.SchoolSystem.school.entities.schoolSubject.SchoolSubject;
import com.example.SchoolSystem.school.web.dto.teacher.TeacherDto;
import com.example.SchoolSystem.school.web.dto.teacher.TeacherRequest;
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
        Teacher teacher = new Teacher();
        teacher.setAddress(createAddress(request));
        teacher.setHoursPerWeek(request.getHoursPerWeek());
        teacher.setPersonInformation(createPersonInformation(request));
        teacher.setSalary(request.getSalary());
        return teacher;
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
                .setPesel(request.getPesel())
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
