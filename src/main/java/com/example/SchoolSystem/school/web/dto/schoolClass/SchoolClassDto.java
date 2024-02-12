package com.example.SchoolSystem.school.web.dto.schoolClass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Map;


@AllArgsConstructor
@Getter
public class SchoolClassDto extends RepresentationModel<SchoolClassDto> {
    private Long id;
    private String totalGrade;
    private List<String> students;
    private  Map<String, String> subjectsWithTeachers;
}
