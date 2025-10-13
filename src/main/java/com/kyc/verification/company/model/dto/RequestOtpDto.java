package com.kyc.verification.company.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
public class RequestOtpDto {
    @NotBlank(message = "شماره موبایل الزامی است")
    @Size(min = 11, max = 11, message = "شماره موبایل باید ۱۱ رقمی باشد")
    @Pattern(regexp = "^[0-9]+$", message = "شماره موبایل فقط باید شامل اعداد باشد")
    private String mobileNumber;
}
