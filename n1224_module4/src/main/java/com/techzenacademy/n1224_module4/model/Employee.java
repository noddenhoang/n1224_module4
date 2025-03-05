package com.techzenacademy.n1224_module4.model;

import com.techzenacademy.n1224_module4.enums.Gender;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Employee {
    @Id
    @GeneratedValue(generator = "UUID")
    UUID id;
    String name;
    LocalDate dob;
    @Column(columnDefinition = "ENUM('MALE', 'FEMALE')")
    @Enumerated(EnumType.STRING)
    Gender gender;
    Double salary;
    String phone;
    @ManyToOne(fetch = FetchType.EAGER)
    Department department;
}