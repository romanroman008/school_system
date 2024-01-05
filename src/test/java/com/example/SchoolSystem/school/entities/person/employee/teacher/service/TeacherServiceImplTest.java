package com.example.SchoolSystem.school.entities.person.employee.teacher.service;


import com.example.SchoolSystem.school.database.teacher.ITeacherDao;
import com.example.SchoolSystem.school.entities.person.PersonInformation;

import com.example.SchoolSystem.school.entities.person.teacher.Teacher;

import com.example.SchoolSystem.school.entities.person.teacher.service.TeacherServiceImpl;
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

import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TeacherServiceImplTest {

    @Mock
    ITeacherDao teacherDao;

    @Mock
    Teacher teacher;
    @Mock
    PersonInformation personInformation;

    @Captor
    ArgumentCaptor<Teacher> teacherArgumentCaptor;

    @InjectMocks
    TeacherServiceImpl teacherService;


    @Nested
    class add {

        @BeforeEach
        void setUp() {
            given(teacher.getPersonInformation()).willReturn(personInformation);
            given(personInformation.getPesel()).willReturn("cos");
        }

        @Test
        public void should_Throw_EntityExistsException_When_TeacherIsAlreadyInDatabase() {
            //given
            given(teacherDao.existsByPesel(anyString())).willReturn(true);


            //when
            Executable executable = () -> teacherService.add(teacher);

            //then
            assertThrows(EntityExistsException.class, executable);
        }

        @Test
        public void should_Add_Teacher_toDatabase() {
            //given
            given(teacherDao.existsByPesel(anyString())).willReturn(false);
            Teacher expected = teacher;

            //when
            teacherService.add(teacher);
            verify(teacherDao).save(teacherArgumentCaptor.capture());
            Teacher actual = teacherArgumentCaptor.getValue();

            //then
            assertEquals(expected, actual);


        }

    }

    @Nested
    class delete {

        @Test
        void should_Delete_TeacherSuccessfully() {
            //given
            Long teacherId = 1L;
            Teacher expected = new Teacher();
            given(teacherDao.findById(teacherId)).willReturn(Optional.of(expected));

            //when
            Teacher actual = teacherService.delete(teacherId);

            //then
            verify(teacherDao, times(1)).deleteById(teacherId);
            assertSame(expected, actual);
        }

        @Test
        void should_ThrowEntityNotFoundException_When_TeacherIsNotFound() {
            //given
            Long teacherId = 1L;
            given(teacherDao.findById(teacherId)).willReturn(Optional.empty());

            //when
            Executable executable = () -> teacherService.delete(teacherId);

            //then
            assertThrows(EntityNotFoundException.class, executable);
        }

        @Test
        void should_Throw_DataIntegrityViolationException_When_TeacherIsNotDeleted() {
            //given
            Long teacherId = 1L;
            Teacher expected = new Teacher();
            given(teacherDao.findById(any())).willReturn(Optional.of(expected));
            given(teacherDao.existsById(any())).willReturn(true);

            //when
            Executable executable = () -> teacherService.delete(teacherId);

            //then
            assertThrows(DataIntegrityViolationException.class, executable);

        }
    }

    @Nested
    class getById {

        @Test
        public void should_Throw_EntityNotFoundException_When_ThereIsNoTeacher() {
            //given
            Long teacherId = 1L;
            given(teacherDao.findById(anyLong())).willReturn(Optional.empty());

            //when
            Executable executable = () -> teacherService.findById(teacherId);

            //then
            assertThrows(EntityNotFoundException.class, executable);
        }


        @Test
        public void should_Return_ProperTeacher() {
            //given
            Long teacherId = 1L;
            Teacher expected = new Teacher();
            given(teacherDao.findById(anyLong())).willReturn(Optional.of(expected));

            //when
            Teacher actual = teacherService.findById(1L);

            ///then
            assertEquals(expected, actual);

        }
    }

    @Nested
    class getAll {

        @Test
        public void should_Return_EmptyList() {
            //given
            int expected = 0;

            //when
            List<Teacher> actual = teacherService.findAll();

            //then
            assertEquals(expected, actual.size());
        }

        @Test
        public void should_Return_3_Teachers() {
            //given
            Teacher employee1 = new Teacher();
            Teacher employee2 = new Teacher();
            Teacher employee3 = new Teacher();
            List<Teacher> expected = new ArrayList<>(List.of(employee1, employee2, employee3));
            given(teacherDao.findAll()).willReturn(expected);

            //when
            List<Teacher> actual = teacherService.findAll();

            //then
            assertEquals(expected.size(), actual.size());
            assertTrue(expected.containsAll(actual));
        }

    }






}