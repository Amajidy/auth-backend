package com.kyc.verification.company.controller;

import com.kyc.verification.common.ApiResponse;
import com.kyc.verification.company.model.dto.UserProfileDto;
import com.kyc.verification.company.model.dto.UserReportDto;
import com.kyc.verification.company.service.CompanyService;
import com.kyc.verification.stepVerification.service.VerificationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@SecurityRequirement(name = "BearerAuth")
@RequiredArgsConstructor
public class CompanyAdminController {

    private final VerificationService verificationService;
    private final CompanyService companyService;

    @GetMapping("reports")
    @PreAuthorize("hasRole('COMPANY_ADMIN')")
    public ResponseEntity<ApiResponse<List<UserReportDto>>> getCompanyReports() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String adminMobileNumber = authentication.getName();

        List<UserReportDto> reports = verificationService.getReportsByCompanyAdminPrincipal(adminMobileNumber);

        return ResponseEntity.ok(ApiResponse.success(reports));
    }

    @GetMapping("profile")
    @PreAuthorize("hasRole('COMPANY_ADMIN')")
    public ResponseEntity<ApiResponse<UserProfileDto>> getCompanyProfile() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String adminMobileNumber = authentication.getName();

        UserProfileDto profile = companyService.getCompanyProfile(adminMobileNumber);

        return ResponseEntity.ok(ApiResponse.success(profile));
    }
}