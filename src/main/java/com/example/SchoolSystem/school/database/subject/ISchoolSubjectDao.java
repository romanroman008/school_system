package com.example.SchoolSystem.school.database.subject;


import com.example.SchoolSystem.school.entities.schoolSubject.SchoolSubject;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ISchoolSubjectDao extends JpaRepository<SchoolSubject,Long> {
    boolean existsByName(String name);
}
