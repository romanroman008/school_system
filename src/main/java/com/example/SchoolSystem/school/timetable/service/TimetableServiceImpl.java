package com.example.SchoolSystem.school.timetable.service;

import com.example.SchoolSystem.printer.IPrinter;
import com.example.SchoolSystem.school.database.timetable.ITimetableRepository;
import com.example.SchoolSystem.school.entities.assignments.IAssignmentService;
import com.example.SchoolSystem.school.timetable.converter.TimetableConverter;
import com.example.SchoolSystem.school.timetable.factory.ITimetableFactory;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.Timetable;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TimetableServiceImpl implements ITimetableService {

    private final ITimetableRepository timetableRepository;
    private final  ITimetableFactory timetableFactory;

    private final IPrinter printer;



    @Override
    public Timetable create() {
        Timetable created = timetableFactory.create();
        return timetableRepository.save(created);
    }

    @Override
    public Timetable generateToExcel(Long id) throws IOException {
        Timetable timetable = findById(id);
        printer.print(TimetableConverter.toExcel(timetable));
        return timetable;
    }


    @Override
    public Timetable findById(Long id) {
        Optional<Timetable> timetable = timetableRepository.findById(id);
        if (timetable.isEmpty())
            throw new EntityNotFoundException(String.format("Timetable with id %s is not found", id));
        return timetable.get();
    }
    @Override
    public List<Timetable> findAll() {
        return timetableRepository.findAll();
    }


    @Override
    public void remove(Long id) {
        Timetable timetable = findById(id);
        timetableRepository.delete(timetable);
        if(timetableRepository.existsById(id))
            throw new DataIntegrityViolationException(String.format("Failed to delete timetable with id: %s",id));
    }
}
