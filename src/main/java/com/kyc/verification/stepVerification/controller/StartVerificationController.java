package com.kyc.verification.stepVerification.controller;

import com.kyc.verification.stepVerification.service.StartVerificationService;
import com.kyc.verification.stepVerification.service.VerificationFlowService;
import com.kyc.verification.stepVerification.model.dto.StartVerificationRequestDto;
import com.kyc.verification.stepVerification.model.dto.StartVerificationResponse;
import com.kyc.verification.stepVerification.service.VerificationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/verification")
@SecurityRequirement(name = "ApiKeyAuth")
@RequiredArgsConstructor
public class StartVerificationController {

    private final StartVerificationService startVerificationService;
    private final VerificationService verificationService;

    @PostMapping("/start")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<StartVerificationResponse> startVerification(
            @Valid @RequestBody StartVerificationRequestDto requestDto,
            Authentication authentication
    ) {
        String apiKey = (String) authentication.getPrincipal();

        Optional<StartVerificationResponse> resultIfExist = verificationService.findSessionIfExist(
                apiKey, requestDto.getTrackingCode()
        );

        if (resultIfExist.isPresent()) {
            return ResponseEntity.ok(resultIfExist.get());
        }

        StartVerificationResponse result = startVerificationService.startNewSession(
                apiKey,
                requestDto.getTrackingCode(),
                requestDto.getNationalCode(),
                requestDto.getMobileNumber(),
                requestDto.getFirstName(),
                requestDto.getLastName()
        );

        return ResponseEntity.ok(result);
    }
}