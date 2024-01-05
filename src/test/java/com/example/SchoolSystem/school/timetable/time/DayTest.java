package com.example.SchoolSystem.school.timetable.time;


import com.example.SchoolSystem.school.entities.schoolClass.Grade;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.lesson.LessonTimetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.schoolClass.ClassTimetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.teacher.TeacherTimetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.time.Day;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.time.DayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.InvalidParameterException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class DayTest {

    Day day;


    @Mock
    Day dayMock;

    @Mock
    TeacherTimetable fyrstTyczer;
    @Mock
    TeacherTimetable sekontTyczer;
    @Mock
    TeacherTimetable tirtTyczer;
    @Mock
    TeacherTimetable leastTyczer;

    @Mock
    ClassTimetable ersteKlase;
    @Mock
    ClassTimetable zweiteKlase;
    @Mock
    ClassTimetable dreiteKlase;
    @Mock
    ClassTimetable zuzeltKlase;

    Set<ClassTimetable> classesMocked;
    Set<TeacherTimetable> teachersMocked;

    Set<ClassTimetable> classes;
    Set<TeacherTimetable> teachers;

    TeacherTimetable teacher;
    ClassTimetable schoolClass;

    int MAX_HOURS_PER_DAY = 10;







    @Nested
    class getFirstLessonNumberTimetable {

    }

    @Nested
    class getLastLessonTimetable {


        @Test
        public void should_Return_EmptyList() {
            //given
            List<LessonTimetable> expected = Collections.emptyList();


            //when
            List<LessonTimetable> actual = day.getLastLesson(schoolClass);


            //then
            assertEquals(expected, actual);
        }

        @Test
        public void should_Return_ProperLastLesson() {
            //given
            Day mockedDay = Mockito.mock(Day.class);
            LessonTimetable lessonTimetableMock = mock(LessonTimetable.class);
            LessonTimetable lessonTimetableMock2 = mock(LessonTimetable.class);
            LessonTimetable lessonTimetableMock3 = mock(LessonTimetable.class);
            given(lessonTimetableMock.getNumber()).willReturn(9);
            given(lessonTimetableMock.getSchoolClass()).willReturn(schoolClass);
            given(lessonTimetableMock2.getSchoolClass()).willReturn(schoolClass);
            given(lessonTimetableMock3.getSchoolClass()).willReturn(schoolClass);
            List<LessonTimetable> expected = Collections.singletonList(lessonTimetableMock);
            day.addLessonToGivenHour(lessonTimetableMock, 9);
            day.addLessonToGivenHour(lessonTimetableMock2, 3);
            day.addLessonToGivenHour(lessonTimetableMock3, 5);

            //when
            List<LessonTimetable> actual = day.getLastLesson(schoolClass);


            //then
            assertEquals(expected.size(), actual.size());
            assertTrue(expected.containsAll(actual));


        }
    }

    @Nested
    class getLessonAmountOfGivenSchoolClassTimetable {


        @Test
        public void should_Return_0() {
            //given
            ClassTimetable schoolClassMock = mock(ClassTimetable.class);
            int expected = 0;

            //when
            int actual = day.getLessonAmountOfGivenSchoolClass(schoolClassMock);

            //then
            assertEquals(expected, actual);

        }

        @ParameterizedTest
        @ValueSource(ints = {1, 2, 3, 6, 10, 23, 100})
        public void shouldReturn_ProperAmountOfLessons(int lessonsAmount) {
            //given
            LessonTimetable lessonTimetableMock = mock(LessonTimetable.class);
            given(lessonTimetableMock.getSchoolClass()).willReturn(schoolClass);
            int expected = lessonsAmount;
            for (int i = 0; i < lessonsAmount; i++) {
                day.addLessonToGivenHour(lessonTimetableMock, 1);
            }

            //when
            int actual = day.getLessonAmountOfGivenSchoolClass(schoolClass);

            //then
            assertEquals(expected, actual);

        }

    }

    @Nested
    class checkIfLessonCanBeAddedTimetable {

        @Test
        public void should_ReturnTrue_When_AvailableHourNumberIsReturned() {
            //given
            boolean expected = true;
            LessonTimetable lessonTimetable = mock(LessonTimetable.class);
            given(dayMock.getAvailableHourNumber(any())).willReturn(4);   // nie wiem
            given(dayMock.checkIfLessonCanBeAdded(any())).willCallRealMethod();

            //when
            boolean actual = dayMock.checkIfLessonCanBeAdded(lessonTimetable);

            //then
            assertEquals(expected, actual);
        }

        @Test
        public void should_ReturnFalse_When_ThereIsNoAvailableHourNumber() {
            //given
            boolean expected = false;
            LessonTimetable lessonTimetable = mock(LessonTimetable.class);
            given(dayMock.getAvailableHourNumber(any())).willThrow(NoSuchElementException.class);
            given(dayMock.checkIfLessonCanBeAdded(any())).willCallRealMethod();

            //when
            boolean actual = dayMock.checkIfLessonCanBeAdded(lessonTimetable);

            //then
            assertEquals(expected, actual);
        }

    }

    @Nested
    class getAvailableHourNumber {



        @Test
        public void should_ReturnZero_When_ThereIsNoLessons() {
            //given
            LessonTimetable lessonTimetable = mock(LessonTimetable.class);
            given(lessonTimetable.getTeacher()).willReturn(teacher);
            int expected = 0;

            //when
            int actual = day.getAvailableHourNumber(lessonTimetable);

            //then
            assertEquals(expected, actual);

        }

        @Test
        public void should_smród() {
            //given
            LessonTimetable lessonTimetable = mock(LessonTimetable.class);

            given(lessonTimetable.getTeacher()).willReturn(fyrstTyczer);
            given(lessonTimetable.getSchoolClass()).willReturn(ersteKlase);

        }

        @Test
        public void should_Return_1_When_FirstLessonIsAtSecondHour() {
            //given
            LessonTimetable lessonTimetable = mock(LessonTimetable.class);
            given(lessonTimetable.getTeacher()).willReturn(teacher);
            given(lessonTimetable.getSchoolClass()).willReturn(schoolClass);
            int expected = 1;
            day.addLessonToGivenHour(lessonTimetable, 2);         //todo tego nie powinno tu byc ale idk


            //when
            int actual = day.getAvailableHourNumber(lessonTimetable);


            //then
            assertEquals(expected, actual);

        }

        @Test
        public void should_Thrown_NoSuchElementException() {
            //given
            LessonTimetable lessonTimetable = mock(LessonTimetable.class);

            //when
            Executable executable = () -> day.getAvailableHourNumber(lessonTimetable);

            //then
            assertThrows(NoSuchElementException.class, executable);

        }
    }


    @Nested
    class addLessonToGivenHourTimetable {



        @ParameterizedTest
        @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
        public void should_AddLesson_ToGivenHour(int expected) {
            //given

            LessonTimetable lessonTimetable = mock(LessonTimetable.class);


            //when
            day.addLessonToGivenHour(lessonTimetable, expected);
            int actual = day.getHours().get(expected).getNumber();


            //then
            assertEquals(expected, actual);

        }

        @ParameterizedTest
        @ValueSource(ints = {-1, 200, 43, 10, -23, 1820, 31, 901})
        public void should_ThrowInvalidParameterException(int hourNumber) {
            //given
            LessonTimetable lessonTimetable = mock(LessonTimetable.class);


            //when
            Executable executable = () -> day.addLessonToGivenHour(lessonTimetable, hourNumber);


            //then
            assertThrows(InvalidParameterException.class, executable);

        }


    }


}