package com.kyc.verification.stepVerification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyc.verification.stepVerification.model.dto.ShahkarConvertJson;
import com.kyc.verification.stepVerification.model.dto.ShahkarStepRequest;
import com.kyc.verification.stepVerification.model.dto.UpdateStepResponse;
import com.kyc.verification.stepVerification.model.dto.VerificationCallbackResult;
import com.kyc.verification.stepVerification.model.entity.VerificationSession;
import com.kyc.verification.stepVerification.model.entity.VerificationStepResult;
import com.kyc.verification.stepVerification.model.enums.VerificationStepEnum;
import com.kyc.verification.stepVerification.repository.VerificationSessionRepository;
import com.kyc.verification.stepVerification.repository.VerificationStepResultRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationFlowService {

    private final VerificationSessionRepository sessionRepository;
    private final VerificationStepResultRepository resultRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public UpdateStepResponse shahkarStep(@Valid ShahkarStepRequest request) throws JsonProcessingException {
        VerificationSession session = sessionRepository.findByTrackingCode(request.getTrackingCode())
                .orElseThrow(() -> new RuntimeException("Session not found or expired"));

        Boolean isPassed = true;

        ShahkarConvertJson convertJson  = new ShahkarConvertJson(
                request.getNationalCode(),
                request.getMobileNumber()
        );

        String detailJson = objectMapper.writeValueAsString(convertJson);

        VerificationStepResult result = new VerificationStepResult();
        result.setSession(session);
        result.setStepName(VerificationStepEnum.SHAHKAR);
        result.setIsPassed(isPassed);
        result.setDetails(detailJson);
        resultRepository.save(result);

        if (isPassed) {
            session.setCurrentStep(VerificationStepEnum.VIDEO);
            sessionRepository.save(session);
        }

        return UpdateStepResponse.builder()
                .isPassed(isPassed)
                .build();
    }

    @Transactional
    public VerificationSession updateStepStatus(
            String trackingCode, VerificationStepEnum stepName,
            boolean isPassed, String storagePath,
            String details
    ) {

        VerificationSession session = sessionRepository.findByTrackingCode(trackingCode)
                .orElseThrow(() -> new RuntimeException("Session not found or expired"));

        if (!session.getCurrentStep().equals(stepName)) {
            throw new IllegalStateException("User is not at step " + stepName);
        }

        VerificationStepResult result = new VerificationStepResult();
        result.setSession(session);
        result.setStepName(stepName);
        result.setIsPassed(isPassed);
        result.setDataStoragePath(storagePath);
        result.setDetails(details);
        resultRepository.save(result);

        if (isPassed) {
            if (stepName.getStepNumber() < 3) {
                session.setCurrentStep(VerificationStepEnum.fromStepNumber(stepName.getStepNumber() + 1));
            } else {
                session.setIsCompleted(true);
            }
        } else {
            session.setIsCompleted(false);
            session.setFailedReason("Failed at step " + stepName);
        }

        return sessionRepository.save(session);
    }

    public VerificationCallbackResult finalizeCallback(VerificationSession session) {
        String status = session.getIsCompleted() ? "SUCCESS" : "FAILED";
        String reason = session.getFailedReason() != null ? session.getFailedReason() : "";

        String queryParams = String.format(
                "?trackingCode=%s&status=%s&reason=%s",
                session.getTrackingCode(),
                status,
                reason
        );

        String finalRedirectUrl = session.getCompany().getCallbackUrl() + queryParams;

        return VerificationCallbackResult.builder()
                .redirectUrl(finalRedirectUrl)
                .trackingCode(session.getTrackingCode())
                .status(status)
                .callbackUrl(session.getCompany().getCallbackUrl())
                .build();
    }
}