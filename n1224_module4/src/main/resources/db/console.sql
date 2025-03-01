create database employee_mana;

use employee_mana;

CREATE TABLE employee (
                          id CHAR(36) PRIMARY KEY, -- UUID
                          name VARCHAR(50),
                          dob DATE,
                          gender ENUM('MALE', 'FEMALE', 'OTHER'),
                          salary DOUBLE,
                          phone VARCHAR(15),
                          department_id INT,
                          FOREIGN KEY (department_id) REFERENCES department(id) ON DELETE SET NULL
);


CREATE TABLE department (
                            id INT PRIMARY KEY AUTO_INCREMENT,
                            name VARCHAR(50)
);

INSERT INTO department (name) VALUES
                                  ('Quản lý'),
                                  ('Kế toán'),
                                  ('Sale-Marketing'),
                                  ('Sản xuất');

select * from department;

INSERT INTO employee (id, name, dob, gender, salary, phone, department_id) VALUES
                                                                               (UUID(), 'Thai Hoang Bao', '1990-10-18', 'MALE', 15000000.0, '1234567890', 1),
                                                                               (UUID(), 'Nguyen Minh Tuan', '1988-05-21', 'OTHER', 20000000.0, '0987654321', 2),
                                                                               (UUID(), 'Tran Thi Lan', '1992-03-14', 'FEMALE', 18500000.0, '0912345678', 3),
                                                                               (UUID(), 'Le Hoang Duy', '1995-11-05', 'MALE', 22000000.0, '0938123456', 4),
                                                                               (UUID(), 'Pham Thi Mai', '1993-08-10', 'FEMALE', 17500000.0, '0976543210', 1),
                                                                               (UUID(), 'Bui Quang Hieu', '1991-12-01', 'MALE', 19000000.0, '0908765432', 2),
                                                                               (UUID(), 'Nguyen Thi Thanh', '1994-04-25', 'FEMALE', 16500000.0, '0945612345', 3),
                                                                               (UUID(), 'Hoang Minh Tu', '1996-07-30', 'MALE', 21500000.0, '0956123456', 4),
                                                                               (UUID(), 'Vu Quoc Duy', '1992-06-18', 'MALE', 20500000.0, '0965432109', 1),
                                                                               (UUID(), 'Tran Thi Lan', '1990-10-25', 'FEMALE', 17000000.0, '0988765432', 1),
                                                                               (UUID(), 'Phan Hoang Tuan', '1995-09-12', 'MALE', 19500000.0, '0912387456', 1);

select * from employee;

SELECT * FROM employee WHERE id = 'a746d733-f411-11ef-a248-088fc30777b6';
