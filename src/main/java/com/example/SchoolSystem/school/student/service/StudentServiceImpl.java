package com.example.SchoolSystem.school.student.service;

import com.example.SchoolSystem.school.student.IStudentDao;
import com.example.SchoolSystem.school.student.Student;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements IStudentService {


    private final IStudentDao studentDao;

    @Override
    public Student add(@Valid Student student) {
        if (!studentDao.existsByPesel(student.getPersonInformation().getPesel()))
            return studentDao.save(student);
        throw new EntityExistsException("Student already exists");
    }

    @Override
    public List<Student> addAll(List<Student> students) {
        List<String> teachersAlreadyExisting = students
                .stream()
                .filter(teacher -> studentDao.existsByPesel(teacher.getPersonInformation().getPesel()))
                .map(Student::getFullName)
                .toList();
        if (!teachersAlreadyExisting.isEmpty())
            throw new EntityExistsException(String.format("List of students contains already existing students: %s", teachersAlreadyExisting));

        return studentDao.saveAll(students);
    }

    @Override
    public Student delete(Long id) {
        Optional<Student> student = studentDao.findById(id);
        if (student.isPresent()) {
            studentDao.deleteById(id);
            if (studentDao.existsById(id))
                throw new DataIntegrityViolationException(String.format("Failed to delete student with id = %d ", id));
            return student.get();
        } else
            throw new EntityNotFoundException(String.format("Student with id: %d does not exists", id));

    }

    @Override
    public Student update(Long id, Student updatedStudent) {
        Student student = findById(id);
        student.setPersonInformation(updatedStudent.getPersonInformation());
        student.setAddress(updatedStudent.getAddress());
        student.setGrade(updatedStudent.getGrade());
        studentDao.flush();
        return student;
    }


    @Override
    public Student findById(Long id) {
        Optional<Student> student = studentDao.findById(id);
        if(student.isPresent())
            return student.get();
        throw new EntityNotFoundException("Student does not exists");
    }

    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }



    @Override
    public List<Student> getStudentsUnassignedToClasses() {
        return studentDao.findStudentsNotAssignedToClasses();
    }
}
