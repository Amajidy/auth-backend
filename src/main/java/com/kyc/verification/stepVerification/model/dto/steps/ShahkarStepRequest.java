package com.kyc.verification.stepVerification.model.dto.steps;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ShahkarStepRequest {

    @NotBlank(message = "شماره موبایل الزامی است.")
    @Size(min = 11, max = 11, message = "شماره موبایل باید ۱۱ رقمی باشد.")
    @Pattern(regexp = "^[0-9]+$", message = "شماره موبایل فقط باید شامل اعداد باشد.")
    private String mobileNumber;

    @NotBlank(message = "کدملی الزامی است.")
    @Size(min = 10, max = 10, message = "کد ملی باید ۱۰ رقمی باشد.")
    @Pattern(regexp = "^[0-9]+$", message = "کدملی فقط باید شامل اعداد باشد.")
    private String nationalCode;

    @NotBlank(message = "شناسه کاربر الزامی است.")
    private String trackingCode;
}
