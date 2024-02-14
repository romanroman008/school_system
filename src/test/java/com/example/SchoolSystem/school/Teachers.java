package com.example.SchoolSystem.school;

import com.example.SchoolSystem.school.person.Address;
import com.example.SchoolSystem.school.person.PersonInformation;
import com.example.SchoolSystem.school.schoolClass.Grade;
import com.example.SchoolSystem.school.student.Student;
import com.example.SchoolSystem.school.teacher.Teacher;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Teachers {
    public static Teacher teacher(){
        return new Teacher(personInformation(),address(),10000, 40);
    }

    public static Teacher anotherTeacher(){
        return new Teacher(secondPersonInformation(),secondAddress(),10000,40);
    }

    public static List<Teacher> teachers(){
        List<Teacher> teachers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            teachers.add(new Teacher(personInformation(String.valueOf(i)),address(),10000,40));
        }
        return teachers;
    }

    public static List<Teacher> teachersWithSameIdNumbers(){
        List<Teacher> teachers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            teachers.add(new Teacher(personInformation(),address(),10000,40));
        }
        return teachers;
    }


    private static Address address(){
        return new Address.Builder()
                .setCity("City")
                .setStreet("Street")
                .setFlatNumber(12)
                .setHouseNumber(1)
                .build();
    }
    private static Address secondAddress(){
        return new Address.Builder()
                .setCity("City")
                .setStreet("Street")
                .setFlatNumber(12)
                .setHouseNumber(1)
                .build();
    }

    private static PersonInformation personInformation(){
        return new PersonInformation.Builder()
                .setFirstName("Firstname")
                .setLastName("Lastname")
                .setIDNumber("IDNumber")
                .setBirthday(LocalDate.parse("01-01-2017", DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .setPhone("phone")
                .setEmail("email@mail.com")
                .build();
    }
    private static PersonInformation secondPersonInformation(){
        return new PersonInformation.Builder()
                .setFirstName("Firstname")
                .setLastName("Lastname")
                .setIDNumber("IDNumber")
                .setBirthday(LocalDate.parse("01-01-2017", DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .setPhone("phone")
                .setEmail("email@mail.com")
                .build();
    }
    private static PersonInformation personInformation(String IDNumber){
        return new PersonInformation.Builder()
                .setFirstName("Firstname")
                .setLastName("Lastname")
                .setIDNumber(IDNumber)
                .setBirthday(LocalDate.parse("01-01-2017", DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .setPhone("phone")
                .setEmail("email@mail.com")
                .build();
    }
}
