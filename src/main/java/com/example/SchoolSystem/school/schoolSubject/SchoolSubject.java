package com.example.SchoolSystem.school.schoolSubject;


import com.example.SchoolSystem.school.schoolClass.Grade;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "school_subject")
public class SchoolSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Setter
    private String name;
    @Setter
    @ElementCollection
    @CollectionTable(name = "subject_hours_per_week", joinColumns = @JoinColumn(name = "subject_id"))
    @MapKeyColumn(name = "grade")
    @Column(name = "hours_per_week")
    private Map<Grade,Integer> hoursPerWeek;

    public SchoolSubject(String name, Map<Grade, Integer> hoursPerWeek) {
        this.name = name;
        this.hoursPerWeek = hoursPerWeek;
    }


    public void changeHoursPerWeekForSpecificGrade(Grade grade, int hours){
        hoursPerWeek.replace(grade,hours);
    }


    public int getHoursPerWeek(Grade grade) {
        return hoursPerWeek.get(grade);
    }


}
