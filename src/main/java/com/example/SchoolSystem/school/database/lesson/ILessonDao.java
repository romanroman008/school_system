package com.example.SchoolSystem.school.database.lesson;

import com.example.SchoolSystem.school.entities.lesson.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILessonDao extends JpaRepository<Lesson, Long> {
}
