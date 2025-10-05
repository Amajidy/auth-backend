package com.kyc.verification.stepVerification.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kyc.verification.stepVerification.model.dto.ShahkarStepRequest;
import com.kyc.verification.stepVerification.model.dto.StepUpdateRequestDto;
import com.kyc.verification.stepVerification.model.dto.UpdateStepResponse;
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
@RequiredArgsConstructor
public class StepVerificationController {

    private final VerificationFlowService verificationFlowService;

    @PostMapping("/shahkar")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<?> shahkarStep(@Valid @RequestBody ShahkarStepRequest request) {
        try {
            UpdateStepResponse response = this.verificationFlowService.shahkarStep(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

//    @PostMapping("/step/update")
//    @PreAuthorize("hasRole('CLIENT')")
//    public ResponseEntity<?> updateStep(@Valid @RequestBody StepUpdateRequestDto updateDto) {
//        try {
//            VerificationSession session = verificationFlowService.updateStepStatus(
//                    updateDto.getTrackingCode(),
//                    updateDto.getStep(),
//                    updateDto.getIsPassed(),
//                    updateDto.getStoragePath(),
//                    updateDto.getDetails()
//            );
//
////            if (session.getIsCompleted() != null && session.getIsCompleted()) {
////
////                VerificationCallbackResult callbackResult = verificationFlowService.finalizeCallback(session);
////
////                return ResponseEntity
////                        .status(HttpStatus.FOUND)
////                        .location(URI.create(callbackResult.getRedirectUrl()))
////                        .build();
////            }
//
//            UpdateStepResponse response = UpdateStepResponse.builder()
//                    .step(session.getCurrentStep())
//                    .isCompleted(session.getIsCompleted())
//                    .build();
//
//            return ResponseEntity.ok(response);
//
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

}
