package com.techzenacademy.n1224_module4;

import com.techzenacademy.n1224_module4.exception.AppException;
import com.techzenacademy.n1224_module4.exception.ErrorCode;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final List<Employee> employees = new ArrayList<>(
            Arrays.asList(
                    new Employee(UUID.randomUUID(), "Thai Hoang Bao", LocalDate.of(1990, 10, 18), Gender.MALE, 15.00000, "1234567890", 1),
                    new Employee(UUID.randomUUID(), "Nguyen Minh Tuan", LocalDate.of(1988, 5, 21), Gender.OTHER, 20.00000, "0987654321", 2),
                    new Employee(UUID.randomUUID(), "Tran Thi Lan", LocalDate.of(1992, 3, 14), Gender.FEMALE, 18.50000, "0912345678", 3),
                    new Employee(UUID.randomUUID(), "Le Hoang Duy", LocalDate.of(1995, 11, 5), Gender.MALE, 22.00000, "0938123456", 4),
                    new Employee(UUID.randomUUID(), "Pham Thi Mai", LocalDate.of(1993, 8, 10), Gender.FEMALE, 17.50000, "0976543210", 1),
                    new Employee(UUID.randomUUID(), "Bui Quang Hieu", LocalDate.of(1991, 12, 1), Gender.MALE, 19.00000, "0908765432", 2),
                    new Employee(UUID.randomUUID(), "Nguyen Thi Thanh", LocalDate.of(1994, 4, 25), Gender.FEMALE, 16.50000, "0945612345", 3),
                    new Employee(UUID.randomUUID(), "Hoang Minh Tu", LocalDate.of(1996, 7, 30), Gender.MALE, 21.50000, "0956123456", 4),
                    new Employee(UUID.randomUUID(), "Vu Quoc Duy", LocalDate.of(1992, 6, 18), Gender.MALE, 20.50000, "0965432109", 1),
                    new Employee(UUID.randomUUID(), "Tran Thi Lan", LocalDate.of(1990, 10, 25), Gender.FEMALE, 17.00000, "0988765432", 1),
                    new Employee(UUID.randomUUID(), "Phan Hoang Tuan", LocalDate.of(1995, 9, 12), Gender.MALE, 19.50000, "0912387456", 1)
            )
    );

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "dobFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dobFrom,
            @RequestParam(value = "dobTo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dobTo,
            @RequestParam(value = "gender", required = false) Gender gender,
            @RequestParam(value = "salaryRange", required = false) String salaryRange,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "departmentId", required = false) Integer departmentId
    ) {
        List<Employee> filteredEmployees = employees.stream()
                .filter(e -> name == null || e.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(e -> dobFrom == null || e.getDob().isBefore(dobFrom))
                .filter(e -> dobTo == null || e.getDob().isAfter(dobTo))
                .filter(e -> gender == null || e.getGender() == gender)
                .filter(e -> phone == null || e.getPhone().contains(phone))
                .filter(e -> departmentId == null || Objects.equals(e.getDepartmentId(), departmentId))

                .filter(e -> {
                    if (salaryRange == null) {
                        return true;
                    }

                    return switch (salaryRange) {
                        case "<5" -> e.getSalary() < 5000000;
                        case "5-10" -> e.getSalary() >= 5000000 && e.getSalary() < 10000000;
                        case "10-20" -> e.getSalary() >= 10000000 && e.getSalary() <= 20000000;
                        case ">20" -> e.getSalary() > 20000000;
                        default -> true;
                    };
                })
                .collect(Collectors.toList());

        return JsonResponse.ok(filteredEmployees);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") UUID id) {
        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .map(JsonResponse::ok)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXIST));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Employee employee) {
        employee.setId(UUID.randomUUID());
        employees.add(employee);
        return JsonResponse.created(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody Employee employee) {
        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .map(e -> {
                    e.setName(employee.getName());
                    e.setDob(employee.getDob());
                    e.setGender(employee.getGender());
                    e.setSalary(employee.getSalary());
                    e.setPhone(employee.getPhone());
                    return JsonResponse.ok(e);
                })
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXIST));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .map(e -> {
                    employees.remove(e);
                    return JsonResponse.noContent();
                })
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXIST));
    }
}
