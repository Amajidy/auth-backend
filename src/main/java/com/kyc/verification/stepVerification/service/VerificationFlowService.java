package com.kyc.verification.stepVerification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyc.verification.stepVerification.model.dto.steps.*;
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
    public UpdateStepResponse videoStep(@Valid VideoStepRequest request) {
        VerificationSession session = sessionRepository.findByTrackingCode(request.getTrackingCode())
                .orElseThrow(() -> new RuntimeException("Session not found or expired"));

        Boolean isPassed = true;

        VerificationStepResult stepResult = new VerificationStepResult();
        stepResult.setSession(session);
        stepResult.setStepName(VerificationStepEnum.VIDEO);
        stepResult.setIsPassed(isPassed);

        if (request.getVideoData() != null && !request.getVideoData().isEmpty()) {
            stepResult.setVideoData(request.getVideoData());
        }

        resultRepository.save(stepResult);

        if (isPassed) {
            session.setCurrentStep(VerificationStepEnum.SIGN);
            sessionRepository.save(session);
        }

        return UpdateStepResponse.builder()
                .isPassed(isPassed)
                .build();
    }

    @Transactional
    public UpdateStepResponse signStep(@Valid SignStepRequest request) {
        VerificationSession session = sessionRepository.findByTrackingCode(request.getTrackingCode())
                .orElseThrow(() -> new RuntimeException("Session not found or expired"));

        Boolean isPassed = true;

        VerificationStepResult stepResult = new VerificationStepResult();
        stepResult.setSession(session);
        stepResult.setStepName(VerificationStepEnum.SIGN);
        stepResult.setIsPassed(isPassed);

        if (request.getSignData() != null && !request.getSignData().isEmpty()) {
            stepResult.setSignData(request.getSignData());
        }

        resultRepository.save(stepResult);

        if (isPassed) {
            session.setIsCompleted(true);
            sessionRepository.save(session);
        }

        return UpdateStepResponse.builder()
                .isPassed(isPassed)
                .build();
    }
}