package com.example.SchoolSystem.school.schoolClass;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISchoolClassDao extends JpaRepository<SchoolClass, Long> {

    @Query("SELECT c FROM SchoolClass c WHERE c.everySubjectHasTeacherAssigned = true")
    List<SchoolClass> findClassesWithSubjectTeachersAssigned();

    @Query("SELECT c FROM SchoolClass c WHERE c.everySubjectHasTeacherAssigned = false")
    List<SchoolClass> findClassesWithoutSubjectTeachersAssigned();

    @Query("SELECT c FROM SchoolClass c WHERE c.isGraduated = false")
    List<SchoolClass> findAllNotGraduatedClasses();


    @Query("SELECT c FROM SchoolClass c WHERE c.isFull = false")
    List<SchoolClass> findWithAvailableCapacity();

    @Query("SELECT CASE WHEN COUNT(c) > 0 then true ELSE false END FROM SchoolClass c WHERE c.grade = :grade AND c.alphabeticalGrade = :alphabeticalGrade AND c.isGraduated = false")
    boolean existsByTotalGrade(@Param("grade")String grade, @Param("alphabeticalGrade") String alphabeticalGrade);


}
