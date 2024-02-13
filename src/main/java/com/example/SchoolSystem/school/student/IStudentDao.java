package com.example.SchoolSystem.school.student;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStudentDao  extends JpaRepository<Student,Long> {

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Student s WHERE s.personInformation.pesel = :pesel")
    boolean existsByPesel(@Param("pesel") String pesel);

    @Query("SELECT s FROM Student s WHERE s.isAssignedToClass = false")
    List<Student> findStudentsNotAssignedToClasses();
}
