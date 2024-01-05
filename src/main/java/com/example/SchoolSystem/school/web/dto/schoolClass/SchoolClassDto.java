package com.example.SchoolSystem.school.web.dto.schoolClass;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;


public record SchoolClassDto (Long id, String totalGrade, List<String> students, Map<String, String> subjectsWithTeachers) {}
