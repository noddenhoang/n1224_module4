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
    private final IDepartmentRepository departmentRepository;

    @Override
    public List<Department> getAll() {
        return departmentRepository.getAll();
    }

    @Override
    public Optional<Department> findById(Integer id) {
        return departmentRepository.findById(id);
    }

    @Override
    public Department save(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public void deleteDepartment(Integer id) {
        departmentRepository.deleteDepartment(id);
    }
}
