package com.techzenacademy.n1224_module4.service;

import com.techzenacademy.n1224_module4.model.Department;

import java.util.List;
import java.util.Optional;

public interface IDepartmentService {
    List<Department> findAll();
    Optional<Department> findById(Integer id);
    Department save(Department department);
    void delete(Integer departmentId);
}