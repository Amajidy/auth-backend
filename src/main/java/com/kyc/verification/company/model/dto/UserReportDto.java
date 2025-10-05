package com.kyc.verification.company.model.dto;

import com.kyc.verification.stepVerification.model.entity.VerificationSession;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserReportDto {
    private String trackingCode;
    private String status; // وضعیت نهایی: SUCCESS, FAILED, PENDING
    private String failedReason;
    private String currentStep;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;

    public static UserReportDto fromSession(VerificationSession session) {
        String finalStatus = "PENDING";
        if (session.getIsCompleted() != null) {
            finalStatus = session.getIsCompleted() ? "SUCCESS" : "FAILED";
        }

        return UserReportDto.builder()
                .trackingCode(session.getTrackingCode())
                .status(finalStatus)
                .failedReason(session.getFailedReason())
                .currentStep("Step " + session.getCurrentStep())
                .createdAt(session.getCreatedAt())
                .completedAt(session.getUpdatedAt())
                .build();
    }
}
