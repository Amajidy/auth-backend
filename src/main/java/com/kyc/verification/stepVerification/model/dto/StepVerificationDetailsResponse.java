package com.kyc.verification.stepVerification.model.dto;

import com.kyc.verification.stepVerification.model.entity.VerificationStepResult;
import com.kyc.verification.stepVerification.model.enums.VerificationStepEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StepVerificationDetailsResponse {
    private String details;
    private String videoData;
    private String signData;
    private VerificationStepEnum stepName;
    private Boolean isPassed;

    public static StepVerificationDetailsResponse fromStepResult(VerificationStepResult stepResult) {
        return StepVerificationDetailsResponse.builder()
                .details(stepResult.getDetails())
                .videoData(stepResult.getVideoData())
                .signData(stepResult.getSignData())
                .stepName(stepResult.getStepName())
                .isPassed(stepResult.getIsPassed())
                .build();
    }
}
