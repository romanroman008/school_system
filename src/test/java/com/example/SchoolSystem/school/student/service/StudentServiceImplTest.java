package com.example.SchoolSystem.school.student.service;

import com.example.SchoolSystem.school.Students;
import com.example.SchoolSystem.school.student.IStudentDao;
import com.example.SchoolSystem.school.student.Student;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.checkerframework.checker.units.qual.N;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private IStudentDao studentDao;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student;

    private List<Student> students;


    @BeforeEach
    void setUp(){
        student = Students.student();
        students = Students.students();
    }


    @Nested
    class add{

        @Test
        public void should_Return_GivenStudent(){
            //given
            when(studentDao.existsByPesel(anyString())).thenReturn(false);
            when(studentDao.save(any())).thenReturn(student);


            //when
            Student actual = studentService.add(student);

            //then
            assertEquals(student,actual);
        }

        @Test
        public void should_Throw_EntityExistsException(){
            //given
            when(studentDao.existsByPesel(anyString())).thenReturn(true);


            //when
            Executable executable = () -> studentService.add(student);

            //then
            assertThrows(EntityExistsException.class,executable);
        }
    }

    @Nested
    class addAll {

        @Test
        public void should_Return_GivenStudentsList_When_DatabaseDoesNotContainsGivenStudentsIdNumbers(){
            //given
            when(studentDao.existsByPesel(anyString())).thenReturn(false);
            when(studentDao.saveAll(any())).thenReturn(students);

            //when
            List<Student> actual = studentService.addAll(students);

            //then
            assertArrayEquals(students.toArray(),actual.toArray());
        }

        @Test
        public void should_Throw_EntityExistsException_When_StudentWithGivenIdNumberExists(){
            //given
            when(studentDao.existsByPesel(anyString())).thenReturn(true);

            //when
            Executable executable = () -> studentService.addAll(students);


            //then
            assertThrows(EntityExistsException.class,executable);

        }

        @Test
        public void should_Throw_EntityExistsException_When_GivenStudentsIdNumbersAreNotUnique(){
            //given
            students = Students.studentsWithSameIdNumbers();

            //when
            Executable executable = () -> studentService.addAll(students);


            //then
            assertThrows(IllegalArgumentException.class,executable);

        }
    }

    @Nested
    class delete{

        @Test
        public void should_Return_DeletedStudent_When_StudentExistsInDatabase(){
            //given
           when(studentDao.findById(anyLong())).thenReturn(Optional.of(student));


           //when
            Student actual = studentService.delete(1L);

            //then
            assertEquals(student,actual);
        }

        @Test
        public void should_Throw_EntityNotFoundException_WhenStudentDoesNotExistsInDatabase(){
            //given
            when(studentDao.findById(anyLong())).thenReturn(Optional.empty());

            //when
            Executable executable = () -> studentService.delete(1L);

            //then
            assertThrows(EntityNotFoundException.class,executable);
        }


        @Test
        public void should_DataIntegrityViolationException_When_StudentIsNotDeletedFromDatabase(){
            //given
            when(studentDao.findById(anyLong())).thenReturn(Optional.of(student));
            when(studentDao.existsById(anyLong())).thenReturn(true);

            //when
            Executable executable = () -> studentService.delete(1L);

            //then
            assertThrows(DataIntegrityViolationException.class,executable);
        }
    }

    @Nested
    class update{

        @Test
        public void should_Return_UpdatedStudent_When_StudentExistsInDatabase(){
            //given
            Student updatedStudent = Students.anotherStudent();
            when(studentDao.findById(anyLong())).thenReturn(Optional.of(student));

            //when
            Student actual = studentService.update(1L, updatedStudent);

            //then
            assertEquals(updatedStudent.getPersonInformation(), actual.getPersonInformation());
            assertEquals(updatedStudent.getAddress(), actual.getAddress());
        }

        @Test
        public void should_Throw_EntityDoesNotExists_When_StudentDoesNotExistsInDatabase(){
            //given
            when(studentDao.findById(1L)).thenReturn(Optional.empty());

            //when
            Executable executable = () -> studentService.update(1L,student);

            assertThrows(EntityNotFoundException.class,executable);
        }
    }

    @Nested
    class findById{

        @Test
        public void should_Return_StudentWithGivenId(){
            //given
            when(studentDao.findById(1L)).thenReturn(Optional.of(student));

            //when
            Student actual = studentService.findById(1L);

            //then
            assertEquals(student,actual);
        }

        @Test
        public void should_Throw_EntityNotFoundException_WhenStudentWithGivenIdDoesNotExists(){
            //given
            when(studentDao.findById(1L)).thenReturn(Optional.empty());

            //when
            Executable executable = () -> studentService.findById(1L);

            //then
            assertThrows(EntityNotFoundException.class,executable);
        }
    }

    @Nested
    class findAll{

        @Test
        public void should_Return_EmptyList(){
            //given
            when(studentDao.findAll()).thenReturn(Collections.emptyList());

            //when
            List<Student> actual = studentService.findAll();

            //then
            assertArrayEquals(Collections.emptyList().toArray(),actual.toArray());
        }

        @Test
        public void should_Return_StudentsList(){
            //given
            when(studentDao.findAll()).thenReturn(students);

            //when
            List<Student> actual = studentService.findAll();

            //then
            assertArrayEquals(students.toArray(),actual.toArray());
        }

    }


}