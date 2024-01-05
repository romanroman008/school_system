package com.example.SchoolSystem.school.web.dto.teacher.converters;

import com.example.SchoolSystem.school.AppConfig;
import com.example.SchoolSystem.school.entities.person.Address;
import com.example.SchoolSystem.school.entities.person.PersonInformation;
import com.example.SchoolSystem.school.entities.person.teacher.Teacher;
import com.example.SchoolSystem.school.web.dto.teacher.TeacherRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Component
public class FromRequestTeacherConverter {


    private final AppConfig appConfig;

    public List<Teacher> convert(List<TeacherRequest> requests){
        return requests.stream().map(this::convert).toList();
    }
    public Teacher convert(TeacherRequest request){
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

}
