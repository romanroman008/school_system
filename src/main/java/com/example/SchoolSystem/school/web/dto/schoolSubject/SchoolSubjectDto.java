package com.example.SchoolSystem.school.web.dto.schoolSubject;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Map;

@Getter
@AllArgsConstructor
public class SchoolSubjectDto extends RepresentationModel<SchoolSubjectDto> {
    private Long id;
    private String name;
    private  Map<String, Integer> hoursPerWeek;

}
