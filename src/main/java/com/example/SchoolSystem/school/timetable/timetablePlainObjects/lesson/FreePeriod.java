package com.example.SchoolSystem.school.timetable.timetablePlainObjects.lesson;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Getter
@Entity
@NoArgsConstructor
public class FreePeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    int number;
    @Column(name = "DayName")
    String day;
    String schoolClass;
    List<String> availableTeachers;

    public FreePeriod(int number, String day, String schoolClass, List<String> availableTeachers) {
        this.number = number;
        this.day = day;
        this.schoolClass = schoolClass;
        this.availableTeachers = availableTeachers;
    }
}
