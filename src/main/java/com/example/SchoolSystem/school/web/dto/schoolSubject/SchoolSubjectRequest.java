package com.example.SchoolSystem.school.web.dto.schoolSubject;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;


import java.util.Map;


public record SchoolSubjectRequest(String name, Map<String, Integer> hoursPerWeekPerGrade) {
}
