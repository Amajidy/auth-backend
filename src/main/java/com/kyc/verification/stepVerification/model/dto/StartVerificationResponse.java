package com.kyc.verification.stepVerification.model.dto;

import com.kyc.verification.stepVerification.model.enums.VerificationStepEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StartVerificationResponse {
    private String companyName;
    private String trackingCode;
    private String firstName;
    private String lastName;
    private String nationalCode;
    private String mobileNumber;
    private VerificationStepEnum currentStep;
    private Boolean isCompleted;
}
