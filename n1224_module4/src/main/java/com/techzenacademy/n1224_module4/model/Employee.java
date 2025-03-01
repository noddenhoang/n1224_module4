package com.techzenacademy.n1224_module4.model;

import com.techzenacademy.n1224_module4.enums.Gender;
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
public class Employee {
    UUID id;
    String name;
    LocalDate dob;
    Gender gender;
    Double salary;
    String phone;
    Integer departmentId;
}