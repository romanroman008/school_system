package com.example.SchoolSystem.school.schoolClass.service;


import com.example.SchoolSystem.school.schoolClass.ISchoolClassDao;
import com.example.SchoolSystem.school.student.Student;
import com.example.SchoolSystem.school.student.service.IStudentService;
import com.example.SchoolSystem.school.schoolClass.Grade;
import com.example.SchoolSystem.school.schoolClass.SchoolClass;
import com.example.SchoolSystem.school.schoolClass.factory.ISchoolClassFactory;
import com.example.SchoolSystem.school.schoolClass.factory.strategy.AlphabeticalStrategy;
import com.example.SchoolSystem.school.schoolClass.factory.strategy.BirthdayStrategy;
import com.example.SchoolSystem.school.schoolClass.factory.strategy.SchoolClassCreatingStrategy;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SchoolClassServiceImpl implements ISchoolClassService {

    private final ISchoolClassDao schoolClassDao;
    private final IStudentService studentService;
    private final ISchoolClassFactory schoolClassFactory;


    public SchoolClassServiceImpl(@Autowired ISchoolClassDao schoolClassDao,
                                  @Autowired IStudentService studentService,
                                  @Autowired ISchoolClassFactory schoolClassFactory) {
        this.schoolClassDao = schoolClassDao;
        this.studentService = studentService;
        this.schoolClassFactory = schoolClassFactory;
    }

    @Override
    public List<SchoolClass> create(int strategyNumber) {
        SchoolClassCreatingStrategy strategy;
        switch(strategyNumber){
            case 1 -> strategy = new AlphabeticalStrategy();
            case 2 -> strategy = new BirthdayStrategy();
            default -> throw new IllegalArgumentException(String.format("Strategy with given number %d is unknown", strategyNumber));
        }

        List<Student> students = studentService.getStudentsUnassignedToClasses();
        List<SchoolClass> classes = schoolClassDao.findAll();
        classes = schoolClassFactory.create(students, classes, strategy);
        schoolClassDao.flush();
        return classes;

    }

    @Override
    public SchoolClass add(SchoolClass schoolClass) {
        if (schoolClassDao.existsByTotalGrade(schoolClass.getGrade().toString(), schoolClass.getAlphabeticalGrade().toString()))
            throw new EntityExistsException(String.format("School class %s already exists", schoolClass.getTotalGrade()));
        return schoolClassDao.save(schoolClass);
    }

    @Override
    public List<SchoolClass> addAll(List<SchoolClass> classes) {
        List<String> schoolClassesAlreadyExists = classes
                .stream()
                .filter(schoolClass -> schoolClassDao.existsByTotalGrade(schoolClass.getGrade().toString(),schoolClass.getAlphabeticalGrade().toString()))
                .map(SchoolClass::getTotalGrade)
                .toList();
        if(schoolClassesAlreadyExists.isEmpty())
            return schoolClassDao.saveAll(classes);
        throw new EntityExistsException(String.format("List of classes contains already existing classes: %s", schoolClassesAlreadyExists));
    }


    @Override
    public SchoolClass findById(Long id) {
        return schoolClassDao
                .findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format("Class with id: %d is not found", id))
                );
    }

    @Override
    public List<SchoolClass> findWithAvailableCapacity() {
        return schoolClassDao.findWithAvailableCapacity();
    }

    @Override
    public List<SchoolClass> findWithoutTeachersAssigned() {
        return schoolClassDao.findClassesWithoutSubjectTeachersAssigned();
    }

    @Override
    public List<SchoolClass> findWithTeachersAssigned() {
        return schoolClassDao.findClassesWithSubjectTeachersAssigned();
    }

    @Override
    public List<SchoolClass> findAll() {
        return schoolClassDao.findAll();
    }

    @Override
    public List<SchoolClass> findAllNotGraduated() {
        return schoolClassDao.findAllNotGraduatedClasses();
    }


    @Override
    public void update(SchoolClass updatedSchoolClass) {
        SchoolClass schoolClass = findById(updatedSchoolClass.getId());

    }

    @Override
    public void updateAll(List<SchoolClass> classes) {
        classes.forEach(schoolClassDao::save);
    }

    @Override
    public void updateSchoolClasses(List<SchoolClass> classes) {
        schoolClassDao.saveAll(classes);
    }

    @Override
    public void flush() {
        schoolClassDao.flush();
    }

    @Override
    public void increaseAllClassesGrades() {
        List<SchoolClass> schoolClasses = schoolClassDao.findAllNotGraduatedClasses();
        schoolClasses.forEach(schoolClass -> {
            if (schoolClass.getGrade() == Grade.getHighestGrade())
                schoolClass.isGraduated = true;
            schoolClass.increaseGrade();
        });
        schoolClassDao.flush();
    }


    @Override
    public void remove(Long id) {
        SchoolClass schoolClass = findById(id);
        schoolClassDao.delete(schoolClass);
        if(schoolClassDao.existsById(id))
            throw new DataIntegrityViolationException(String.format("Failed to delete class : %s",schoolClass));
    }


}
