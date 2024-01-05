package com.example.SchoolSystem.school.entities.schoolSubject.service;

import com.example.SchoolSystem.school.entities.assignments.TeachersToSubjectsAssignmentStrategyImpl;
import com.example.SchoolSystem.school.entities.person.teacher.service.ITeacherService;
import com.example.SchoolSystem.school.entities.schoolClass.service.ISchoolClassService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TeachersToSubjectsAssignmentStrategyImplTest {

    @Mock
    ISchoolClassService schoolClassService;
    @Mock
    ISchoolSubjectService schoolSubjectService;
    @Mock
    ITeacherService teacherService;

    @InjectMocks
    TeachersToSubjectsAssignmentStrategyImpl schoolSubjectAssignmentService;

    @Nested
    class assignToClasses{


    }



}