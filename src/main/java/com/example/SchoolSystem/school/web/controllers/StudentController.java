package com.example.SchoolSystem.school.web.controllers;

import com.example.SchoolSystem.school.student.service.IStudentService;
import com.example.SchoolSystem.school.student.Student;
import com.example.SchoolSystem.school.student.StudentDto;
import com.example.SchoolSystem.school.student.StudentConverter;
import com.example.SchoolSystem.school.student.StudentRequest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RequestMapping(value = "api/students")
@RestController
@Validated
public class StudentController {
    @Autowired
    private IStudentService studentService;

    @Autowired
    private StudentConverter studentConverter;

    @PostMapping(headers = "X-API-VERSION=1")
    public EntityModel<StudentDto> add(@Valid @RequestBody StudentRequest student) {
        Student saved = studentService.add(studentConverter.fromRequest(student));

        EntityModel<StudentDto> entityModel = EntityModel.of(studentConverter.toDto(saved));
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).read());
        entityModel.add(link.withRel("all_students"));
        return entityModel;
    }

    @PostMapping(value = "/list", headers = "X-API-VERSION=1")
    public CollectionModel<StudentDto> add(@RequestBody List<@Valid StudentRequest> requests) {
        List<StudentDto> students = studentConverter.toDto(studentService.addAll(studentConverter.fromRequest(requests)));

        for (final StudentDto student : students) {
            Long id = student.getId();
            Link selfLink = linkTo(StudentController.class).slash(id).withSelfRel();
            student.add(selfLink);
        }

        Link link = linkTo(StudentController.class).withSelfRel();
        return CollectionModel.of(students, link);


    }

    @GetMapping(value = "/{id}", headers = "X-API-VERSION=1")
    public EntityModel<StudentDto> read(@PathVariable Long id) {
        Student student = studentService.findById(id);

        EntityModel<StudentDto> entityModel = EntityModel.of(studentConverter.toDto(student));
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).read(id));
        entityModel.add(link.withRel("self"));
        return entityModel;
    }

    @GetMapping(headers = "X-API-VERSION=1")
    public CollectionModel<StudentDto> read() {
        List<StudentDto> students = studentConverter.toDto(studentService.findAll());

        for (final StudentDto student : students) {
            Long id = student.getId();
            Link selfLink = linkTo(StudentController.class).slash(id).withSelfRel();
            student.add(selfLink);
        }

        Link link = linkTo(StudentController.class).withSelfRel();
        return CollectionModel.of(students, link);
    }


    @PutMapping(value = "/{id}", headers = "X-API-VERSION=1")
    public EntityModel<StudentDto> update(@PathVariable Long id, @Valid @RequestBody StudentRequest request) {
        StudentDto saved = studentConverter.toDto(studentService.update(id, studentConverter.fromRequest(request)));

        Link selfLink = linkTo(StudentController.class).slash(saved.getId()).withSelfRel();
        saved.add(selfLink);
        return EntityModel.of(saved).add(selfLink);
    }


    @DeleteMapping(value = "/{id}", headers = "X-API-VERSION=1")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        studentService.delete(id);
        return new ResponseEntity<>("Student deleted.", HttpStatus.OK);
    }


}
