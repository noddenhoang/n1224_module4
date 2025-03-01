package com.techzenacademy.n1224_module4.repository.impl;

import com.techzenacademy.n1224_module4.model.Department;
import com.techzenacademy.n1224_module4.repository.IDepartmentRepository;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DepartmentRepository implements IDepartmentRepository {

    @Override
    public List<Department> getAll() {
        List<Department> departments = new ArrayList<>();
        String query = "SELECT * FROM department";
        try (PreparedStatement preparedStatement = BaseRepository.getConnection().prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                departments.add(mapResultSetToDepartment(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy danh sách phòng ban: " + e.getMessage(), e);
        }
        return departments;
    }

    @Override
    public Optional<Department> findById(Integer departmentId) {
        String query = "SELECT * FROM department WHERE id = ?";
        try (PreparedStatement preparedStatement = BaseRepository.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, departmentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapResultSetToDepartment(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tìm phòng ban: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Department save(Department department) {
        String query = "INSERT INTO department (id, name) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE name = VALUES(name)";
        try (PreparedStatement preparedStatement = BaseRepository.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            if (department.getId() == null) {
                preparedStatement.setNull(1, Types.INTEGER);
            } else {
                preparedStatement.setInt(1, department.getId());
            }
            preparedStatement.setString(2, department.getName());

            preparedStatement.executeUpdate();

            // Lấy ID tự động tăng nếu chưa có
            if (department.getId() == null) {
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    department.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lưu phòng ban: " + e.getMessage(), e);
        }
        return department;
    }

    @Override
    public void deleteDepartment(Integer departmentId) {
        String query = "DELETE FROM department WHERE id = ?";
        try (PreparedStatement preparedStatement = BaseRepository.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, departmentId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi xóa phòng ban: " + e.getMessage(), e);
        }
    }

    private Department mapResultSetToDepartment(ResultSet resultSet) throws SQLException {
        return new Department(
                resultSet.getInt("id"),
                resultSet.getString("name")
        );
    }
}
