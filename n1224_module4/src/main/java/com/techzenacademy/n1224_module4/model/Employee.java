package com.techzenacademy.n1224_module4.model;

import com.techzenacademy.n1224_module4.enums.Gender;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

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
    @Column(columnDefinition = "BINARY(16)")
    UUID id;
    String name;
    LocalDate dob;
    @Column(columnDefinition = "ENUM ('MALE', 'FEMALE', 'OTHER')")
    @Enumerated(EnumType.STRING)
    Gender gender;
    Double salary;
    @Column(columnDefinition = "char(10)")
    String phone;
    @ManyToOne
    Department department;
}