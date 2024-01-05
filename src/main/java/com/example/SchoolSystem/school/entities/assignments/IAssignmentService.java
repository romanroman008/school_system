package com.example.SchoolSystem.school.entities.assignments;

import com.example.SchoolSystem.school.web.assignments.IAssignment;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface IAssignmentService {

    Map<String,List<String>> assign(List<? extends IAssignment> assignments);


}
