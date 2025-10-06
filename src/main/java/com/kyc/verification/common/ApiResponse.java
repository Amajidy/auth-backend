package com.kyc.verification.common;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(HttpStatus.OK.value(), message, data);
    }

    public static <T> ApiResponse<T> success(T data) {
        return success(data, "عملیات با موفقیت انجام شد.");
    }

    public static ApiResponse<Void> successNoContent(String message) {
        return new ApiResponse<>(HttpStatus.NO_CONTENT.value(), message, null);
    }
}
