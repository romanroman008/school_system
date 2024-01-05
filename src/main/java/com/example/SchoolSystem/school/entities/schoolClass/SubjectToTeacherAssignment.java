package com.example.SchoolSystem.school.entities.schoolClass;


import com.example.SchoolSystem.school.entities.person.teacher.Teacher;
import com.example.SchoolSystem.school.entities.schoolSubject.SchoolSubject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name ="sranie_mapowanie")
public class SubjectToTeacherAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "subjecto_id", referencedColumnName = "id")
    private SchoolSubject schoolSubject;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "teachero_id", referencedColumnName = "id")
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
