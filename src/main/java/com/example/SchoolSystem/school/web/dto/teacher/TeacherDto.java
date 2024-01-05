package com.example.SchoolSystem.school.web.dto.teacher;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


public record TeacherDto(Long id, String firstName, String lastName, List<String> teachingSubjects, List<String> teachingClasses) {
}

