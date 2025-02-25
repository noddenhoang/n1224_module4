package com.techzenacademy.n1224_module4.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    EMPLOYEE_NOT_EXIST(1001, "Employee does not exist", HttpStatus.NOT_FOUND),
    DEPARTMENT_NOT_EXIST(1002, "Department does not exist", HttpStatus.NOT_FOUND);

    int code;
    String message;
    HttpStatus status;
}

