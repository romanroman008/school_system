package com.example.SchoolSystem.school.student;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@AllArgsConstructor
public class StudentDto extends RepresentationModel<StudentDto>{
    private Long id;
    private String firstName;
    private String lastName;
    private String className;

}
