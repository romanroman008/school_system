package com.example.SchoolSystem.school.entities.schoolSubject.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.SchoolSystem.school.database.subject.ISchoolSubjectDao;
import com.example.SchoolSystem.school.entities.schoolClass.Grade;
import com.example.SchoolSystem.school.entities.schoolSubject.SchoolSubject;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class SchoolSubjectServiceImplTest {

    @Mock
    ISchoolSubjectDao schoolSubjectDao;

    @InjectMocks
    SchoolSubjectServiceImpl schoolSubjectService;

    @Test
    void add_Should_AddSchoolSubject_When_NotExists() {
        //given
        SchoolSubject schoolSubject = new SchoolSubject("Math", Map.of(Grade.I, 3, Grade.II, 4));
        given(schoolSubjectDao.existsByName(anyString())).willReturn(false);

        //when
        schoolSubjectService.add(schoolSubject);

        //then
        verify(schoolSubjectDao).existsByName("Math");
        verify(schoolSubjectDao).save(schoolSubject);
    }

    @Test
    void add_Should_Throw_EntityExistsException_When_SchoolSubjectExists() {
        //given
        SchoolSubject schoolSubject = new SchoolSubject("Math", Map.of(Grade.I, 3, Grade.II, 4));
        given(schoolSubjectDao.existsByName(anyString())).willReturn(true);

        //when
        Executable executable = () -> schoolSubjectService.add(schoolSubject);

        //then
        assertThrows(EntityExistsException.class, executable);
        verify(schoolSubjectDao, never()).save(any());
    }

    @Test
    void delete_Should_DeleteSchoolSubject_When_Exists() {
        //given
        Long subjectId = 1L;
        SchoolSubject expected = new SchoolSubject("Math", Map.of(Grade.I, 3, Grade.II, 4));
        given(schoolSubjectDao.findById(subjectId)).willReturn(Optional.of(expected));

        //when
        SchoolSubject result = schoolSubjectService.delete(subjectId);

        //then
        verify(schoolSubjectDao).deleteById(subjectId);
        assertEquals(expected, result);
    }

    @Test
    void delete_Should_Throw_EntityNotFoundException_When_SchoolSubjectNotExists() {
        //given
        Long nonExistentId = 99L;
        given(schoolSubjectDao.findById(nonExistentId)).willReturn(Optional.empty());

        //when
        Executable executable = () -> schoolSubjectService.delete(nonExistentId);

        //then
        assertThrows(EntityNotFoundException.class, executable);
        verify(schoolSubjectDao, never()).deleteById(any());
    }

    @Test
    void delete_Should_Throw_DataIntegrityViolationException_When_FailedToDelete_SchoolSubject() {
        //given
        Long subjectId = 1L;
        SchoolSubject expected = new SchoolSubject("Math", Map.of(Grade.I, 3, Grade.II, 4));
        given(schoolSubjectDao.findById(subjectId)).willReturn(Optional.of(expected));
        given(schoolSubjectDao.existsById(subjectId)).willReturn(true);

        //when
        Executable executable = () -> schoolSubjectService.delete(subjectId);

        //then
        assertThrows(DataIntegrityViolationException.class, executable);
        verify(schoolSubjectDao).deleteById(subjectId);
    }

    @Test
    void update_Should_UpdateSchoolSubject() {
        //given
//        SchoolSubject schoolSubject = new SchoolSubject("Math", Map.of(Grade.I, 3, Grade.II, 4));
//
//        //when
//        schoolSubjectService.update(schoolSubject);
//
//        //then
//        verify(schoolSubjectDao).save(schoolSubject);
    }

    @Test
    void getById_Should_ReturnSchoolSubjectById_When_Exists() {
        //given
        Long subjectId = 1L;
        SchoolSubject expected = new SchoolSubject("Math", Map.of(Grade.I, 3, Grade.II, 4));
        given(schoolSubjectDao.findById(subjectId)).willReturn(Optional.of(expected));

        //when
        SchoolSubject result = schoolSubjectService.findById(subjectId);

        //then
        assertEquals(expected, result);
    }

    @Test
    void getById_Should_Throw_EntityNotFoundException_When_SchoolSubjectNotExists() {
        //given
        Long nonExistentId = 99L;
        given(schoolSubjectDao.findById(nonExistentId)).willReturn(Optional.empty());

        //when
        Executable executable = () -> schoolSubjectService.findById(nonExistentId);

        //then
        assertThrows(EntityNotFoundException.class, executable);
    }

    @Test
    void getAll_Should_ReturnAllSchoolSubjects() {
        //given
        List<SchoolSubject> expected = List.of(
                new SchoolSubject("Math", Map.of(Grade.I, 3, Grade.II, 4)),
                new SchoolSubject("Science", Map.of(Grade.I, 2, Grade.II, 3))
        );
        given(schoolSubjectDao.findAll()).willReturn(expected);

        //when
        List<SchoolSubject> result = schoolSubjectService.findAll();

        //then
        assertEquals(expected, result);
    }
}
