package com.techzenacademy.n1224_module4;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final List<Employee> employees = new ArrayList<>(
            Arrays.asList(
                    new Employee(UUID.randomUUID(), "Thai Hoang Bao", LocalDate.of(1990, 10, 18), Gender.MALE, 15.00000, "1234567890"),
                    new Employee(UUID.randomUUID(), "Nguyen Minh Tuan", LocalDate.of(1988, 5, 21), Gender.OTHER, 20.00000, "0987654321"),
                    new Employee(UUID.randomUUID(), "Tran Thi Lan", LocalDate.of(1992, 3, 14), Gender.FEMALE, 18.50000, "0912345678"),
                    new Employee(UUID.randomUUID(), "Le Hoang Duy", LocalDate.of(1995, 11, 5), Gender.MALE, 22.00000, "0938123456"),
                    new Employee(UUID.randomUUID(), "Pham Thi Mai", LocalDate.of(1993, 8, 10), Gender.FEMALE, 17.50000, "0976543210"),
                    new Employee(UUID.randomUUID(), "Bui Quang Hieu", LocalDate.of(1991, 12, 1), Gender.MALE, 19.00000, "0908765432"),
                    new Employee(UUID.randomUUID(), "Nguyen Thi Thanh", LocalDate.of(1994, 4, 25), Gender.FEMALE, 16.50000, "0945612345"),
                    new Employee(UUID.randomUUID(), "Hoang Minh Tu", LocalDate.of(1996, 7, 30), Gender.MALE, 21.50000, "0956123456"),
                    new Employee(UUID.randomUUID(), "Vu Quoc Duy", LocalDate.of(1992, 6, 18), Gender.MALE, 20.50000, "0965432109"),
                    new Employee(UUID.randomUUID(), "Tran Thi Lan", LocalDate.of(1990, 10, 25), Gender.FEMALE, 17.00000, "0988765432"),
                    new Employee(UUID.randomUUID(), "Phan Hoang Tuan", LocalDate.of(1995, 9, 12), Gender.MALE, 19.50000, "0912387456")
            )
    );

    @GetMapping()
    public ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable("id") UUID id) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        employee.setId(UUID.randomUUID());
        employees.add(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable("id") UUID id, @RequestBody Employee employee) {
        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .map(e -> {
                    e.setName(employee.getName());
                    e.setDob(employee.getDob());
                    e.setGender(employee.getGender());
                    e.setSalary(employee.getSalary());
                    e.setPhone(employee.getPhone());
                    return ResponseEntity.ok(e);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .map(e -> {
                    employees.remove(e);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
