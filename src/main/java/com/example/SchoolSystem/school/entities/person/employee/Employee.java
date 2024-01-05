package com.example.SchoolSystem.school.entities.person.employee;


import com.example.SchoolSystem.school.entities.person.Address;
import com.example.SchoolSystem.school.entities.person.PersonInformation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_information_id", referencedColumnName = "id")
    private PersonInformation personInformation;
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
    @Setter
    private float salary;

}
