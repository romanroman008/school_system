package com.example.SchoolSystem.school;

import com.example.SchoolSystem.school.person.Address;
import com.example.SchoolSystem.school.person.PersonInformation;
import com.example.SchoolSystem.school.schoolClass.Grade;
import com.example.SchoolSystem.school.student.Student;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Students {

    public static Student student(){
        return new Student(personInformation(),address(), Grade.I);
    }

    public static Student anotherStudent(){
        return new Student(secondPersonInformation(),secondAddress(),Grade.I);
    }

    public static List<Student> students(){
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            students.add(new Student(personInformation(String.valueOf(i)),address(),Grade.I));
        }
        return students;
    }

    public static List<Student> studentsWithSameIdNumbers(){
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            students.add(new Student(personInformation(),address(),Grade.I));
        }
        return students;
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
