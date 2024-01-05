package com.example.SchoolSystem.school.entities.person;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
//@Table(name ="address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int houseNumber;
    private int flatNumber;
    private String street;
    private String city;



    public Address(Builder builder) {
        this.houseNumber = builder.houseNumber;
        this.flatNumber = builder.flatNumber;
        this.street = builder.street;
        this.city = builder.city;
    }

    public static class Builder {
        private int houseNumber;
        private int flatNumber;
        private String street;
        private String city;

        public Builder() {
        }

        ;

        public Builder setHouseNumber(int houseNumber) {
            this.houseNumber = houseNumber;
            return this;
        }

        public Builder setFlatNumber(int flatNumber) {
            this.flatNumber = flatNumber;
            return this;
        }

        public Builder setStreet(String street) {
            this.street = street;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }
}
