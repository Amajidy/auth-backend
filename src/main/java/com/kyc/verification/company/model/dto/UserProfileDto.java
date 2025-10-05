package com.kyc.verification.company.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserProfileDto {
    private String companyName;
    private String adminEmail;
    private String adminMobileNumber;
    private String apiKey;
    private String callbackUrl;
    private Boolean isActive;
    private LocalDateTime createdAt;
}
