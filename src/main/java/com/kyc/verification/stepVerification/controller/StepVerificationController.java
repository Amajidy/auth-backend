package com.kyc.verification.stepVerification.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kyc.verification.stepVerification.model.dto.steps.ShahkarStepRequest;
import com.kyc.verification.stepVerification.model.dto.StepUpdateRequestDto;
import com.kyc.verification.stepVerification.model.dto.steps.SignStepRequest;
import com.kyc.verification.stepVerification.model.dto.steps.UpdateStepResponse;
import com.kyc.verification.stepVerification.model.dto.steps.VideoStepRequest;
import com.kyc.verification.stepVerification.model.entity.VerificationSession;
import com.kyc.verification.stepVerification.service.VerificationFlowService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/verification/step")
@SecurityRequirement(name = "ApiKeyAuth")
@PreAuthorize("hasRole('CLIENT')")
@RequiredArgsConstructor
public class StepVerificationController {

    private final VerificationFlowService verificationFlowService;

    @PostMapping("/shahkar")
    public ResponseEntity<UpdateStepResponse> shahkarStep(@Valid @RequestBody ShahkarStepRequest request) {
        try {
            UpdateStepResponse response = this.verificationFlowService.shahkarStep(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/video")
    public ResponseEntity<UpdateStepResponse> videoStep(@Valid @RequestBody VideoStepRequest request) {
        try {
            UpdateStepResponse session = verificationFlowService.videoStep(request);
            return ResponseEntity.ok(session);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/sign")
    public ResponseEntity<UpdateStepResponse> signStep(@Valid @RequestBody SignStepRequest request) {
        try {
            UpdateStepResponse session = verificationFlowService.signStep(request);
            return ResponseEntity.ok(session);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
