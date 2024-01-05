package com.example.SchoolSystem.school;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.time.format.DateTimeFormatter;

@Configuration
@ConfigurationProperties(prefix = "app")
@Setter
public class AppConfig {


    private int maxAttemptsToFillFreePeriods;
    private int firstStudentYearAge;
    private int maxStudentsPerClass;
    private int maxHoursPerDay;
    private String dateTimeFormatPattern;


    public int getMaxAttemptsToFillFreePeriods() {
        return maxAttemptsToFillFreePeriods;
    }

    public int getFirstStudentYearAge() {
        return firstStudentYearAge;
    }

    public int getMaxStudentsPerClass() {
        return maxStudentsPerClass;
    }

    public int getMaxHoursPerDay() {
        return maxHoursPerDay;
    }


    public DateTimeFormatter getFormatter() {
        return DateTimeFormatter.ofPattern(dateTimeFormatPattern);
    }
}
