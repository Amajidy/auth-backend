package com.kyc.verification.stepVerification.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerificationResultResponseDto {

    private String trackingCode;

    private String status;

    private String reason;

    private String completedAt;
}
