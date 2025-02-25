package com.techzenacademy.n1224_module4;

import com.techzenacademy.n1224_module4.exception.AppException;
import com.techzenacademy.n1224_module4.exception.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final List<Department> departments = new ArrayList<>(
            Arrays.asList(
                    new Department(1, "Quản lý"),
                    new Department(2, "Kế toán"),
                    new Department(3, "Sale-Marketing"),
                    new Department(4, "Sản xuất")
            )
    );

    @GetMapping
    public ResponseEntity<?> getAll() {
        return JsonResponse.ok(departments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        return departments.stream()
                .filter(d -> d.getId() == id)
                .findFirst()
                .map(JsonResponse::ok)
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_EXIST));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Department department) {
        department.setId((int) (Math.random() * 1000000));
        departments.add(department);
        return JsonResponse.created(department);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody Department department) {
        return departments.stream()
                .filter(d -> d.getId() == id)
                .findFirst()
                .map(d -> {
                    d.setName(department.getName());
                    return JsonResponse.ok(d);
                })
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_EXIST));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        return departments.stream()
                .filter(d -> d.getId() == id)
                .findFirst()
                .map(d -> {
                    departments.remove(d);
                    return JsonResponse.noContent();
                })
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_EXIST));
    }
}

