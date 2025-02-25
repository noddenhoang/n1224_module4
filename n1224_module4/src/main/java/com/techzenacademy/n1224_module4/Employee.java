package com.techzenacademy.n1224_module4;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Employee {
    private UUID id;
    private String name;
    private LocalDate dob;
    private Gender gender;
    private Double salary;
    private String phone;
}
