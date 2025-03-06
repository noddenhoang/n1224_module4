package com.techzenacademy.n1224_module4.repository.impl;

import com.techzenacademy.n1224_module4.dto.EmployeeSearchRequest;
import com.techzenacademy.n1224_module4.enums.Gender;
import com.techzenacademy.n1224_module4.model.Employee;
import com.techzenacademy.n1224_module4.repository.IEmployeeRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class EmployeeRepository implements IEmployeeRepository {

    @Override
    public List<Employee> findByAttributes(EmployeeSearchRequest request) {
        Session session = ConnectionUtil.sessionFactory.openSession();

        // Sử dụng HQL
        String hql = "FROM Employee e LEFT JOIN FETCH e.department WHERE "
                + "(:name IS NULL OR lower(e.name) LIKE CONCAT('%', :name, '%')) "
                + "AND (:dobFrom IS NULL OR e.dob >= :dobFrom) "
                + "AND (:dobTo IS NULL OR e.dob <= :dobTo) "
                + "AND (:gender IS NULL OR e.gender = :gender) "
                + "AND (:phone IS NULL OR e.phone LIKE CONCAT('%', :phone, '%')) "
                + "AND (:departmentId IS NULL OR e.department.id = :departmentId) ";

        // Xử lý salary range
        if (request.getSalaryRange() != null) {
            hql += "AND ("; // Mở đầu điều kiện salary range
            switch (request.getSalaryRange()) {
                case "lt5":
                    hql += "e.salary < 5000000";
                    break;
                case "5-10":
                    hql += "e.salary >= 5000000 AND e.salary < 10000000";
                    break;
                case "10-20":
                    hql += "e.salary >= 10000000 AND e.salary <= 20000000";
                    break;
                case "gt20":
                    hql += "e.salary > 20000000";
                    break;
            }
            hql += ")"; // Đóng điều kiện salary range
        }
        Query<Employee> query = session.createQuery(hql, Employee.class);
        // Đặt giá trị tham số
        query.setParameter("name", request.getName());
        query.setParameter("dobFrom", request.getDobFrom());
        query.setParameter("dobTo", request.getDobTo());
        query.setParameter("gender", request.getGender());
        query.setParameter("phone", request.getPhone());
        query.setParameter("departmentId", request.getDepartmentId());

        return query.getResultList();
    }

    @Override
    public Employee save(Employee employee) {
        Session session = ConnectionUtil.sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            if (employee.getId() == null) {
                employee.setId(UUID.randomUUID());
                session.save(employee);
            } else {
                session.update(employee);
            }

            transaction.commit();
            return employee;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error while saving employee", e);
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<Employee> findById(UUID id) {
        Session session = ConnectionUtil.sessionFactory.openSession();

        try {
            Employee employee = session.get(Employee.class, id);
            return Optional.ofNullable(employee);
        } catch (Exception e) {
            throw new RuntimeException("Error while finding employee by ID", e);
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(UUID id) {
        Session session = ConnectionUtil.sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Employee employee = session.get(Employee.class, id);
            if (employee != null) {
                session.delete(employee);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error while deleting employee", e);
        } finally {
            session.close();
        }
    }
}