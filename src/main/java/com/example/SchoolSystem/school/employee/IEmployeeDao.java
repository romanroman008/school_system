package com.example.SchoolSystem.school.employee;


import com.example.SchoolSystem.school.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeDao extends JpaRepository<Employee,Long> {

    @Query("SELECT CASE WHEN COUNT(e) > 0 then true ELSE false END FROM Employee e WHERE e.personInformation.pesel = :pesel")
    boolean existsByPesel(@Param("pesel") String pesel);
}
