package com.example.SchoolSystem.school.teacher;

import com.example.SchoolSystem.school.schoolSubject.SchoolSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITeacherDao extends JpaRepository<Teacher,Long> {
    @Query("SELECT CASE WHEN COUNT(t) > 0 then true ELSE false END FROM Teacher t WHERE t.personInformation.pesel = :pesel")
    boolean existsByPesel(@Param("pesel") String pesel);

    List<Teacher> findByTeachingSubjectsContains(SchoolSubject subject);
}
