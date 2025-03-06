-- Tạo database nếu chưa có
CREATE DATABASE IF NOT EXISTS employee_mana;
USE employee_mana;

-- Xóa bảng nếu đã tồn tại để tránh lỗi
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS department;

-- Tạo bảng department trước
CREATE TABLE department
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
);

-- Tạo bảng employee (UUID lưu dạng BINARY(16))
CREATE TABLE employee
(
    id            BINARY(16) PRIMARY KEY,  -- UUID
    name          VARCHAR(50) NOT NULL,
    dob           DATE NOT NULL,
    gender        ENUM ('MALE', 'FEMALE', 'OTHER') NOT NULL,
    salary        DOUBLE NOT NULL,
    phone         VARCHAR(15) NOT NULL,
    department_id INT,
    FOREIGN KEY (department_id) REFERENCES department (id) ON DELETE SET NULL
);

-- Thêm dữ liệu vào bảng department
INSERT INTO department (name)
VALUES ('Quản lý'),
       ('Kế toán'),
       ('Sale-Marketing'),
       ('Sản xuất');

-- Kiểm tra dữ liệu bảng department
SELECT * FROM department;

-- Thêm dữ liệu vào bảng employee với UUID được chuyển sang BINARY(16)
INSERT INTO employee (id, name, dob, gender, salary, phone, department_id)
VALUES (UNHEX(REPLACE(UUID(), '-', '')), 'Thai Hoang Bao', '1990-10-18', 'MALE', 15000000.0, '1234567890', 1),
       (UNHEX(REPLACE(UUID(), '-', '')), 'Nguyen Minh Tuan', '1988-05-21', 'OTHER', 20000000.0, '0987654321', 2),
       (UNHEX(REPLACE(UUID(), '-', '')), 'Tran Thi Lan', '1992-03-14', 'FEMALE', 18500000.0, '0912345678', 3),
       (UNHEX(REPLACE(UUID(), '-', '')), 'Le Hoang Duy', '1995-11-05', 'MALE', 22000000.0, '0938123456', 4),
       (UNHEX(REPLACE(UUID(), '-', '')), 'Pham Thi Mai', '1993-08-10', 'FEMALE', 17500000.0, '0976543210', 1),
       (UNHEX(REPLACE(UUID(), '-', '')), 'Bui Quang Hieu', '1991-12-01', 'MALE', 19000000.0, '0908765432', 2),
       (UNHEX(REPLACE(UUID(), '-', '')), 'Nguyen Thi Thanh', '1994-04-25', 'FEMALE', 16500000.0, '0945612345', 3),
       (UNHEX(REPLACE(UUID(), '-', '')), 'Hoang Minh Tu', '1996-07-30', 'MALE', 21500000.0, '0956123456', 4),
       (UNHEX(REPLACE(UUID(), '-', '')), 'Vu Quoc Duy', '1992-06-18', 'MALE', 20500000.0, '0965432109', 1),
       (UNHEX(REPLACE(UUID(), '-', '')), 'Tran Thi Lan', '1990-10-25', 'FEMALE', 17000000.0, '0988765432', 1),
       (UNHEX(REPLACE(UUID(), '-', '')), 'Phan Hoang Tuan', '1995-09-12', 'MALE', 19500000.0, '0912387456', 1);

-- Kiểm tra dữ liệu bảng employee
SELECT * FROM employee;

-- Tìm kiếm employee theo UUID (phải convert từ string sang BINARY)
SELECT *
FROM employee
WHERE id = UNHEX(REPLACE('a746d733-f411-11ef-a248-088fc30777b6', '-', ''));
