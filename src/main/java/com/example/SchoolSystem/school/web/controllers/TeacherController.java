package com.example.SchoolSystem.school.web.controllers;


import com.example.SchoolSystem.school.entities.person.teacher.Teacher;
import com.example.SchoolSystem.school.entities.person.teacher.service.ITeacherService;
import com.example.SchoolSystem.school.web.dto.teacher.TeacherDto;
import com.example.SchoolSystem.school.web.dto.teacher.TeacherRequest;
import com.example.SchoolSystem.school.web.dto.teacher.converters.FromRequestTeacherConverter;
import com.example.SchoolSystem.school.web.dto.teacher.converters.ToDtoTeacherConverter;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Validated
@RequestMapping("api/teacher")
public class TeacherController {

    @Autowired
    private ITeacherService teacherService;

    @Autowired
    private FromRequestTeacherConverter fromRequestTeacherConverter;


    @PostMapping
    public ResponseEntity<Object> add(@Valid @RequestBody TeacherRequest request) {
        try {
            Teacher saved = teacherService.add(fromRequestTeacherConverter.convert(request));
            return new ResponseEntity<>(ToDtoTeacherConverter.convert(saved), HttpStatus.OK);
        } catch (EntityExistsException e) {
            return new ResponseEntity<>(String.format("Error when saving teacher: %s", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(String.format("Error when saving teacher: %s", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @PostMapping("/all")
    public ResponseEntity<Object> add(@RequestBody List<@Valid TeacherRequest> requests) {
        try {
            List<Teacher> saved = teacherService.addAll(fromRequestTeacherConverter.convert(requests));
            return new ResponseEntity<>(ToDtoTeacherConverter.convert(saved), HttpStatus.OK);
        } catch (EntityExistsException e) {
            return new ResponseEntity<>(String.format("Error when saving teachers: %s", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(String.format("Error when saving teachers: %s", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> read(@PathVariable Long id) {
        try {
            Teacher found = teacherService.findById(id);
            return new ResponseEntity<>(ToDtoTeacherConverter.convert(found), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/all")
    public ResponseEntity<List<TeacherDto>> read() {
        List<TeacherDto> teachers = ToDtoTeacherConverter.convert(teacherService.findAll());
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody TeacherRequest request) {
        try {
            Teacher saved = teacherService.update(id, fromRequestTeacherConverter.convert(request));
            return new ResponseEntity<>(ToDtoTeacherConverter.convert(saved), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(String.format("Error when updating teacher: %s", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            Teacher deleted = teacherService.delete(id);
            return new ResponseEntity<>(ToDtoTeacherConverter.convert(deleted), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

