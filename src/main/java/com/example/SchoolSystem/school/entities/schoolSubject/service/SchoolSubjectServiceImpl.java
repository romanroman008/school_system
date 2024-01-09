package com.example.SchoolSystem.school.entities.schoolSubject.service;

import com.example.SchoolSystem.school.database.subject.ISchoolSubjectDao;
import com.example.SchoolSystem.school.entities.schoolSubject.SchoolSubject;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.usermodel.TableRowHeightRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SchoolSubjectServiceImpl implements ISchoolSubjectService {

    private final ISchoolSubjectDao schoolSubjectDao;



    @Override
    public SchoolSubject add(SchoolSubject subject) {
        if (schoolSubjectDao.existsByName(subject.getName()))
            throw new EntityExistsException("School subject is already in database");
        return schoolSubjectDao.save(subject);
    }

    @Override
    public List<SchoolSubject> addAll(List<SchoolSubject> subjects) {

        List<String> schoolSubjectsAlreadyExisting = subjects
                .stream()
                .map(SchoolSubject::getName)
                .filter(schoolSubjectDao::existsByName)
                .toList();
        if(!schoolSubjectsAlreadyExisting.isEmpty())
            throw new EntityExistsException(String.format("List of subjects contains already existing subjects: %s",schoolSubjectsAlreadyExisting));

        return schoolSubjectDao.saveAll(subjects);
    }



    @Override
    public SchoolSubject findById(Long id) {
        Optional<SchoolSubject> schoolSubject = schoolSubjectDao.findById(id);
        if (schoolSubject.isEmpty())
            throw new EntityNotFoundException(String.format("Subject with id: %d not found", id));
        return schoolSubject.get();
    }

    @Override
    public List<SchoolSubject> findAll() {
        return schoolSubjectDao.findAll().stream().toList();
    }



    @Override
    public SchoolSubject update(Long id, SchoolSubject updated) {
        SchoolSubject schoolSubject = findById(id);
        schoolSubject.setName(updated.getName());
        schoolSubject.setHoursPerWeek(updated.getHoursPerWeek());
        schoolSubjectDao.flush();
        return schoolSubject;
    }


    @Override
    public SchoolSubject delete(Long id) {
        Optional<SchoolSubject> schoolSubject = schoolSubjectDao.findById(id);
        if (schoolSubject.isPresent()) {
            schoolSubjectDao.deleteById(id);
            if (schoolSubjectDao.existsById(id))
                throw new DataIntegrityViolationException(String.format("Failed to delete subject with id: %d", id));
            return schoolSubject.get();
        } else
            throw new EntityNotFoundException(String.format("Subject with id: %d not found", id));
    }




}
