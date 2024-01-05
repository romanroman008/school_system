package com.example.SchoolSystem.school.timetable.timetablePlaneObjects.schoolClass;

import com.example.SchoolSystem.school.entities.schoolClass.Grade;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.schoolClass.ClassTimetable;

import com.example.SchoolSystem.school.timetable.timetablePlainObjects.subject.SubjectTimetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.teacher.TeacherTimetable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


@ExtendWith(MockitoExtension.class)
class ClassTimetableTest {

    ClassTimetable classTimetable;

    @Mock
    SubjectTimetable history;
    @Mock
    SubjectTimetable PE;
    @Mock
    SubjectTimetable math;
    @Mock
    SubjectTimetable english;

    List<SubjectTimetable> subjects;



    @Nested
    class getTeachingSubjectOfGivenTeacher{
        @BeforeEach
        void setUp(){
            //given
            subjects = new ArrayList<>(Arrays.asList(history,PE,math,english));
            classTimetable = new ClassTimetable("XD", Grade.I,subjects);
        }
        @Test
        public void should_Throw_NoSuchElementException(){

            //given
            TeacherTimetable teacher = mock(TeacherTimetable.class);

            //when
            Executable executable = () -> classTimetable.getTeachingSubjectOfGivenTeacher(teacher);

            //then
            assertThrows(NoSuchElementException.class, executable);
        }

        @Test
        public void should_Return_Math(){
            //given
            TeacherTimetable teacher = mock(TeacherTimetable.class);
            SubjectTimetable expected = math;
            classTimetable.assignTeacherToSubject(expected,teacher);

            //when
            SubjectTimetable actual = classTimetable.getTeachingSubjectOfGivenTeacher(teacher);


            //then
            assertEquals(expected,actual);

        }
    }







    @Nested
    class getAllSubjects{


        @Test
        public void should_Return_SubjectsList(){
            //given
            subjects = new ArrayList<>(Arrays.asList(history,PE,math,english));
            classTimetable = new ClassTimetable("XD", Grade.I,subjects);
            List<SubjectTimetable> expected = subjects;

            //when
            List<SubjectTimetable> actual = classTimetable.getAllSubjects();

            //then
            assertEquals(expected.size(),actual.size());
            assertTrue(expected.containsAll(actual));

        }

        @Test
        public void should_Return_EmptyList(){
            //given

            List<SubjectTimetable> expected = Collections.emptyList();
            classTimetable = new ClassTimetable("XD", Grade.I,expected);

            //when
            List<SubjectTimetable> actual = classTimetable.getAllSubjects();

            //then
            assertIterableEquals(expected, actual);

        }

    }
}