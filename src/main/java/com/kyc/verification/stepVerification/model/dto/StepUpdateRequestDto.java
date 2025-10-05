package com.kyc.verification.stepVerification.model.dto;

import com.kyc.verification.stepVerification.model.enums.VerificationStepEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StepUpdateRequestDto {

    @NotBlank
    private String trackingCode;

    @NotNull
    private VerificationStepEnum step;

    @NotNull
    private Boolean isPassed;

    private String storagePath;

    private String details;
}
