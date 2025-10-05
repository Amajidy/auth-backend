package com.kyc.verification.company.model.dto;

import com.kyc.verification.company.model.entity.Company;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyResponseDto {

    private String name;
    private String adminEmail;
    private String adminMobileNumber;
    private String callbackUrl;
    private Boolean isActive;

    private String apiKey;

    public static CompanyResponseDto fromEntity(Company company) {
        return CompanyResponseDto.builder()
                .name(company.getName())
                .adminEmail(company.getAdminEmail())
                .adminMobileNumber(company.getAdminMobileNumber())
                .callbackUrl(company.getCallbackUrl())
                .isActive(company.getIsActive())
                .apiKey(company.getApiKey())
                .build();
    }
}