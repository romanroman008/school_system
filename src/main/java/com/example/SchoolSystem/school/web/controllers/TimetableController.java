package com.example.SchoolSystem.school.web.controllers;

import com.example.SchoolSystem.printer.IPrinter;
import com.example.SchoolSystem.school.exceptions.NotEnoughTeachersException;
import com.example.SchoolSystem.school.exceptions.SchoolClassHasToManyLessonsPerWeekException;
import com.example.SchoolSystem.school.timetable.service.ITimetableService;
import com.example.SchoolSystem.school.timetable.converter.TimetableConverter;
import com.example.SchoolSystem.school.web.dto.timetable.TimetableDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/timetable")
public class TimetableController {

    @Autowired
    ITimetableService timetableService;




    @PostMapping("/create")
    public ResponseEntity<Object> create(){

        try{
            TimetableDto saved = TimetableConverter.toDto(timetableService.create());
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (NotEnoughTeachersException e) {
            return new ResponseEntity<>(String.format("Error occured while creating object: %s",
                    e.getHireRecommendation().getMessage()), HttpStatus.CONFLICT);
        } catch (SchoolClassHasToManyLessonsPerWeekException e) {
            return new ResponseEntity<>(String.format("Error occured while creating object: %s", e.getMessage()), HttpStatus.CONFLICT);
        }
        catch (Exception e) {
            return new ResponseEntity<>(String.format("Error occured while creating object: %s", e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/excel")
    public ResponseEntity<Object> generateExcelFile(@RequestParam(name = "id") Long timetableId){
        try{
            TimetableDto generated = TimetableConverter.toDto(timetableService.generateToExcel(timetableId));
            return new ResponseEntity<>(generated, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable Long id){
        try {
            TimetableDto found = TimetableConverter.toDto(timetableService.findById(id));
            return new ResponseEntity<>(found, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("hehe", HttpStatus.NOT_FOUND);
        }
    }



}
