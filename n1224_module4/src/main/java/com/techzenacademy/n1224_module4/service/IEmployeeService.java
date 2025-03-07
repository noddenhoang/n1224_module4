package com.techzenacademy.n1224_module4.service;

import com.techzenacademy.n1224_module4.dto.EmployeeSearchRequest;
import com.techzenacademy.n1224_module4.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IEmployeeService {
    Page<Employee> findByAttributes(EmployeeSearchRequest employeeSearchRequest, Pageable pageable);
    Optional<Employee> findById(UUID id);
    Employee save(Employee employee);
    void delete(UUID id);
}