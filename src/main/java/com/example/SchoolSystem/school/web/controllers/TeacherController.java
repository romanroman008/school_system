package com.example.SchoolSystem.school.web.controllers;


import com.example.SchoolSystem.school.entities.person.teacher.Teacher;
import com.example.SchoolSystem.school.entities.person.teacher.service.ITeacherService;
import com.example.SchoolSystem.school.web.dto.schoolSubject.SchoolSubjectDto;
import com.example.SchoolSystem.school.web.dto.student.StudentDto;
import com.example.SchoolSystem.school.web.dto.teacher.TeacherDto;
import com.example.SchoolSystem.school.web.dto.teacher.TeacherRequest;
import com.example.SchoolSystem.school.web.dto.teacher.converters.TeacherConverter;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@RestController
@Validated
@RequestMapping("api/teachers")
public class TeacherController {

    @Autowired
    private ITeacherService teacherService;

    @Autowired
    private TeacherConverter teacherConverter;


    @PostMapping(headers = "X-API-VERSION=1")
    public EntityModel<TeacherDto> add(@Valid @RequestBody TeacherRequest request) {

        TeacherDto saved = teacherConverter.toDto(teacherService.add(teacherConverter.fromRequest(request)));
        return createEntityModel(saved);
    }

    @PostMapping(value = "/list", headers = "X-API-VERSION=1")
    public CollectionModel<TeacherDto> add(@RequestBody List<@Valid TeacherRequest> requests) {
        List<TeacherDto> savedTeachers = teacherConverter.toDto(teacherService.addAll(teacherConverter.fromRequest(requests)));
        return createCollectionModel(savedTeachers);
    }

    @GetMapping(value = "/{id}", headers = "X-API-VERSION=1")
    public EntityModel<TeacherDto> read(@PathVariable Long id) {
        TeacherDto teacher = teacherConverter.toDto(teacherService.findById(id));
        return createEntityModel(teacher);
    }

    @GetMapping(headers = "X-API-VERSION=1")
    public CollectionModel<TeacherDto> read() {
        List<TeacherDto> teachers = teacherConverter.toDto(teacherService.findAll());
        return createCollectionModel(teachers);
    }



    @PutMapping(value = "/{id}", headers = "X-API-VERSION=1")
    public EntityModel<TeacherDto> update(@PathVariable Long id, @Valid @RequestBody TeacherRequest request) {
        TeacherDto saved = teacherConverter.toDto(teacherService.update(id, teacherConverter.fromRequest(request)));
        return createEntityModel(saved);
    }


    @DeleteMapping(value = "/{id}", headers = "X-API-VERSION=1")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        Teacher deleted = teacherService.delete(id);
        return new ResponseEntity<>(teacherConverter.toDto(deleted), HttpStatus.OK);

    }


    private EntityModel<TeacherDto> createEntityModel(TeacherDto teacher) {
        Link selfLink = linkTo(TeacherController.class).slash(teacher.getId()).withSelfRel();
        Link link = linkTo(TeacherController.class).withSelfRel();

        return EntityModel.of(teacher).add(selfLink).add(link);
    }

    private CollectionModel<TeacherDto> createCollectionModel(List<TeacherDto> teachers) {
        for (final TeacherDto teacher : teachers) {
            Long id = teacher.getId();
            Link selfLink = linkTo(TeacherController.class).slash(id).withSelfRel();
            teacher.add(selfLink);
        }

        Link link = linkTo(TeacherController.class).withSelfRel();
        return CollectionModel.of(teachers, link);
    }

}

