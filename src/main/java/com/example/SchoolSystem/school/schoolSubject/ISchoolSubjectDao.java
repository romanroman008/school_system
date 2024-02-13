package com.example.SchoolSystem.school.schoolSubject;


import org.springframework.data.jpa.repository.JpaRepository;


public interface ISchoolSubjectDao extends JpaRepository<SchoolSubject,Long> {
    boolean existsByName(String name);
}
