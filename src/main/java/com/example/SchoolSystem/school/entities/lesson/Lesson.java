package com.example.SchoolSystem.school.entities.lesson;


import com.example.SchoolSystem.school.timetable.timetablePlainObjects.Timetable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@Getter
@Table(name = "lesson")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private long sortingNumber;
    private int number;
    @Column(name = "lesson_day")
    private String day;
    private String schoolClass;
    private String subject;
    private String teacher;




    public Lesson(long sortingNumber, int number, String day, String schoolClass, String subject, String teacher) {
        this.sortingNumber = sortingNumber;
        this.number = number;
        this.day = day;
        this.schoolClass = schoolClass;
        this.subject = subject;
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", number=" + number +
                ", day='" + day + '\'' +
                ", schoolClass='" + schoolClass + '\'' +
                ", subject='" + subject + '\'' +
                ", teacher='" + teacher + '\'' +
                '}';
    }
}
