package com.techzenacademy.n1224_module4.controller;

import com.techzenacademy.n1224_module4.util.JsonResponse;
import com.techzenacademy.n1224_module4.exception.AppException;
import com.techzenacademy.n1224_module4.exception.ErrorCode;
import com.techzenacademy.n1224_module4.model.Department;
import com.techzenacademy.n1224_module4.service.IDepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
@AllArgsConstructor
public class DepartmentController {
    private final IDepartmentService departmentService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return JsonResponse.ok(departmentService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        return departmentService.findById(id)
                .map(JsonResponse::ok)
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_EXIST));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Department department) {
        return JsonResponse.ok(departmentService.save(department));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody Department department) {
        departmentService.findById(department.getId())
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_EXIST));

        departmentService.deleteDepartment(department.getId());
        return JsonResponse.noContent();
    }
}
