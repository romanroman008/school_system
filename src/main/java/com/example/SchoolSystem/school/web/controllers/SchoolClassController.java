package com.example.SchoolSystem.school.web.controllers;


import com.example.SchoolSystem.school.entities.schoolClass.SchoolClass;
import com.example.SchoolSystem.school.entities.schoolClass.service.ISchoolClassService;
import com.example.SchoolSystem.school.web.dto.schoolClass.SchoolClassDto;
import com.example.SchoolSystem.school.web.dto.schoolClass.converters.SchoolClassConverter;
import com.example.SchoolSystem.school.web.dto.teacher.TeacherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@RequestMapping("api/school-classes")
@RestController
public class SchoolClassController {

    @Autowired
    private ISchoolClassService schoolClassService;

    @Autowired
    private SchoolClassConverter schoolClassConverter;


    @PostMapping(value = "/create", headers = "X-API-VERSION=1")
    public CollectionModel<SchoolClassDto> createClasses(@RequestParam int strategy) {
            List<SchoolClassDto> created =  schoolClassConverter.toDto(schoolClassService.create(strategy));
            return createCollectionModel(created);
    }

    @GetMapping(headers = "X-API-VERSION=1")
    public CollectionModel<SchoolClassDto> getAll() {
        List<SchoolClassDto> created = schoolClassConverter.toDto(schoolClassService.findAll());
        return createCollectionModel(created);

    }

    @DeleteMapping(value ="/{id}",headers = "X-API-VERSION=1")
    public void delete(@PathVariable Long id) {
        schoolClassService.remove(id);
    }


    private EntityModel<SchoolClassDto> createEntityModel(SchoolClassDto schoolClass) {
        Link selfLink = linkTo(SchoolClassController.class).slash(schoolClass.getId()).withSelfRel();
        Link link = linkTo(SchoolClassController.class).withSelfRel();

        return EntityModel.of(schoolClass).add(selfLink).add(link);
    }

    private CollectionModel<SchoolClassDto> createCollectionModel(List<SchoolClassDto> schoolClasses) {
        for (final SchoolClassDto schoolClass : schoolClasses) {
            Long id = schoolClass.getId();
            Link selfLink = linkTo(SchoolClassController.class).slash(id).withSelfRel();
            schoolClass.add(selfLink);
        }

        Link link = linkTo(SchoolClassController.class).withSelfRel();
        return CollectionModel.of(schoolClasses, link);
    }


}
