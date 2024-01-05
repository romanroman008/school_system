package com.example.SchoolSystem.school.web.dto.schoolSubject.converters;

import com.example.SchoolSystem.school.entities.schoolClass.Grade;
import com.example.SchoolSystem.school.entities.schoolSubject.SchoolSubject;
import com.example.SchoolSystem.school.web.dto.schoolSubject.SchoolSubjectDto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ToDtoSchoolSubjectConverter {

    public static List<SchoolSubjectDto> convert(List<SchoolSubject> schoolSubjects){
        return schoolSubjects.stream().map(ToDtoSchoolSubjectConverter::convert).toList();
    }

    public static SchoolSubjectDto convert(SchoolSubject schoolSubject) {
        return new SchoolSubjectDto(
                schoolSubject.getId(),
                schoolSubject.getName(),
                convertHoursPerWeekMap(schoolSubject.getHoursPerWeek())
        );
    }

    private static Map<String, Integer> convertHoursPerWeekMap(Map<Grade, Integer> hoursPerWeek) {
        return hoursPerWeek
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().toString(),
                        Map.Entry::getValue
                ));
    }
}
