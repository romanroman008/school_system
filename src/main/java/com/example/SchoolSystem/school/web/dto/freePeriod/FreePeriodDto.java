package com.example.SchoolSystem.school.web.dto.freePeriod;

import java.util.List;

public record FreePeriodDto(Long id, int number, String day, String schoolClass, List<String> availableTeachers) {
}
