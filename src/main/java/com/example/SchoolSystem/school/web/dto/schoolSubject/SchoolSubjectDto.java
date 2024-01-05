package com.example.SchoolSystem.school.web.dto.schoolSubject;


import java.util.Map;


public record SchoolSubjectDto(Long id, String name , Map<String, Integer> hoursPerWeek) { }
