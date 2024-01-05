package com.example.SchoolSystem.school.entities.schoolClass.service;

import com.example.SchoolSystem.school.database.schoolClasses.ISchoolClassDao;
import com.example.SchoolSystem.school.entities.schoolClass.SchoolClass;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SchoolClassServiceImplTest {

    @Mock
    ISchoolClassDao schoolClassDao;

    @InjectMocks
    SchoolClassServiceImpl schoolClassService;

    @Nested
    class add {

        @Test
        void should_Add_SchoolClass() {
            //given
            SchoolClass schoolClass = new SchoolClass();

            //when
            schoolClassService.add(schoolClass);

            //then
            verify(schoolClassDao).save(schoolClass);
        }
    }

    @Nested
    class addAll {

        @Test
        void should_AddAll_SchoolClasses() {
            //given
            List<SchoolClass> classes = List.of(
                    new SchoolClass(),
                    new SchoolClass(),
                    new SchoolClass()
            );

            //when
            schoolClassService.addAll(classes);

            //then
            verify(schoolClassDao, times(3)).save(any());
        }
    }

    @Nested
    class remove {

        @Test
        void should_Remove_SchoolClass() {
            //given
            Long classId = 1L;

            //when
            schoolClassService.remove(classId);

            //then

        }
    }

    @Nested
    class update {


    }

    @Nested
    class updateAll {


    }

    @Nested
    class getById {

        @Test
        void should_Return_SchoolClassById() {
            //given
            Long classId = 1L;
            SchoolClass expected = new SchoolClass();
            given(schoolClassDao.findById(classId)).willReturn(Optional.of(expected));

            //when
            SchoolClass result = schoolClassService.findById(classId);

            //then
            assertEquals(expected, result);
        }

        @Test
        void should_Throw_EntityNotFoundException_When_SchoolClassNotExists() {
            //given
            Long nonExistentId = 99L;
            given(schoolClassDao.findById(nonExistentId)).willReturn(Optional.empty());

            //when
            Executable executable = () -> schoolClassService.findById(nonExistentId);

            //then
            assertThrows(EntityNotFoundException.class, executable);
        }
    }

    @Nested
    class getAllClasses {

        @Test
        void should_Return_AllClasses() {
            //given
            List<SchoolClass> expected = List.of(
                    new SchoolClass(),
                    new SchoolClass(),
                    new SchoolClass()
            );
            given(schoolClassDao.findAll()).willReturn(expected);

            //when


            //then

        }
    }

    @Nested
    class updateSchoolClasses {


    }
}