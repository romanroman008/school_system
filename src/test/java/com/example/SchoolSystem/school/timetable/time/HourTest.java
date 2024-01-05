package com.example.SchoolSystem.school.timetable.time;


import com.example.SchoolSystem.school.timetable.timetablePlainObjects.lesson.LessonTimetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.schoolClass.ClassTimetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.teacher.TeacherTimetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.time.DayName;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.time.Hour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class HourTest {

    private Set<TeacherTimetable> availableTeachers;
    private Set<ClassTimetable> availableClasses;

    @BeforeEach
    void setUp() {
        // You can create mock instances for TeacherTimetable and ClassTimetable here
        availableTeachers = new HashSet<>();
        availableClasses = new HashSet<>();
    }

    @Nested
    class ConstructorTests {

        @Test
        void shouldCreateHourWithAvailableTeachersAndClasses() {
            // Given
            DayName day = DayName.MONDAY;
            int number = 1;

            // When
            Hour hour = new Hour(number, day, availableTeachers, availableClasses);

            // Then
            assertEquals(number, hour.getNumber());
            assertEquals(day, hour.getDay());
            assertEquals(availableTeachers, hour.getAvailableTeachers());
            assertEquals(availableClasses, hour.getAvailableClasses());
            assertTrue(hour.getLessons().isEmpty());
        }

        // Add more tests for constructor if needed
    }

    @Nested
    class AddLessonTestsTimetable {

        @Test
        void shouldAddLessonAndUpdateAvailableTeachersAndClasses() {
            // Given
            Hour hour = new Hour(1, DayName.MONDAY, availableTeachers, availableClasses);
            LessonTimetable lessonTimetable = mock(LessonTimetable.class);

            // When
            hour.addLesson(lessonTimetable);

            // Then
            assertFalse(hour.getAvailableTeachers().contains(lessonTimetable.getTeacher()));
            assertFalse(hour.getAvailableClasses().contains(lessonTimetable.getSchoolClass()));
            assertTrue(hour.getLessons().contains(lessonTimetable));
        }

        // Add more tests for addLesson if needed
    }

    @Nested
    class RemoveLessonTestsTimetable {

        @Test
        void shouldRemoveLessonAndUpdateAvailableTeachersAndClasses() {
            // Given
            Hour hour = new Hour(1, DayName.MONDAY, availableTeachers, availableClasses);
            LessonTimetable lessonTimetable = mock(LessonTimetable.class);
            hour.addLesson(lessonTimetable);

            // When
            hour.removeLesson(lessonTimetable);

            // Then
            assertTrue(hour.getAvailableTeachers().contains(lessonTimetable.getTeacher()));
            assertTrue(hour.getAvailableClasses().contains(lessonTimetable.getSchoolClass()));
            assertFalse(hour.getLessons().contains(lessonTimetable));
        }

        // Add more tests for removeLesson if needed
    }

    @Nested
    class CheckIfDoesNotContainsConcreteSchoolClassTests {

        @Test
        void shouldReturnTrueWhenDoesNotContainSchoolClass() {
            // Given
            Hour hour = new Hour(1, DayName.MONDAY, availableTeachers, availableClasses);
            ClassTimetable schoolClass = mock(ClassTimetable.class);

            // Then
            assertTrue(hour.checkIfDoesNotContainsConcreteSchoolClass(schoolClass));
        }

        @Test
        void shouldReturnFalseWhenContainsSchoolClass() {
            // Given
            Hour hour = new Hour(1, DayName.MONDAY, availableTeachers, availableClasses);
            ClassTimetable schoolClass = mock(ClassTimetable.class);
            LessonTimetable lessonTimetable = mock(LessonTimetable.class);
            given(lessonTimetable.getSchoolClass()).willReturn(schoolClass);
            hour.addLesson(lessonTimetable);

            // Then
            assertFalse(hour.checkIfDoesNotContainsConcreteSchoolClass(schoolClass));
        }

        // Add more tests for checkIfDoesNotContainsConcreteSchoolClass if needed
    }

    @Nested
    class CheckIfTeacherIsAvailableTests {

        @Test
        void shouldReturnTrueWhenTeacherIsAvailable() {
            // Given
            TeacherTimetable teacher = mock(TeacherTimetable.class);
            availableTeachers.add(teacher);
            Hour hour = new Hour(1, DayName.MONDAY, availableTeachers, availableClasses);


            // Then
            assertTrue(hour.checkIfTeacherIsAvailable(teacher));
        }

        @Test
        void shouldReturnFalseWhenTeacherIsNotAvailable() {
            // Given
            Hour hour = new Hour(1, DayName.MONDAY, availableTeachers, availableClasses);
            TeacherTimetable teacher = mock(TeacherTimetable.class);
            LessonTimetable lessonTimetable = mock(LessonTimetable.class);
            given(lessonTimetable.getTeacher()).willReturn(teacher);
            hour.addLesson(lessonTimetable);

            // Then
            assertFalse(hour.checkIfTeacherIsAvailable(teacher));
        }

        // Add more tests for checkIfTeacherIsAvailable if needed
    }

    @Nested
    class GetLessonTestsTimetable {

        @Test
        void shouldReturnListOfLessonsForSchoolClass() {
            // Given
            Hour hour = new Hour(1, DayName.MONDAY, availableTeachers, availableClasses);
            ClassTimetable schoolClass = mock(ClassTimetable.class);
            LessonTimetable lessonTimetable1 = mock(LessonTimetable.class);
            LessonTimetable lessonTimetable2 = mock(LessonTimetable.class);
            given(lessonTimetable1.getSchoolClass()).willReturn(schoolClass);
            given(lessonTimetable2.getSchoolClass()).willReturn(schoolClass);
            hour.addLesson(lessonTimetable1);
            hour.addLesson(lessonTimetable2);

            // When
            List<LessonTimetable> result = hour.getLesson(schoolClass);

            // Then
            assertEquals(2, result.size());
            assertTrue(result.contains(lessonTimetable1));
            assertTrue(result.contains(lessonTimetable2));
        }

        // Add more tests for getLesson if needed
    }
}
