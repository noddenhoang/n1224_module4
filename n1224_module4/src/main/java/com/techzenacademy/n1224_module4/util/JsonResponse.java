package com.techzenacademy.n1224_module4.util;

import com.techzenacademy.n1224_module4.dto.ApiResponse;
import com.techzenacademy.n1224_module4.dto.page.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class JsonResponse {

    @SuppressWarnings("unchecked")
    public static <T> ResponseEntity<ApiResponse<?>> ok(T t) {
        if (t instanceof Page<?>) {
            return ResponseEntity.ok(ApiResponse
                    .<PageResponse<T>>builder()
                    .data(new PageResponse<>((Page<T>) t))
                    .build());
        }

        return ResponseEntity.ok(ApiResponse.<T>builder().data(t).build());
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(T t) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<T>builder().data(t).build());
    }

    public static ResponseEntity<Void> noContent() {
        return ResponseEntity.notFound().build();
    }
}
