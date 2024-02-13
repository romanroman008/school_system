package com.example.SchoolSystem.school.lesson;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILessonDao extends JpaRepository<Lesson, Long> {
}
