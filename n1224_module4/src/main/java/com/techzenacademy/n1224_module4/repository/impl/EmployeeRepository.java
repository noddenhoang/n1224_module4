package com.techzenacademy.n1224_module4.repository.impl;

import com.techzenacademy.n1224_module4.dto.EmployeeSearchRequest;
import com.techzenacademy.n1224_module4.enums.Gender;
import com.techzenacademy.n1224_module4.model.Employee;
import com.techzenacademy.n1224_module4.repository.IEmployeeRepository;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class EmployeeRepository implements IEmployeeRepository {

    @Override
    public List<Employee> findByAttributes(EmployeeSearchRequest employeeSearchRequest) {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM employee";
        try (PreparedStatement preparedStatement = BaseRepository.getConnection().prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                employees.add(mapResultSetToEmployee(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employees;
    }

    @Override
    public Optional<Employee> findById(UUID id) {
        String query = "SELECT * FROM employee WHERE id = ?";
        try (PreparedStatement preparedStatement = BaseRepository.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, id.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapResultSetToEmployee(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Employee save(Employee employee) {
        String query = "INSERT INTO employee (id, name, dob, gender, salary, phone, department_id) VALUES (?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE name = VALUES(name), dob = VALUES(dob), gender = VALUES(gender), salary = VALUES(salary), phone = VALUES(phone), department_id = VALUES(department_id)";
        try (PreparedStatement preparedStatement = BaseRepository.getConnection().prepareStatement(query)) {
            if (employee.getId() == null) {
                employee.setId(UUID.randomUUID());
            }
            preparedStatement.setString(1, employee.getId().toString());
            preparedStatement.setString(2, employee.getName());
            preparedStatement.setDate(3, Date.valueOf(employee.getDob()));
            preparedStatement.setString(4, employee.getGender().toString());
            preparedStatement.setDouble(5, employee.getSalary());
            preparedStatement.setString(6, employee.getPhone());
            preparedStatement.setInt(7, employee.getDepartmentId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employee;
    }

    @Override
    public void delete(UUID id) {
        String query = "DELETE FROM employee WHERE id = ?";
        try (PreparedStatement preparedStatement = BaseRepository.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, id.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Employee mapResultSetToEmployee(ResultSet resultSet) throws SQLException {
        return new Employee(
                UUID.fromString(resultSet.getString("id")),
                resultSet.getString("name"),
                resultSet.getDate("dob").toLocalDate(),
                Gender.valueOf(resultSet.getString("gender")),
                resultSet.getDouble("salary"),
                resultSet.getString("phone"),
                resultSet.getInt("department_id")
        );
    }
}