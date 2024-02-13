package com.example.SchoolSystem.school.timetable.timetablePlainObjects;




import com.example.SchoolSystem.school.timetable.timetablePlainObjects.lesson.FreePeriod;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.time.Week;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.lesson.LessonConverter;
import com.example.SchoolSystem.school.lesson.Lesson;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
@Table(name ="timetable")
public class Timetable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;


    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "timetable_id", referencedColumnName = "id")
    private List<Lesson> lessons = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "timetable_id", referencedColumnName = "id")
    private List<FreePeriod> freePeriods = new ArrayList<>();






    public Timetable(Week week) {
        setLessons(week);
        freePeriods = week.findAllFreePeriods();
    }


    public List<Lesson> getLessonsOfGivenSchoolClasses(List<String> totalGrades){
        return lessons.stream().filter(lesson -> totalGrades.contains(lesson.getSchoolClass())).toList();
    }





    private void setLessons(Week week){
        this.lessons = week.days.stream()
                .flatMap(day -> day.getHours().stream())
                .flatMap(hour -> hour.getLessons().stream())
                .map(LessonConverter::toEntityFromTimetable)
                .sorted(Comparator.comparingLong(Lesson::getSortingNumber))
                .toList();
    }



}
