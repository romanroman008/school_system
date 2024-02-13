package com.example.SchoolSystem.school.timetable;


import com.example.SchoolSystem.school.freePeriod.FreePeriodDto;
import com.example.SchoolSystem.school.lesson.LessonDto;

import java.util.List;

public record TimetableDto(Long id, List<LessonDto> lessons, List<FreePeriodDto> freePeriods) {
}
