package com.example.SchoolSystem.school.web.controllers;

import com.example.SchoolSystem.school.person.Address;
import com.example.SchoolSystem.school.person.PersonInformation;
import com.example.SchoolSystem.school.student.Student;
import com.example.SchoolSystem.school.student.service.IStudentService;
import com.example.SchoolSystem.school.schoolClass.Grade;
import com.example.SchoolSystem.school.student.StudentDto;
import com.example.SchoolSystem.school.student.StudentRequest;
import com.example.SchoolSystem.school.student.StudentConverter;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.EntityModel;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @InjectMocks
    StudentController studentController;

    @Mock
    IStudentService studentService;

    @Mock
    StudentConverter studentConverter;


    @Test
    public void should_AddStudent_And_DtoStudent() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        String birthday = "01-01-2017";


        Address address = new Address.Builder()
                .setCity("City")
                .setStreet("Street")
                .setFlatNumber(12)
                .setHouseNumber(1)
                .build();
        PersonInformation personInformation = new PersonInformation.Builder()
                .setFirstName("Firstname")
                .setLastName("Lastname")
                .setPesel("pesel")
                .setBirthday(LocalDate.parse(birthday, DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .setPhone("phone")
                .setEmail("email@mail.com")
                .build();

        Student student = new Student(personInformation,address, Grade.I);
        StudentDto studentDto = new StudentDto(1L,"Firstname", "Lastname", "Not assigned");
        StudentRequest studentRequest = new StudentRequest("Firstname", "Lastname", "pesel",birthday,"phone", "email@mail.com","City", "Street", 12,1,0);

        when(studentService.add(any(Student.class))).thenReturn(student);
        when(studentConverter.fromRequest(any(StudentRequest.class))).thenReturn(student);
        when(studentConverter.toDto(any(Student.class))).thenReturn(studentDto);

        EntityModel<StudentDto> result = studentController.add(studentRequest);


        assertEquals(result.getContent(), studentDto);

    }

    @Test
    public void should_AddStudent_And_Throw_EntityExistException() {
         MockHttpServletRequest request = new MockHttpServletRequest();
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        String birthday = "01-01-2017";


        Address address = new Address.Builder()
                .setCity("City")
                .setStreet("Street")
                .setFlatNumber(12)
                .setHouseNumber(1)
                .build();
        PersonInformation personInformation = new PersonInformation.Builder()

                .setFirstName("Firstname")
                .setLastName("Lastname")
                .setPesel("pesel")
                .setBirthday(LocalDate.parse(birthday, DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .setPhone("phone")
                .setEmail("email@mail.com")
                .build();

        Student student = new Student(personInformation,address, Grade.I);
        StudentRequest studentRequest = new StudentRequest("Firstname", "Lastname", "pesel",birthday,"phone", "email@mail.com","City", "Street", 12,1,0);

        when(studentService.add(any(Student.class))).thenThrow(EntityExistsException.class);
        when(studentConverter.fromRequest(any(StudentRequest.class))).thenReturn(student);


        Executable e = () -> studentController.add(studentRequest);


        assertThrows(EntityExistsException.class, e);


    }




}