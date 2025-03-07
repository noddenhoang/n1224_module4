package com.techzenacademy.n1224_module4.repository;

import com.techzenacademy.n1224_module4.enums.Gender;
import com.techzenacademy.n1224_module4.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface IEmployeeRepository extends JpaRepository<Employee, UUID> {

    @Query(value = """
        SELECT e FROM Employee e
        LEFT JOIN Department d ON e.department.id = d.id
        WHERE (:name IS NULL OR e.name LIKE CONCAT('%', :name, '%'))
        AND (:dobFrom IS NULL OR e.dob >= :dobFrom)
        AND (:phone IS NULL OR e.phone LIKE CONCAT('%', :phone, '%'))
        AND (:departmentId IS NULL OR d.id = :departmentId)
        AND (
            CASE
                WHEN :salaryRange = 'lt5' THEN e.salary < 5000000
                WHEN :salaryRange = '5-10' THEN e.salary BETWEEN 5000000 AND 10000000
                WHEN :salaryRange = '10-20' THEN e.salary BETWEEN 10000000 AND 20000000
                WHEN :salaryRange = 'gt20' THEN e.salary > 20000000
                ELSE TRUE
            END
        )
        """, countQuery = """
        SELECT COUNT(e) FROM Employee e
        LEFT JOIN Department d ON e.department.id = d.id
        WHERE (:name IS NULL OR e.name LIKE CONCAT('%', :name, '%'))
        AND (:dobFrom IS NULL OR e.dob >= :dobFrom)
        AND (:dobTo IS NULL OR e.dob <= :dobTo)
        AND (:phone IS NULL OR e.phone LIKE CONCAT('%', :phone, '%'))
        AND (:departmentId IS NULL OR d.id = :departmentId)
        AND (
            CASE
                WHEN :salaryRange = 'lt5' THEN e.salary < 5000000
                WHEN :salaryRange = '5-10' THEN e.salary BETWEEN 5000000 AND 10000000
                WHEN :salaryRange = '10-20' THEN e.salary BETWEEN 10000000 AND 20000000
                WHEN :salaryRange = 'gt20' THEN e.salary > 20000000
                ELSE TRUE
            END
        )
        """, nativeQuery = false)
    Page<Employee> findByAttributes(
            @Param("name") String name,
            @Param("dobFrom") LocalDate dobFrom,
            @Param("dobTo") LocalDate dobTo,
            @Param("gender") Gender gender,
            @Param("salaryRange") String salaryRange,
            @Param("phone") String phone,
            @Param("departmentId") Integer departmentId,
            Pageable pageable
    );
}