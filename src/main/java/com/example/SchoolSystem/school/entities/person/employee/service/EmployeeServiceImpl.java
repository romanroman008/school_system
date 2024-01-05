package com.example.SchoolSystem.school.entities.person.employee.service;


import com.example.SchoolSystem.school.database.employee.IEmployeeDao;
import com.example.SchoolSystem.school.entities.person.employee.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements IEmployeeService {

    private final IEmployeeDao employeeDao;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeDao.findAll();
    }

    @Override
    public void updateEmployees(List<Employee> employees) {
        employees.forEach(employeeDao::save);
    }

    @Override
    public Employee add(Employee employee) {
        return  employeeDao.save(employee);
    }

    @Override
    public void delete(Long id) {
        employeeDao.deleteById(id);
    }

    @Override
    public void update(Employee employee) {
        employeeDao.save(employee);
    }
}
