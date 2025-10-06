package com.kyc.verification.company.model.dto;

import com.kyc.verification.stepVerification.model.entity.VerificationSession;
import com.kyc.verification.stepVerification.model.enums.VerificationStepEnum;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserReportDto {
    private String trackingCode;
    private Boolean isCompleted;
    private String failedReason;
    private VerificationStepEnum currentStep;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private String firstName;
    private String lastName;
    private String nationalCode;
    private String mobileNumber;

    public static UserReportDto fromSession(VerificationSession session) {
        return UserReportDto.builder()
                .trackingCode(session.getTrackingCode())
                .isCompleted(session.getIsCompleted())
                .failedReason(session.getFailedReason())
                .firstName(session.getFirstName())
                .lastName(session.getLastName())
                .nationalCode(session.getNationalCode())
                .mobileNumber(session.getMobileNumber())
                .currentStep(session.getCurrentStep())
                .createdAt(session.getCreatedAt())
                .completedAt(session.getUpdatedAt())
                .build();
    }
}
