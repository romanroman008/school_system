package com.example.SchoolSystem.school.employee.service;


import com.example.SchoolSystem.school.employee.Employee;

import java.util.List;

public interface IEmployeeService {

    List<Employee> getAllEmployees();
    void updateEmployees(List<Employee> employees);
    Employee add(Employee employee);
    void delete(Long id);
    void update(Employee employee);
}
