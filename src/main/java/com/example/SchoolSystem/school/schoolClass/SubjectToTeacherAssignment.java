package com.example.SchoolSystem.school.schoolClass;


import com.example.SchoolSystem.school.teacher.Teacher;
import com.example.SchoolSystem.school.schoolSubject.SchoolSubject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name ="subject_to_teacher_assignment")
public class SubjectToTeacherAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private SchoolSubject schoolSubject;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;
    private boolean isAssigned;

    public SubjectToTeacherAssignment(SchoolSubject schoolSubject) {
        this.schoolSubject = schoolSubject;
        isAssigned = false;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
        isAssigned = true;
    }

    public void removeTeacher(){
        this.teacher = null;
        isAssigned = false;
    }
}
