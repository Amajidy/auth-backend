package com.kyc.verification.stepVerification.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StartVerificationRequestDto {

    @NotBlank(message = "کد رهگیری (Tracking Code) الزامی است.")
    private String trackingCode;

    @NotBlank(message = "نام الزامی است.")
    private String firstName;

    @NotBlank(message = "نام خانوادگی الزامی است.")
    private String lastName;

    @NotBlank(message = "کد ملی الزامی است.")
    @Size(min = 10, max = 10, message = "کد ملی باید ۱۰ رقمی باشد.")
    private String nationalCode;

    @NotBlank(message = "شماره موبایل الزامی است.")
    @Size(min = 11, max = 11, message = "شماره موبایل باید ۱۱ رقمی باشد.")
    private String mobileNumber;

}
