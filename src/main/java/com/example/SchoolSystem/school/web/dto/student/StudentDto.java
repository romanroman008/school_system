package com.example.SchoolSystem.school.web.dto.student;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@AllArgsConstructor
public class StudentDto extends RepresentationModel<StudentDto>{
    Long id;
    String firstName;
    String lastName;
    String className;

}
