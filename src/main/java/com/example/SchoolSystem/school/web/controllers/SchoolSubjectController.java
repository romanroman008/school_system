package com.example.SchoolSystem.school.web.controllers;

import com.example.SchoolSystem.school.entities.schoolSubject.SchoolSubject;
import com.example.SchoolSystem.school.entities.schoolSubject.service.ISchoolSubjectService;
import com.example.SchoolSystem.school.web.dto.schoolSubject.SchoolSubjectDto;
import com.example.SchoolSystem.school.web.dto.schoolSubject.SchoolSubjectRequest;
import com.example.SchoolSystem.school.web.dto.schoolSubject.converters.FromRequestSchoolSubjectConverter;
import com.example.SchoolSystem.school.web.dto.schoolSubject.converters.ToDtoSchoolSubjectConverter;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("api/schoolSubject")
@RestController
@Validated
public class SchoolSubjectController {
    @Autowired
    private ISchoolSubjectService schoolSubjectService;

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody @Valid SchoolSubjectRequest request) {
        try {
            SchoolSubject saved = schoolSubjectService.add(FromRequestSchoolSubjectConverter.convert(request));
            return new ResponseEntity<>(ToDtoSchoolSubjectConverter.convert(saved), HttpStatus.OK);
        } catch (EntityExistsException e) {
            return new ResponseEntity<>("Error when saving object: %s" + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error when saving object: %s" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/all")
    public ResponseEntity<Object> add(@RequestBody List<@Valid SchoolSubjectRequest> requests) {
        try {
            List<SchoolSubject> saved = schoolSubjectService.addAll(FromRequestSchoolSubjectConverter.convert(requests));
            return new ResponseEntity<>(ToDtoSchoolSubjectConverter.convert(saved), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Error when saving object: %s" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> read(@PathVariable Long id) {
        try {
            SchoolSubject subject = schoolSubjectService.findById(id);
            return new ResponseEntity<>(ToDtoSchoolSubjectConverter.convert(subject), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Object not found: %s " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>("Object not found: %s " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        try {
            schoolSubjectService.delete(id);
            return new ResponseEntity<>("Subject deleted.", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Error when deleting object: %s" + e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>("Error when deleting object: %s" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestParam Long id, @RequestBody SchoolSubjectRequest request) {
        try{
            SchoolSubject updated = schoolSubjectService.update(id,FromRequestSchoolSubjectConverter.convert(request));
            return new ResponseEntity<>(ToDtoSchoolSubjectConverter.convert(updated), HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>("Error when updating object: %s" + e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>("Error when updating object: %s" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



    @GetMapping("/all")
    public ResponseEntity<List<SchoolSubjectDto>> getALL() {
        List<SchoolSubjectDto> subjects = ToDtoSchoolSubjectConverter.convert(schoolSubjectService.findAll());
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }

}
