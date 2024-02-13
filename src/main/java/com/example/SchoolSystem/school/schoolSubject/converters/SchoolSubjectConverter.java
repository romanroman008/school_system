package com.example.SchoolSystem.school.schoolSubject.converters;

import com.example.SchoolSystem.school.schoolClass.Grade;
import com.example.SchoolSystem.school.schoolSubject.SchoolSubject;
import com.example.SchoolSystem.school.schoolSubject.SchoolSubjectDto;
import com.example.SchoolSystem.school.schoolSubject.SchoolSubjectRequest;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SchoolSubjectConverter {

    public List<SchoolSubject> fromRequest(List<SchoolSubjectRequest> requests){
        return requests.stream().map(this::fromRequest).toList();
    }

    public SchoolSubject fromRequest(SchoolSubjectRequest request){
        return new SchoolSubject(
                request.name(),
                this.convertToGradeMap(request.hoursPerWeekPerGrade())
        );
    }





    public List<SchoolSubjectDto> toDto(List<SchoolSubject> schoolSubjects){
        return schoolSubjects.stream().map(this::toDto).toList();
    }

    public SchoolSubjectDto toDto(SchoolSubject schoolSubject) {
        return new SchoolSubjectDto(
                schoolSubject.getId(),
                schoolSubject.getName(),
                convertFromGradeMap(schoolSubject.getHoursPerWeek())
        );
    }

    private Map<String, Integer> convertFromGradeMap(Map<Grade, Integer> hoursPerWeek) {
        return hoursPerWeek
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().toString(),
                        Map.Entry::getValue
                ));
    }

    private Map<Grade,Integer> convertToGradeMap(Map<String, Integer> dtoMap){
        return dtoMap
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> integerToGrade(Integer.parseInt(entry.getKey())),
                        Map.Entry::getValue
                ));
    }

    private Grade integerToGrade(int gradeNumber){
        Optional<Grade> grade = Grade.valueOf(gradeNumber);
        if(grade.isEmpty())
            throw new InvalidParameterException("Grade is beyond the range");
        return grade.get();
    }
}
