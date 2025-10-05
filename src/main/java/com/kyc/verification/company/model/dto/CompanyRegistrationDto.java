package com.kyc.verification.company.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CompanyRegistrationDto {

    @NotBlank(message = "نام شرکت الزامی است.")
    @Size(max = 255, message = "نام شرکت طولانی است.")
    private String companyName;

    @NotBlank(message = "ایمیل ادمین الزامی است.")
    @Email(message = "فرمت ایمیل نامعتبر است.")
    private String adminEmail;

    @NotBlank(message = "شماره موبایل ادمین الزامی است.")
    @Size(min = 11, max = 11, message = "شماره موبایل باید ۱۱ رقمی باشد.")
    @Pattern(regexp = "^[0-9]+$", message = "شماره موبایل فقط باید شامل اعداد باشد.")
    private String adminMobileNumber;

    @NotBlank(message = "URL بازگشت (Callback URL) الزامی است.")
    private String callbackUrl;

}