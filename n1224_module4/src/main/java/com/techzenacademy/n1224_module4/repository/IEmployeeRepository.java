package com.techzenacademy.n1224_module4.repository;

import com.techzenacademy.n1224_module4.dto.EmployeeSearchRequest;
import com.techzenacademy.n1224_module4.model.Employee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IEmployeeRepository {
    List<Employee> findByAttributes(EmployeeSearchRequest employeeSearchRequest);
    Optional<Employee> findById(UUID id);
    Employee save(Employee employee);
    void delete(UUID id);
}
