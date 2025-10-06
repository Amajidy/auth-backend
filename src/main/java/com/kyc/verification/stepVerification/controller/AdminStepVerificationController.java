package com.kyc.verification.stepVerification.controller;

import com.kyc.verification.stepVerification.model.dto.StepVerificationDetailsResponse;
import com.kyc.verification.stepVerification.service.VerificationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin/verification")
@SecurityRequirement(name = "BearerAuth")
@RequiredArgsConstructor
public class AdminStepVerificationController {

    private final VerificationService verificationService;

    @GetMapping("/details")
    @PreAuthorize("hasRole('COMPANY_ADMIN')")
    public ResponseEntity<List<StepVerificationDetailsResponse>> getStepDetails(@RequestParam String trackingCode) {

        Optional<List<StepVerificationDetailsResponse>> details = verificationService.getDetailsByTrackingCode(trackingCode);

        return details.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

}
