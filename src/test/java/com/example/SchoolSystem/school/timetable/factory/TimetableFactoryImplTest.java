package com.example.SchoolSystem.school.timetable.factory;

import com.example.SchoolSystem.school.entities.schoolClass.Grade;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.Timetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.schoolClass.ClassTimetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.subject.SubjectTimetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.teacher.TeacherTimetable;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TimetableFactoryImplTest {

    ITimetableFactory factory;
    Timetable timetable;

    List<String> names = List.of(
            "Arnold",
            "Kieran",
            "Horacy",
            "Filemon",
            "Gerwazy",
            "Ponfacy",
            "Pankracy",
            "Ojeciec Pijo",
            "Bonifacy",
            "Alojzy",
            "Hiacynty",
            "Eugeniusz",
            "Ernest",
            "Debora",
            "Estera",
            "Esperal",
            "Damazy",
            "T-1000",
            "Saruman",
            "Tyler Durden",
            "Andrea Bocelli",
            "Lucian Pavarotti");  //23

    private List<TeacherTimetable> createTeacherList(int amount) {
        List<TeacherTimetable> teachers = new ArrayList<>();
        for (int i = 0; i < amount; i++){
            teachers.add(new TeacherTimetable(i,names.get(i),40));
        }
        return teachers;
    }


    private List<ClassTimetable> createClassList() {

        List<ClassTimetable> classes = new ArrayList<>(List.of(
//                new ClassTimetable("IA", Grade.I, createSubjectList()),
//                new ClassTimetable("IB", Grade.I, createSubjectList()),
//                new ClassTimetable("IC", Grade.I, createSubjectList()),
//                new ClassTimetable("ID", Grade.I, createSubjectList()),
//                new ClassTimetable("IIA", Grade.II, createSubjectList()),
//                new ClassTimetable("IIB", Grade.II, createSubjectList()),
//                new ClassTimetable("IIC", Grade.II, createSubjectList()),
//                new ClassTimetable("IID", Grade.II, createSubjectList()),
//                new ClassTimetable("IIIA", Grade.III, createSubjectList()),
//                new ClassTimetable("IIIB", Grade.III, createSubjectList()),
//                new ClassTimetable("IIIC", Grade.III, createSubjectList()),
//                new ClassTimetable("IIID", Grade.III, createSubjectList()),
//                new ClassTimetable("IVA", Grade.IV, createSubjectList()),
//                new ClassTimetable("IVB", Grade.IV, createSubjectList()),
//                new ClassTimetable("IVC", Grade.IV, createSubjectList()),
//                new ClassTimetable("IVD", Grade.IV, createSubjectList()),
//                new ClassTimetable("VA", Grade.V, createSubjectList()),
//                new ClassTimetable("VB", Grade.V, createSubjectList()),
//                new ClassTimetable("VC", Grade.V, createSubjectList()),
//                new ClassTimetable("VD", Grade.V, createSubjectList()),
//                new ClassTimetable("VIA", Grade.VI, createSubjectList()),
//                new ClassTimetable("VIB", Grade.VI, createSubjectList()),
//                new ClassTimetable("VIC", Grade.VI, createSubjectList()),
//                new ClassTimetable("VID", Grade.VI, createSubjectList()),
//                new ClassTimetable("VIIA", Grade.VII, createSubjectList()),
//                new ClassTimetable("VIIB", Grade.VII, createSubjectList()),
//                new ClassTimetable("VIIC", Grade.VII, createSubjectList()),
//                new ClassTimetable("VIID", Grade.VII, createSubjectList()),
//                new ClassTimetable("VIIIA", Grade.VIII, createSubjectList()),
//                new ClassTimetable("VIIIB", Grade.VIII, createSubjectList()),
//                new ClassTimetable("VIIIC", Grade.VIII, createSubjectList()),
//                new ClassTimetable("VIIID", Grade.VIII, createSubjectList())
        ));

        return classes;
    }


    @Nested
    class MaxAmountClassesTests{

        void setUp(){

        }

    }

}