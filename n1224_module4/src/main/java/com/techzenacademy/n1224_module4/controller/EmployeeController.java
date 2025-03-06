package com.techzenacademy.n1224_module4.controller;

import com.techzenacademy.n1224_module4.dto.EmployeeSearchRequest;
import com.techzenacademy.n1224_module4.service.IEmployeeService;
import com.techzenacademy.n1224_module4.util.JsonResponse;
import com.techzenacademy.n1224_module4.exception.AppException;
import com.techzenacademy.n1224_module4.exception.ErrorCode;
import com.techzenacademy.n1224_module4.model.Employee;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/employees")
public class EmployeeController {
    IEmployeeService employeeService;

    @GetMapping
    public ResponseEntity<?> getAll(EmployeeSearchRequest employeeSearchRequest) {
        return JsonResponse.ok(employeeService.findByAttributes(employeeSearchRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") UUID id) {
        return employeeService.findById(id)
                .map(JsonResponse::ok)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXIST));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Employee employee) {
        return JsonResponse.created(employeeService.save(employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody Employee employee) {
        employeeService.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXIST));

        employee.setId(id);
        return JsonResponse.ok(employeeService.save(employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        employeeService.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXIST));

        employeeService.delete(id);
        return ResponseEntity.ok(JsonResponse.ok("Employee deleted successfully"));
    }

}