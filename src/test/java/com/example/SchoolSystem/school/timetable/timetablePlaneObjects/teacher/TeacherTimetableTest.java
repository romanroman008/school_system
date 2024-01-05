package com.example.SchoolSystem.school.timetable.timetablePlaneObjects.teacher;



import com.example.SchoolSystem.school.timetable.timetablePlainObjects.teacher.TeacherTimetable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TeacherTimetableTest {

    TeacherTimetable teacher;
    int HOURS_PER_WEEK = 40;

    @BeforeEach
    void setUp(){
        teacher = new TeacherTimetable(1,"Ticzer", HOURS_PER_WEEK);
    }
   @Nested
    class assignHours{

       @ParameterizedTest
       @ValueSource(ints = {1,10,39,40})
       public void should_Assign_ProperAmountOfHours(int amount){
           //given
           int expected = HOURS_PER_WEEK - amount;

           //when
           int actual = teacher.assignHours(amount).getHoursAvailable();

           //then
           assertEquals(expected,actual);
       }

       @ParameterizedTest
       @ValueSource(ints = {0,41,42,50,100})
       public void should_DoesNotAssign_AnyAmountOfHours(int amount){
           //given
           int expected = HOURS_PER_WEEK;

           //when
           int actual = teacher.assignHours(amount).getHoursAvailable();

           //then
           assertEquals(expected,actual);
       }
   }
   @Nested
    class checkIfHasSufficientAmountOfHours{
        @ParameterizedTest
        @ValueSource(ints = {41,42,50,100})
        public void should_ReturnFalse_When_GivenAmountOfHoursIsHigherThenHoursPerWeek(int amount){
            //when
            boolean actual = teacher.checkIfHasSufficientAmountOfHours(amount);

            //then
            assertFalse(actual);
        }

       @ParameterizedTest
       @ValueSource(ints = {40,32,0,10})
       public void should_ReturnTrue_When_GivenAmountOfHoursIsLowerOrEqualsThenHoursPerWeek(int amount){
           //when
           boolean actual = teacher.checkIfHasSufficientAmountOfHours(amount);

           //then
           assertTrue(actual);
       }
   }

}