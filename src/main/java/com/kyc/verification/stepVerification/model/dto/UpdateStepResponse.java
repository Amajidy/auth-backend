package com.kyc.verification.stepVerification.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateStepResponse {
    private Boolean isPassed;
    private String reason;
}
