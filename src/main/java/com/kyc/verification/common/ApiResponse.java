package com.kyc.verification.common;

import com.kyc.verification.company.model.dto.RequestOtpResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private T result;
    private String message;
    private Map<String, String> errors;

    public static <T> ApiResponse<T> success(T result, String message) {
        return new ApiResponse<>(result, message, null);
    }

    public static <T> ApiResponse<T> success(T result) {
        return success(result, "عملیات با موفقیت انجام شد");
    }

    public static <T> ApiResponse<T> error(T data) {
        return new ApiResponse<>(data, "عملیات با مشکل مواجه شد", null);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(null, message, null);
    }

    public static <T> ApiResponse<T> validationError(Map<String, String> errors) {
        return new ApiResponse<>(null, "خطاهای اعتبارسنجی در ورودی وجود دارد", errors);
    }
}
