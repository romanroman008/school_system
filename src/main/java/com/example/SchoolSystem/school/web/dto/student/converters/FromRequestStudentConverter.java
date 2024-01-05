package com.example.SchoolSystem.school.web.dto.student.converters;

import com.example.SchoolSystem.school.AppConfig;
import com.example.SchoolSystem.school.entities.person.Address;
import com.example.SchoolSystem.school.entities.person.PersonInformation;
import com.example.SchoolSystem.school.entities.person.student.Student;
import com.example.SchoolSystem.school.entities.schoolClass.Grade;
import com.example.SchoolSystem.school.exceptions.StudentsAgeException;
import com.example.SchoolSystem.school.web.dto.student.StudentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FromRequestStudentConverter {

    private final AppConfig appConfig;


    public List<Student> convert(List<StudentRequest> requests) {
        return requests.stream().map(this::convert).toList();
    }

    public Student convert(StudentRequest request) {
        Address address = createAddress(request);
        PersonInformation personInformation = createPersonInformation(request);
        return new Student(personInformation, address, getGradeFromBirthday(request));
    }


    private Address createAddress(StudentRequest request) {
        return new Address.Builder().setCity(request.getCity())
                .setStreet(request.getStreet())
                .setFlatNumber(request.getFlatNumber())
                .setHouseNumber(request.getHouseNumber())
                .build();

    }

    private PersonInformation createPersonInformation(StudentRequest request) {

        return new PersonInformation.Builder()
                .setFirstName(request.getFirstName())
                .setLastName(request.getLastName())
                .setPesel(request.getPesel())
                .setBirthday(LocalDate.parse(request.getBirthday(), appConfig.getFormatter()))
                .setPhone(request.getPhone())
                .setEmail(request.getEmail())
                .build();

    }
    private Grade getGradeFromBirthday(StudentRequest request){
        LocalDate birthday = LocalDate.parse(request.getBirthday(), appConfig.getFormatter());
        int gradeNumber = LocalDate.now().getYear() - birthday.getYear() - appConfig.getFirstStudentYearAge() + request.getGradeToBirthdayDeflection();
        return Grade.valueOf(gradeNumber)
                .orElseThrow(() -> new StudentsAgeException("Student is too young or too old for this school",request));
    }


}
