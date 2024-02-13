package com.example.SchoolSystem.school.student;

import com.example.SchoolSystem.school.AppConfig;
import com.example.SchoolSystem.school.person.Address;
import com.example.SchoolSystem.school.person.PersonInformation;
import com.example.SchoolSystem.school.schoolClass.Grade;
import com.example.SchoolSystem.school.exceptions.StudentsAgeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StudentConverter {

    private final AppConfig appConfig;


    public List<Student> fromRequest(List<StudentRequest> requests) {
        return requests.stream().map(this::fromRequest).toList();
    }

    public Student fromRequest(StudentRequest request) {
        Address address = createAddress(request);
        PersonInformation personInformation = createPersonInformation(request);
        return new Student(personInformation, address, getGradeFromBirthday(request));
    }


    public StudentDto toDto(Student student){
        return new StudentDto(
                student.getId(),
                student.getPersonInformation().getFirstName(),
                student.getPersonInformation().getLastName(),
                student.getSchoolClass()
        );
    }



    public List<StudentDto> toDto(List<Student> students){
        return students
                .stream()
                .map(this::toDto).toList();
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
