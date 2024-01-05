package com.example.SchoolSystem.school.entities.person.employee.teacher;

import static org.junit.jupiter.api.Assertions.*;

import com.example.SchoolSystem.school.entities.person.PersonInformation;
import com.example.SchoolSystem.school.entities.person.teacher.Teacher;
import com.example.SchoolSystem.school.entities.schoolClass.SchoolClass;
import com.example.SchoolSystem.school.entities.schoolSubject.SchoolSubject;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TeacherTest {

    @Test
    void addTeachingClass_Should_AddTeachingClass() {
        //given
        Teacher teacher = new Teacher();
        SchoolClass schoolClass = new SchoolClass();

        //when
        teacher.addTeachingClass(schoolClass);

        //then
        assertTrue(teacher.getTeachingClasses().contains(schoolClass));
    }

    @Test
    void removeTeachingClass_Should_RemoveTeachingClass() {
        //given
        Teacher teacher = new Teacher();
        SchoolClass schoolClass = new SchoolClass();
        teacher.addTeachingClass(schoolClass);

        //when
        teacher.removeTeachingClass(schoolClass);

        //then
        assertFalse(teacher.getTeachingClasses().contains(schoolClass));
    }

    @Test
    void addTeachingSubject_Should_AddTeachingSubject() {
        //given
        Teacher teacher = new Teacher();
        SchoolSubject schoolSubject = new SchoolSubject();

        //when
        teacher.addTeachingSubject(schoolSubject);

        //then
        assertTrue(teacher.getTeachingSubjects().contains(schoolSubject));
    }

    @Test
    void getFullName_Should_ReturnFullName() {
        //given
        Teacher teacher = new Teacher();
        PersonInformation personInformation = new PersonInformation.Builder()
                .setFirstName("John")
                .setLastName("Doe")
                .setPesel("12345678901")
                .build();
        teacher.setPersonInformation(personInformation);

        //when
        String fullName = teacher.getFullName();

        //then
        assertEquals("John Doe", fullName);
    }

    @Test
    void getSalary_Should_ReturnSalary() {
        //given
        Teacher teacher = new Teacher();
        float salary = 50000;
        teacher.setSalary(salary);

        //when
        float result = teacher.getSalary();

        //then
        assertEquals(salary, result);
    }

    @Test
    void setSalary_Should_SetSalary() {
        //given
        Teacher teacher = new Teacher();
        float salary = 60000;

        //when
        teacher.setSalary(salary);

        //then
        assertEquals(salary, teacher.getSalary());
    }

    // Add more tests for other methods and scenarios
}
