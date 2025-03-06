package com.techzenacademy.n1224_module4.repository.impl;

import com.techzenacademy.n1224_module4.model.Department;
import com.techzenacademy.n1224_module4.repository.IDepartmentRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DepartmentRepository implements IDepartmentRepository {

    @Override
    public List<Department> getAll() {
        Session session = ConnectionUtil.sessionFactory.openSession();
        List<Department> departmentList = null;
        try {
            departmentList = session.createQuery("from Department", Department.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return departmentList;
    }

    @Override
    public Optional<Department> findById(Integer departmentId) {
        Session session = ConnectionUtil.sessionFactory.openSession();
        Department department = null;
        try {
            department = session.get(Department.class, departmentId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return Optional.ofNullable(department);
    }

    @Override
    public Department save(Department department) {
        Session session = ConnectionUtil.sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            if (department.getId() == null) {
                // New department - use save which returns the generated ID
                session.save(department);
            } else {
                // Existing department - use saveOrUpdate
                session.saveOrUpdate(department);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Lỗi khi lưu phòng ban: " + e.getMessage(), e);
        } finally {
            session.close();
        }
        return department;
    }

    @Override
    public void deleteDepartment(Integer departmentId) {
        Session session = ConnectionUtil.sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            Department department = session.get(Department.class, departmentId);
            if (department != null) {
                session.delete(department);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Lỗi khi xóa phòng ban: " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }
}