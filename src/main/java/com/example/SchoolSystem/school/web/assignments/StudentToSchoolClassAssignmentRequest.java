package com.example.SchoolSystem.school.web.assignments;

import lombok.AllArgsConstructor;
import lombok.Getter;


public record StudentToSchoolClassAssignmentRequest( Long studentId, Long schoolClassId) implements IAssignment{


}
