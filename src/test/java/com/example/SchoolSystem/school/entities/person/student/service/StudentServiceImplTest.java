package com.example.SchoolSystem.school.entities.person.student.service;

import com.example.SchoolSystem.school.database.student.IStudentDao;
import com.example.SchoolSystem.school.entities.person.PersonInformation;
import com.example.SchoolSystem.school.entities.person.student.Student;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    IStudentDao studentDao;

    @Mock
    Student student;

    @Mock
    PersonInformation personInformation;

    @Captor
    ArgumentCaptor<Student> studentArgumentCaptor;

    @InjectMocks
    StudentServiceImpl studentService;

    @Nested
    class add {

        @BeforeEach
        void setUp() {
            given(student.getPersonInformation()).willReturn(personInformation);
            given(personInformation.getPesel()).willReturn("cos");
        }

        @Test
        public void should_Throw_EntityExistsException_When_StudentIsAlreadyInDatabase() {
            //given
            given(studentDao.existsByPesel(anyString())).willReturn(true);

            //when
            Executable executable = () -> studentService.add(student);

            //then
            assertThrows(EntityExistsException.class, executable);
        }

        @Test
        public void should_Add_Student_toDatabase() {
            //given
            given(studentDao.existsByPesel(anyString())).willReturn(false);
            Student expected = student;

            //when
            studentService.add(student);
            verify(studentDao).save(student);
        }

        @Test
        public void should_Add_Student_toDatabase_When_Pesel_IsUnique() {
            //given
            given(studentDao.existsByPesel(anyString())).willReturn(false);
            Student expected = student;

            //when
            studentService.add(student);
            verify(studentDao).save(studentArgumentCaptor.capture());
            Student actual = studentArgumentCaptor.getValue();

            //then
            assertEquals(expected, actual);
        }
    }

    @Nested
    class addAll {

        @Test
        public void should_AddAll_Students_toDatabase() {
            //given
            List<Student> students = List.of(student);

            //when
            studentService.addAll(students);

            //then
            verify(studentDao).saveAll(students);
        }
    }

    @Nested
    class delete {

        @Test
        void should_Delete_StudentSuccessfully() {
            //given
            Long studentId = 1L;
            given(studentDao.existsById(studentId)).willReturn(true);

            //when
            studentService.delete(studentId);

            //then
            verify(studentDao).deleteById(studentId);
        }

        @Test
        void should_NotDelete_StudentWhenNotExists() {
            //given
            Long studentId = 1L;
            given(studentDao.existsById(studentId)).willReturn(false);

            //when
            studentService.delete(studentId);

            //then
            verify(studentDao, never()).deleteById(studentId);
        }

        @Test
        void should_NotThrowException_When_Deleting_Nonexistent_Student() {
            //given
            Long studentId = 1L;
            given(studentDao.existsById(studentId)).willReturn(false);

            //when
            Executable executable = () -> studentService.delete(studentId);

            //then
            assertDoesNotThrow(executable);
        }
    }

    @Nested
    class update {

//        @Test
//        void should_Update_StudentSuccessfully() {
//            //given
//            Student expected = student;
//
//            //when
//            studentService.update(student);
//
//            //then
//            verify(studentDao).save(student);
//        }
//
//        @Test
//        void should_Throw_EntityNotFoundException_When_Updating_Nonexistent_Student() {
//            //given
//            given(studentDao.existsById(anyLong())).willReturn(false);
//
//            //when
//            Executable executable = () -> studentService.update(student);
//
//            //then
//            assertThrows(EntityNotFoundException.class, executable);
//        }
    }

    @Nested
    class getById {

        @Test
        public void should_Throw_EntityNotFoundException_When_ThereIsNoStudent() {
            //given
            Long studentId = 1L;
            given(studentDao.findById(anyLong())).willReturn(Optional.empty());

            //when
            Executable executable = () -> studentService.findById(studentId);

            //then
            assertThrows(EntityNotFoundException.class, executable);
        }

        @Test
        public void should_Return_ProperStudent() {
            //given
            Long studentId = 1L;
            Student expected = new Student();
            given(studentDao.findById(anyLong())).willReturn(Optional.of(expected));

            //when
            Student actual = studentService.findById(studentId);

            ///then
            assertEquals(expected, actual);
        }
    }

    @Nested
    class getAll {

        @Test
        public void should_Return_StudentList() {
            //given
            List<Student> expected = List.of(new Student());
            given(studentDao.findAll()).willReturn(expected);

            //when
            List<Student> actual = studentService.findAll();

            //then
            assertEquals(expected, actual);
        }
    }


}
