package com.kyc.verification.stepVerification.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerificationCallbackResult {
    private String redirectUrl;
    private String trackingCode;
    private String status;
    private String callbackUrl;
}