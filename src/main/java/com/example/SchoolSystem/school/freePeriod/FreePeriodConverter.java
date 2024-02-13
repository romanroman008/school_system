package com.example.SchoolSystem.school.freePeriod;

import com.example.SchoolSystem.school.timetable.timetablePlainObjects.lesson.FreePeriod;

import java.util.List;

public class FreePeriodConverter {

    public static List<FreePeriodDto> toDto(List<FreePeriod> freePeriods) {
        return freePeriods.stream().map(FreePeriodConverter::toDto).toList();
    }

    public static FreePeriodDto toDto(FreePeriod freePeriod) {
        return new FreePeriodDto(
                freePeriod.getId(),
                freePeriod.getNumber(),
                freePeriod.getDay(),
                freePeriod.getSchoolClass(),
                freePeriod.getAvailableTeachers());
    }
}
