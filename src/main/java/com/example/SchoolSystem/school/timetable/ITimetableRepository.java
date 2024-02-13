package com.example.SchoolSystem.school.timetable;


import com.example.SchoolSystem.school.timetable.timetablePlainObjects.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITimetableRepository extends JpaRepository<Timetable, Long> {


}
