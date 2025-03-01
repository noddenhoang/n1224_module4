package com.techzenacademy.n1224_module4.service.impl;

import com.techzenacademy.n1224_module4.dto.EmployeeSearchRequest;
import com.techzenacademy.n1224_module4.model.Employee;
import com.techzenacademy.n1224_module4.repository.IEmployeeRepository;
import com.techzenacademy.n1224_module4.service.IEmployeeService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeService implements IEmployeeService {
    IEmployeeRepository employeeRepository;

    @Override
    public List<Employee> findByAttributes(EmployeeSearchRequest employeeSearchRequest) {
        return employeeRepository.findByAttributes(employeeSearchRequest);
    }

    @Override
    public Optional<Employee> findById(UUID id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void delete(UUID id) {
        employeeRepository.delete(id);
    }
}