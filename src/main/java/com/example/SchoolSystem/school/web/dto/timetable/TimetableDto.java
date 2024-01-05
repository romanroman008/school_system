package com.example.SchoolSystem.school.web.dto.timetable;


import com.example.SchoolSystem.school.web.dto.freePeriod.FreePeriodDto;
import com.example.SchoolSystem.school.web.dto.lesson.LessonDto;

import java.util.List;

public record TimetableDto(Long id, List<LessonDto> lessons, List<FreePeriodDto> freePeriods) {
}
