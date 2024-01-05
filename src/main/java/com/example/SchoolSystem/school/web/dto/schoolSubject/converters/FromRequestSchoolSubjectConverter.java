package com.example.SchoolSystem.school.web.dto.schoolSubject.converters;

import com.example.SchoolSystem.school.entities.schoolClass.Grade;
import com.example.SchoolSystem.school.entities.schoolSubject.SchoolSubject;
import com.example.SchoolSystem.school.web.dto.schoolSubject.SchoolSubjectRequest;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class FromRequestSchoolSubjectConverter {

    public static List<SchoolSubject> convert(List<SchoolSubjectRequest> requests){
        return requests.stream().map(FromRequestSchoolSubjectConverter::convert).toList();
    }

    public static SchoolSubject convert(SchoolSubjectRequest request){
        return new SchoolSubject(
                request.name(),
                convertHoursPerWeekMap(request.hoursPerWeekPerGrade())
        );
    }

    private static Map<Grade,Integer> convertHoursPerWeekMap(Map<String, Integer> dtoMap){
        return dtoMap
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                entry -> integerToGrade(Integer.parseInt(entry.getKey())),
                        Map.Entry::getValue
        ));
    }

    private static Grade integerToGrade(int gradeNumber){
        Optional<Grade> grade = Grade.valueOf(gradeNumber);
        if(grade.isEmpty())
            throw new InvalidParameterException("Grade is beyond the range");
        return grade.get();
    }
}
