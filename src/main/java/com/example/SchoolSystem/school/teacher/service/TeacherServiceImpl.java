package com.example.SchoolSystem.school.teacher.service;

import com.example.SchoolSystem.school.teacher.ITeacherDao;

import com.example.SchoolSystem.school.teacher.Teacher;
import com.example.SchoolSystem.school.schoolSubject.SchoolSubject;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements ITeacherService {


    private final ITeacherDao teacherDao;


    @Override
    public Teacher add(Teacher teacher) {
        if (!teacherDao.existsByPesel(teacher.getPersonInformation().getIDNumber()))
            return teacherDao.save(teacher);
        throw new EntityExistsException(String.format("Teacher %s already exists",teacher.getFullName()));
    }

    @Override
    public List<Teacher> addAll(List<Teacher> teachers) {
        List<String> teachersAlreadyExisting = teachers
                .stream()
                .filter(teacher -> teacherDao.existsByPesel(teacher.getPersonInformation().getIDNumber()))
                .map(Teacher::getFullName)
                .toList();
        if (!teachersAlreadyExisting.isEmpty())
            throw new EntityExistsException(String.format("List of teachers contains already existing teachers: %s", teachersAlreadyExisting));

        return teacherDao.saveAll(teachers);
    }



    @Override
    public Teacher findById(Long id) {
        Optional<Teacher> teacher = teacherDao.findById(id);
        if (teacher.isEmpty())
            throw new EntityNotFoundException(String.format("Teacher with id: %d is not found", id));

        return teacher.get();
    }

    @Override
    public List<Teacher> findAll() {
        return teacherDao.findAll();
    }

    @Override
    public List<Teacher> findGivenSubjectTeachers(SchoolSubject schoolSubject) {
        return teacherDao.findByTeachingSubjectsContains(schoolSubject);
    }



    @Override
    public Teacher update(Long id,Teacher updatedTeacher) {
        Teacher teacher = findById(id);
        teacher.setPersonInformation(updatedTeacher.getPersonInformation());
        teacher.setAddress(updatedTeacher.getAddress());
        teacher.setSalary(updatedTeacher.getSalary());
        teacher.setHoursPerWeek(updatedTeacher.getHoursPerWeek());
        teacherDao.flush();
        return teacher;
    }

    @Override
    public void flush(){
        this.teacherDao.flush();
    }






    @Override
    public Teacher delete(Long id) {
        Optional<Teacher> teacher = teacherDao.findById(id);
        if (teacher.isPresent()) {
            teacherDao.deleteById(id);
            if (teacherDao.existsById(id))
                throw new DataIntegrityViolationException(String.format("Failed to delete teacher with id = %d ", id));
            return teacher.get();
        } else
            throw new EntityNotFoundException(String.format("Teacher with id: %d does not exists", id));

    }


}
