package com.example.SchoolSystem.school.database.timetable;


import com.example.SchoolSystem.school.entities.lesson.Lesson;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITimetableRepository extends JpaRepository<Timetable, Long> {


}
