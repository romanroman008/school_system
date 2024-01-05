package com.example.SchoolSystem.school.timetable.time.lesson;

import com.example.SchoolSystem.school.timetable.timetablePlainObjects.lesson.LessonTimetable;
import com.example.SchoolSystem.school.entities.schoolClass.Grade;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.time.DayName;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.schoolClass.ClassTimetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.subject.SubjectTimetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.teacher.TeacherTimetable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class LessonTimetableTest {

    private ClassTimetable classTimetable;
    private SubjectTimetable subjectTimetable;
    private TeacherTimetable teacherTimetable;

    @BeforeEach
    void setUp() {
        classTimetable = mock(ClassTimetable.class);
        subjectTimetable = mock(SubjectTimetable.class);
        teacherTimetable = mock(TeacherTimetable.class);
    }

    @Nested
    class ConstructorTests {

        @Test
        void shouldCreateLessonWithGeneratedId() {
            // Given
            DayName day = DayName.MONDAY;

            // When
            LessonTimetable lessonTimetable = new LessonTimetable(1, day, classTimetable, subjectTimetable, teacherTimetable);

            // Then
            assertNotNull(lessonTimetable.getId());
            assertEquals(day, lessonTimetable.getDay());
            assertEquals(1, lessonTimetable.getNumber());
            assertEquals(classTimetable, lessonTimetable.getSchoolClass());
            assertEquals(subjectTimetable, lessonTimetable.getSubject());
            assertEquals(teacherTimetable, lessonTimetable.getTeacher());
        }

    }

    @Nested
    class ChangeLessonNumberTestsTimetable {

        @Test
        void shouldChangeLessonNumberAndDay() {
            // Given
            LessonTimetable lessonTimetable = new LessonTimetable(1, DayName.MONDAY, classTimetable, subjectTimetable, teacherTimetable);
            int newNumber = 2;
            DayName newDay = DayName.TUESDAY;

            // When
            lessonTimetable.changeLessonNumber(newNumber, newDay);

            // Then
            assertEquals(newNumber, lessonTimetable.getNumber());
            assertEquals(newDay, lessonTimetable.getDay());
        }


    }


    @Nested
    class LessonTimetableComparatorTests {

        @Test
        void shouldCompareLessons() {
            // Given
            LessonTimetable lessonTimetable1 = new LessonTimetable(1, DayName.MONDAY, classTimetable, subjectTimetable, teacherTimetable);
            LessonTimetable lessonTimetable2 = new LessonTimetable(2, DayName.MONDAY, classTimetable, subjectTimetable, teacherTimetable);

            // When
            int result = new LessonTimetable.LessonComparator().compare(lessonTimetable1, lessonTimetable2);

            // Then
            assertTrue(result < 0);
        }

        @Test
        void shouldCompareEqualLessons() {
            // Given
            LessonTimetable lessonTimetable1 = new LessonTimetable(1, DayName.MONDAY, classTimetable, subjectTimetable, teacherTimetable);
            LessonTimetable lessonTimetable2 = new LessonTimetable(1, DayName.MONDAY, classTimetable, subjectTimetable, teacherTimetable);

            // When
            int result = new LessonTimetable.LessonComparator().compare(lessonTimetable1, lessonTimetable2);

            // Then
            assertEquals(0, result);
        }

        @Test
        void shouldCompareByClass() {
            // Given
            ClassTimetable classTimetable1 = mock(ClassTimetable.class);
            ClassTimetable classTimetable2 = mock(ClassTimetable.class);
            LessonTimetable lessonTimetable1 = new LessonTimetable(1, DayName.MONDAY, classTimetable1, subjectTimetable, teacherTimetable);
            LessonTimetable lessonTimetable2 = new LessonTimetable(1, DayName.MONDAY, classTimetable2, subjectTimetable, teacherTimetable);
            given(classTimetable1.getGrade()).willReturn(Grade.I);
            given(classTimetable1.getGrade()).willReturn(Grade.II);

            // When
            int result = new LessonTimetable.LessonComparator().compare(lessonTimetable1, lessonTimetable2);

            // Then
            assertTrue(result < 0);
        }

        @Test
        void shouldCompareByDay() {
            // Given
            LessonTimetable lessonTimetable1 = new LessonTimetable(1, DayName.MONDAY, classTimetable, subjectTimetable, teacherTimetable);
            LessonTimetable lessonTimetable2 = new LessonTimetable(1, DayName.TUESDAY, classTimetable, subjectTimetable, teacherTimetable);

            // When
            int result = new LessonTimetable.LessonComparator().compare(lessonTimetable1, lessonTimetable2);

            // Then
            assertTrue(result < 0);
        }

        @Test
        void shouldCompareByNumber() {
            // Given
            LessonTimetable lessonTimetable1 = new LessonTimetable(1, DayName.MONDAY, classTimetable, subjectTimetable, teacherTimetable);
            LessonTimetable lessonTimetable2 = new LessonTimetable(2, DayName.MONDAY, classTimetable, subjectTimetable, teacherTimetable);

            // When
            int result = new LessonTimetable.LessonComparator().compare(lessonTimetable1, lessonTimetable2);

            // Then
            assertTrue(result < 0);
        }


    }
}
