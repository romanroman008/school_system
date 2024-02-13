package com.example.SchoolSystem.school.web.controllers;

import com.example.SchoolSystem.school.timetable.service.ITimetableService;
import com.example.SchoolSystem.school.timetable.converter.TimetableConverter;
import com.example.SchoolSystem.school.timetable.TimetableDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/timetables")
public class TimetableController {

    @Autowired
    ITimetableService timetableService;


    @PostMapping(value = "/create", headers = "X-API-VERSION=1")
    public ResponseEntity<Object> create() {
        TimetableDto saved = TimetableConverter.toDto(timetableService.create());
        return new ResponseEntity<>(saved, HttpStatus.CREATED);

    }

    @PostMapping(value = "/excel", headers = "X-API-VERSION=1")
    public ResponseEntity<Object> generateExcelFile(@RequestParam(name = "id") Long timetableId) throws IOException {
        TimetableDto generated = TimetableConverter.toDto(timetableService.generateToExcel(timetableId));
        return new ResponseEntity<>(generated, HttpStatus.CREATED);
    }


    @GetMapping(value = "/{id}", headers = "X-API-VERSION=1")
    public ResponseEntity<Object> get(@PathVariable Long id) {
        TimetableDto found = TimetableConverter.toDto(timetableService.findById(id));
        return new ResponseEntity<>(found, HttpStatus.OK);
    }


}
