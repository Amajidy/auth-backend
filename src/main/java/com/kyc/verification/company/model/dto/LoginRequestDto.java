package com.kyc.verification.company.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequestDto {

    @NotBlank(message = "شماره موبایل الزامی است.")
    @Size(min = 11, max = 11, message = "شماره موبایل باید ۱۱ رقمی باشد.")
    @Pattern(regexp = "^[0-9]+$", message = "شماره موبایل فقط باید شامل اعداد باشد.")
    private String mobileNumber;

    @NotBlank(message = "کد OTP الزامی است.")
    @Size(min = 4, max = 10, message = "طول کد OTP معتبر نیست.")
    @Pattern(regexp = "^[0-9]+$", message = "شماره موبایل فقط باید شامل اعداد باشد.")
    private String otpCode;
}