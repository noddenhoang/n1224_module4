package com.techzenacademy.n1224_module4.repository.impl;

import com.techzenacademy.n1224_module4.dto.EmployeeSearchRequest;
import com.techzenacademy.n1224_module4.enums.Gender;
import com.techzenacademy.n1224_module4.model.Employee;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class EmployeeRepository {

    public List<Employee> findByAttributes(EmployeeSearchRequest employeeSearchRequest) {
        List<Employee> employeeList = new ArrayList<>();
        String query = "SELECT id, name, dob, gender, salary, phone, department_id " +
                "FROM employee WHERE 1=1";

        List<Object> parameters = new ArrayList<>();

        if (employeeSearchRequest.getName() != null) {
            query += " AND LOWER(name) LIKE ?";
            parameters.add("%" + employeeSearchRequest.getName().toLowerCase() + "%");
        }

        if (employeeSearchRequest.getDepartmentId() != null) {
            query += " AND department_id = ?";
            parameters.add(employeeSearchRequest.getDepartmentId());
        }

        if (employeeSearchRequest.getSalaryRange() != null) {
            switch (employeeSearchRequest.getSalaryRange()) {
                case "lt5":
                    query += " AND salary < 500000";
                    break;
                case "5-10":
                    query += " AND salary >= 500000 AND salary < 1000000";
                    break;
                case "10-20":
                    query += " AND salary >= 1000000 AND salary <= 2000000";
                    break;
                case "gt20":
                    query += " AND salary > 2000000";
                    break;
            }
        }

        try (PreparedStatement preparedStatement = BaseRepository.getConnection().prepareStatement(query)) {
            // Set parameters dynamically
            for (int i = 0; i < parameters.size(); i++) {
                Object param = parameters.get(i);
                if (param instanceof LocalDate) {
                    preparedStatement.setDate(i + 1, Date.valueOf((LocalDate) param));
                } else if (param instanceof Integer) {
                    preparedStatement.setInt(i + 1, (Integer) param);
                } else {
                    preparedStatement.setObject(i + 1, param);
                }
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Employee employee = new Employee();
                    employee.setId(UUID.fromString(resultSet.getString("id")));
                    employee.setName(resultSet.getString("name"));
                    employee.setDob(resultSet.getDate("dob").toLocalDate());
                    employee.setGender(Gender.valueOf(resultSet.getString("gender")));
                    employee.setSalary(resultSet.getDouble("salary"));
                    employee.setPhone(resultSet.getString("phone"));
                    employee.setDepartmentId(resultSet.getInt("department_id"));
                    employeeList.add(employee);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while searching employees", e);
        }

        return employeeList;
    }

    public Employee save(Employee employee) {
        String updateQuery = "UPDATE employee SET name = ?, dob = ?, gender = ?, salary = ?, phone = ?, department_id = ? WHERE id = ?";
        String insertQuery = "INSERT INTO employee (id, name, dob, gender, salary, phone, department_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            Optional<Employee> existingEmployee = findById(employee.getId());

            if (existingEmployee.isPresent()) {
                // Update existing employee
                try (PreparedStatement preparedStatement = BaseRepository.getConnection().prepareStatement(updateQuery)) {
                    preparedStatement.setString(1, employee.getName());
                    preparedStatement.setDate(2, Date.valueOf(employee.getDob()));
                    preparedStatement.setString(3, employee.getGender().name());
                    preparedStatement.setDouble(4, employee.getSalary());
                    preparedStatement.setString(5, employee.getPhone());
                    preparedStatement.setInt(6, employee.getDepartmentId());
                    preparedStatement.setString(7, employee.getId().toString());
                    preparedStatement.executeUpdate();
                }
            } else {
                // Insert new employee
                employee.setId(UUID.randomUUID());
                try (PreparedStatement preparedStatement = BaseRepository.getConnection().prepareStatement(insertQuery)) {
                    preparedStatement.setString(1, employee.getId().toString());
                    preparedStatement.setString(2, employee.getName());
                    preparedStatement.setDate(3, Date.valueOf(employee.getDob()));
                    preparedStatement.setString(4, employee.getGender().name());
                    preparedStatement.setDouble(5, employee.getSalary());
                    preparedStatement.setString(6, employee.getPhone());
                    preparedStatement.setInt(7, employee.getDepartmentId());
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while saving employee to the database", e);
        }

        return employee;
    }

    public Optional<Employee> findById(UUID id) {
        String query = "SELECT id, name, dob, gender, salary, phone, department_id FROM employee WHERE id = ?";

        try (PreparedStatement preparedStatement = BaseRepository.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, id.toString());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Employee employee = new Employee();
                    employee.setId(UUID.fromString(resultSet.getString("id")));
                    employee.setName(resultSet.getString("name"));
                    employee.setDob(resultSet.getDate("dob").toLocalDate());
                    employee.setGender(Gender.valueOf(resultSet.getString("gender")));
                    employee.setSalary(resultSet.getDouble("salary"));
                    employee.setPhone(resultSet.getString("phone"));
                    employee.setDepartmentId(resultSet.getInt("department_id"));
                    return Optional.of(employee);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding employee by ID", e);
        }

        return Optional.empty();
    }
}