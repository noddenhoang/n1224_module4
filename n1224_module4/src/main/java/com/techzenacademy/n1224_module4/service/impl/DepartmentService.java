package com.techzenacademy.n1224_module4.service.impl;

import com.techzenacademy.n1224_module4.model.Department;
import com.techzenacademy.n1224_module4.repository.IDepartmentRepository;
import com.techzenacademy.n1224_module4.service.IDepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DepartmentService implements IDepartmentService {
    IDepartmentRepository departmentRepository;

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public Optional<Department> findById(Integer departmentId) {
        return departmentRepository.findById(departmentId);
    }

    @Override
    public Department save(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public void delete(Integer departmentId) {
        departmentRepository.deleteById(departmentId);
    }
}
