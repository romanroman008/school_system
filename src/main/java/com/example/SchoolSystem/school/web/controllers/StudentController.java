package com.example.SchoolSystem.school.web.controllers;

import com.example.SchoolSystem.school.entities.person.student.service.IStudentService;
import com.example.SchoolSystem.school.entities.person.student.Student;
import com.example.SchoolSystem.school.web.dto.student.StudentDto;
import com.example.SchoolSystem.school.web.dto.student.converters.FromRequestStudentConverter;
import com.example.SchoolSystem.school.web.dto.student.StudentRequest;
import com.example.SchoolSystem.school.web.dto.student.converters.ToDtoStudentConverter;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("api/student")
@RestController
@Validated
public class StudentController {
    @Autowired
    private IStudentService studentService;

    @Autowired
    private FromRequestStudentConverter fromRequestStudentConverter;

    @PostMapping
    public ResponseEntity<Object> add(@Valid @RequestBody StudentRequest student) {
        try {
            Student saved = studentService.add(fromRequestStudentConverter.convert(student));
            return new ResponseEntity<>(ToDtoStudentConverter.convert(saved), HttpStatus.OK);
        } catch (EntityExistsException e) {
            return new ResponseEntity<>(String.format("Error when saving object: %s", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(String.format("Error when saving object: %s", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @PostMapping("/all")
    public ResponseEntity<Object> add(@RequestBody List<@Valid StudentRequest> requests) {
        try {
            List<Student> saved = studentService.addAll(fromRequestStudentConverter.convert(requests));
            return new ResponseEntity<>(ToDtoStudentConverter.convert(saved), HttpStatus.OK);
        } catch (EntityExistsException e) {
            return new ResponseEntity<>(String.format("Error when saving object: %s", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(String.format("Error when saving object: %s", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> read(@PathVariable Long id) {
        try {
            Student student = studentService.findById(id);
            return new ResponseEntity<>(ToDtoStudentConverter.convert(student), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/all")
    public ResponseEntity<Object> read() {
        try {

            List<StudentDto> students = ToDtoStudentConverter.convert(studentService.findAll());
            return new ResponseEntity<>(students, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody StudentRequest request) {
        try {
            Student saved = studentService.update(id, fromRequestStudentConverter.convert(request));
            return new ResponseEntity<>(ToDtoStudentConverter.convert(saved), HttpStatus.OK);
        } catch (EntityNotFoundException e){
            return new ResponseEntity<>(String.format("Error when updating object: %s", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>(String.format("Error when updating object: %s", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete (@PathVariable("id") Long id){
        try {
            studentService.delete(id);
            return new ResponseEntity<>("Student deleted.", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Error when deleting object: %s" + e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>("Error when deleting object: %s" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }







}
