-- Tạo cơ sở dữ liệu nếu chưa tồn tại
CREATE DATABASE IF NOT EXISTS employee_management;
USE employee_mana;

-- Tạo bảng department
CREATE TABLE department
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

-- Chèn dữ liệu vào bảng department
INSERT INTO department (id, name)
VALUES (1, 'Quản lý'),
       (2, 'Kế toán'),
       (3, 'Sale-Marketing'),
       (4, 'Sản xuất');

-- Tạo bảng employee
CREATE TABLE employee
(
    id            BINARY(16) PRIMARY KEY,
    name          VARCHAR(255)                     NOT NULL,
    dob           DATE                             NOT NULL,
    gender        ENUM ('MALE', 'FEMALE', 'OTHER') NOT NULL, -- Thêm 'OTHER'
    salary        DECIMAL(15, 2)                   NOT NULL,
    phone         VARCHAR(15),
    department_id INT,
    CONSTRAINT fk_department
        FOREIGN KEY (department_id)
            REFERENCES department (id)
            ON DELETE CASCADE
);

-- Chèn dữ liệu vào bảng employee
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
       (UNHEX(REPLACE(UUID(), '-', '')), 'Phan Hoang Tuan', '1995-09-12', 'MALE', 19500000.0, '0971122334', 2);
