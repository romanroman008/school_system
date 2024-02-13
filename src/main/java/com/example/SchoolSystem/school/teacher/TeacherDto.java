package com.example.SchoolSystem.school.teacher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;


@Getter
@AllArgsConstructor
public class TeacherDto extends RepresentationModel<TeacherDto> {

    private Long id;
    private String firstName;
    private String lastName;
    private List<String> teachingSubjects;
    private List<String> teachingClasses;
}

