package com.example.SchoolSystem.school.entities.person.student;

import static org.junit.jupiter.api.Assertions.*;

import com.example.SchoolSystem.school.entities.person.Address;
import com.example.SchoolSystem.school.entities.person.PersonInformation;
import com.example.SchoolSystem.school.entities.schoolClass.Grade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StudentTest {

    @Test
    void getFullName_Should_ReturnFullName() {
        //given
        PersonInformation personInformation = new PersonInformation.Builder()
                .setFirstName("Alice")
                .setLastName("Smith")
                .setPesel("12345678901")
                .build();

        Address address = new Address.Builder()
                .setHouseNumber(123)
                .setStreet("Main Street")
                .setCity("Cityville")
                .build();

        Student student = new Student(personInformation, address, Grade.I);

        //when
        String fullName = student.getFullName();

        //then
        assertEquals("Alice Smith", fullName);
    }

    @Test
    void isFirstYearStudent_Should_ReturnTrue_ForFirstYearStudent() {
        //given
        PersonInformation personInformation = new PersonInformation.Builder()
                .setFirstName("Bob")
                .setLastName("Johnson")
                .setPesel("9876543210")
                .build();

        Address address = new Address.Builder()
                .setHouseNumber(456)
                .setStreet("Oak Avenue")
                .setCity("Townsville")
                .build();

        Student firstYearStudent = new Student(personInformation, address, Grade.I);

        //then
        assertTrue(firstYearStudent.isAssignedToClass());
    }

    @Test
    void isAssignedToClass_Should_ReturnFalse_ByDefault() {
        //given
        PersonInformation personInformation = new PersonInformation.Builder()
                .setFirstName("Charlie")
                .setLastName("Brown")
                .setPesel("5555555555")
                .build();

        Address address = new Address.Builder()
                .setHouseNumber(789)
                .setStreet("Pine Road")
                .setCity("Villagetown")
                .build();

        Student student = new Student(personInformation, address, Grade.I);

        //then
        assertFalse(student.isAssignedToClass());
    }

    // Add more tests for other methods and scenarios as needed
}
