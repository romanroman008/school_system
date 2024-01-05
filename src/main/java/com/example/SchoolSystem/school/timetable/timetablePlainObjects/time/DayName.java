package com.example.SchoolSystem.school.timetable.timetablePlainObjects.time;

import org.springframework.stereotype.Component;


public enum DayName {
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5);
    private final int value;

    DayName(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
