package com.techzenacademy.n1224_module4.repository;

import com.techzenacademy.n1224_module4.model.Department;

import java.util.List;
import java.util.Optional;

public interface IDepartmentRepository {
    List<Department> getAll();

    Optional<Department> findById(Integer departmentId);

    Department save(Department department);

    void deleteDepartment(Integer departmentId);
}
