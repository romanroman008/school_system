package com.example.SchoolSystem.school.web.controllers;

import com.example.SchoolSystem.school.schoolSubject.service.ISchoolSubjectService;
import com.example.SchoolSystem.school.schoolSubject.SchoolSubjectDto;
import com.example.SchoolSystem.school.schoolSubject.SchoolSubjectRequest;
import com.example.SchoolSystem.school.schoolSubject.converters.SchoolSubjectConverter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@RequestMapping("api/school-subjects")
@RestController
@Validated
public class SchoolSubjectController {
    @Autowired
    private ISchoolSubjectService schoolSubjectService;
    @Autowired
    private SchoolSubjectConverter schoolSubjectConverter;

    @PostMapping(headers = "X-API-VERSION=1")
    public EntityModel<SchoolSubjectDto> add(@RequestBody @Valid SchoolSubjectRequest request) {
        SchoolSubjectDto saved = schoolSubjectConverter.toDto(schoolSubjectService.add(schoolSubjectConverter.fromRequest(request)));
        return createEntityModelWithHateoas(saved);
    }

    @PostMapping(value = "/list", headers = "X-API-VERSION=1")
    public CollectionModel<SchoolSubjectDto> add(@RequestBody List<@Valid SchoolSubjectRequest> requests) {
        List<SchoolSubjectDto> saved = schoolSubjectConverter.toDto(schoolSubjectService.addAll(schoolSubjectConverter.fromRequest(requests)));
        return createCollectionModelWithHateoas(saved);
    }

    @GetMapping(value = "/{id}", headers = "X-API-VERSION=1")
    public EntityModel<SchoolSubjectDto> read(@PathVariable Long id) {

        SchoolSubjectDto found = schoolSubjectConverter.toDto(schoolSubjectService.findById(id));
        return createEntityModelWithHateoas(found);

    }


    @DeleteMapping(value = "/{id}", headers = "X-API-VERSION=1")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        schoolSubjectService.delete(id);
        return new ResponseEntity<>("Subject deleted.", HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", headers = "X-API-VERSION=1")
    public EntityModel<SchoolSubjectDto> update(@PathVariable("id") Long id, @RequestBody SchoolSubjectRequest request) {
        SchoolSubjectDto updated = schoolSubjectConverter.toDto(schoolSubjectService.update(id, schoolSubjectConverter.fromRequest(request)));
        return createEntityModelWithHateoas(updated);
    }


    @GetMapping(headers = "X-API-VERSION=1")
    public CollectionModel<SchoolSubjectDto> getALL() {
        List<SchoolSubjectDto> subjects = schoolSubjectConverter.toDto(schoolSubjectService.findAll());
        return createCollectionModelWithHateoas(subjects);
    }


    private EntityModel<SchoolSubjectDto> createEntityModelWithHateoas(SchoolSubjectDto schoolSubjectDto) {
        Link selfLink = linkTo(SchoolSubjectController.class).slash(schoolSubjectDto.getId()).withSelfRel();
        Link link = linkTo(SchoolSubjectController.class).withSelfRel();
        return EntityModel.of(schoolSubjectDto).add(selfLink).add(link);
    }

    private CollectionModel<SchoolSubjectDto> createCollectionModelWithHateoas(List<SchoolSubjectDto> schoolSubjects) {
        for (final SchoolSubjectDto schoolSubjectDto : schoolSubjects) {
            Long id = schoolSubjectDto.getId();
            Link selfLink = linkTo(SchoolSubjectController.class).slash(id).withSelfRel();
            schoolSubjectDto.add(selfLink);
        }
        Link link = linkTo(SchoolSubjectController.class).withSelfRel();
        return CollectionModel.of(schoolSubjects, link);
    }
}
