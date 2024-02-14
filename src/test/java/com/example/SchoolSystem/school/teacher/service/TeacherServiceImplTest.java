package com.example.SchoolSystem.school.teacher.service;

import com.example.SchoolSystem.school.Students;
import com.example.SchoolSystem.school.Teachers;
import com.example.SchoolSystem.school.student.Student;
import com.example.SchoolSystem.school.teacher.ITeacherDao;
import com.example.SchoolSystem.school.teacher.Teacher;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeacherServiceImplTest {


    @Mock
    private ITeacherDao teacherDao;

    @InjectMocks
    private TeacherServiceImpl teacherService;


    private Teacher teacher;

    private List<Teacher> teachers;

    @BeforeEach
    void setUp() {
        teacher = Teachers.teacher();
        teachers = Teachers.teachers();
    }


    @Nested
    class add{

        @Test
        public void should_Return_GivenTeacher(){
            //given
            when(teacherDao.existsByIDName(anyString())).thenReturn(false);
            when(teacherDao.save(any())).thenReturn(teacher);


            //when
            Teacher actual = teacherService.add(teacher);

            //then
            assertEquals(teacher,actual);
        }

        @Test
        public void should_Throw_EntityExistsException(){
            //given
            when(teacherDao.existsByIDName(anyString())).thenReturn(true);


            //when
            Executable executable = () -> teacherService.add(teacher);

            //then
            assertThrows(EntityExistsException.class,executable);
        }
    }

    @Nested
    class addAll {

        @Test
        public void should_Return_GivenTeachersList_When_DatabaseDoesNotContainsGivenTeachersIdNumbers(){
            //given
            when(teacherDao.existsByIDName(anyString())).thenReturn(false);
            when(teacherDao.saveAll(any())).thenReturn(teachers);

            //when
            List<Teacher> actual = teacherService.addAll(teachers);

            //then
            assertArrayEquals(teachers.toArray(),actual.toArray());
        }

        @Test
        public void should_Throw_EntityExistsException_When_TeacherWithGivenIdNumberExists(){
            //given
            when(teacherDao.existsByIDName(anyString())).thenReturn(true);

            //when
            Executable executable = () -> teacherService.addAll(teachers);


            //then
            assertThrows(EntityExistsException.class,executable);

        }

        @Test
        public void should_Throw_EntityExistsException_When_GivenTeachersIdNumbersAreNotUnique(){
            //given
            teachers = Teachers.teachersWithSameIdNumbers();

            //when
            Executable executable = () -> teacherService.addAll(teachers);


            //then
            assertThrows(IllegalArgumentException.class,executable);

        }
    }


    @Nested
    class delete{

        @Test
        public void should_Return_DeletedStudent_When_TeacherExistsInDatabase(){
            //given
            when(teacherDao.findById(anyLong())).thenReturn(Optional.of(teacher));


            //when
            Teacher actual = teacherService.delete(1L);

            //then
            assertEquals(teacher,actual);
        }

        @Test
        public void should_Throw_EntityNotFoundException_WhenTeacherDoesNotExistsInDatabase(){
            //given
            when(teacherDao.findById(anyLong())).thenReturn(Optional.empty());

            //when
            Executable executable = () -> teacherService.delete(1L);

            //then
            assertThrows(EntityNotFoundException.class,executable);
        }


        @Test
        public void should_DataIntegrityViolationException_When_TeacherIsNotDeletedFromDatabase(){
            //given
            when(teacherDao.findById(anyLong())).thenReturn(Optional.of(teacher));
            when(teacherDao.existsById(anyLong())).thenReturn(true);

            //when
            Executable executable = () -> teacherService.delete(1L);

            //then
            assertThrows(DataIntegrityViolationException.class,executable);
        }
    }

    @Nested
    class update{

        @Test
        public void should_Return_UpdatedTeacher_When_TeacherExistsInDatabase(){
            //given
            Teacher updatedTeacher = Teachers.anotherTeacher();
            when(teacherDao.findById(anyLong())).thenReturn(Optional.of(teacher));

            //when
            Teacher actual = teacherService.update(1L, updatedTeacher);

            //then
            assertEquals(updatedTeacher.getPersonInformation(), actual.getPersonInformation());
            assertEquals(updatedTeacher.getAddress(), actual.getAddress());
        }

        @Test
        public void should_Throw_EntityDoesNotExists_When_TeacherDoesNotExistsInDatabase(){
            //given
            when(teacherDao.findById(1L)).thenReturn(Optional.empty());

            //when
            Executable executable = () -> teacherService.update(1L,teacher);

            assertThrows(EntityNotFoundException.class,executable);
        }
    }

    @Nested
    class findById{

        @Test
        public void should_Return_TeacherWithGivenId(){
            //given
            when(teacherDao.findById(1L)).thenReturn(Optional.of(teacher));

            //when
            Teacher actual = teacherService.findById(1L);

            //then
            assertEquals(teacher,actual);
        }

        @Test
        public void should_Throw_EntityNotFoundException_WhenTeacherWithGivenIdDoesNotExists(){
            //given
            when(teacherDao.findById(1L)).thenReturn(Optional.empty());

            //when
            Executable executable = () -> teacherService.findById(1L);

            //then
            assertThrows(EntityNotFoundException.class,executable);
        }
    }

    @Nested
    class findAll{

        @Test
        public void should_Return_EmptyList(){
            //given
            when(teacherDao.findAll()).thenReturn(Collections.emptyList());

            //when
            List<Teacher> actual = teacherService.findAll();

            //then
            assertArrayEquals(Collections.emptyList().toArray(),actual.toArray());
        }

        @Test
        public void should_Return_TeachersList(){
            //given
            when(teacherDao.findAll()).thenReturn(teachers);

            //when
            List<Teacher> actual = teacherService.findAll();

            //then
            assertArrayEquals(teachers.toArray(),actual.toArray());
        }

    }

}