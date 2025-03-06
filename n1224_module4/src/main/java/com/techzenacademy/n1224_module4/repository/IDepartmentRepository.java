package com.techzenacademy.n1224_module4.repository;

import com.techzenacademy.n1224_module4.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDepartmentRepository extends JpaRepository<Department, Integer> {
    Integer id(Integer id);
}