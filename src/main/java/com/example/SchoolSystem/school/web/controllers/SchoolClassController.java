package com.example.SchoolSystem.school.web.controllers;


import com.example.SchoolSystem.school.entities.schoolClass.SchoolClass;
import com.example.SchoolSystem.school.entities.schoolClass.service.ISchoolClassService;
import com.example.SchoolSystem.school.entities.schoolClass.strategy.AlphabeticalStrategy;
import com.example.SchoolSystem.school.web.dto.schoolClass.converters.ToDtoSchoolClassConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("api/schoolClasses")
@RestController
public class SchoolClassController {

    @Autowired
    private ISchoolClassService schoolClassService;


    @PostMapping("/create")
    public ResponseEntity<Object> createClasses(@RequestParam int strategy) {
        try {
            List<SchoolClass> created = schoolClassService.create(strategy);
            return new ResponseEntity<>(ToDtoSchoolClassConverter.convert(created), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {

        List<SchoolClass> created = schoolClassService.findAll();
        return new ResponseEntity<>(ToDtoSchoolClassConverter.convert(created), HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        schoolClassService.remove(id);
    }


}
