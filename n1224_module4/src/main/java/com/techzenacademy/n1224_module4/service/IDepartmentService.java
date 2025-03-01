package com.techzenacademy.n1224_module4.service;

import com.techzenacademy.n1224_module4.model.Department;

import java.util.List;
import java.util.Optional;

public interface IDepartmentService {
    List<Department> getAll();

    Optional<Department> findById(Integer departmentId);

    Department save(Department department);

    void deleteDepartment(Integer departmentId);
}
