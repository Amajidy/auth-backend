package com.kyc.verification.stepVerification.service;

import com.kyc.verification.company.model.entity.Company;
import com.kyc.verification.company.service.CompanyService;
import com.kyc.verification.stepVerification.model.dto.StartVerificationResponse;
import com.kyc.verification.stepVerification.model.entity.VerificationSession;
import com.kyc.verification.stepVerification.model.enums.VerificationStepEnum;
import com.kyc.verification.stepVerification.repository.VerificationSessionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StartVerificationService {

    private final CompanyService companyService;
    private final VerificationSessionRepository sessionRepository;

    @Transactional
    public StartVerificationResponse startNewSession(
            String apiKey, String trackingCode,
            String nationalCode, String mobileNumber,
            String firstName, String lastName
    ) {
        Company company = companyService.findCompanyByApiKey(apiKey)
                .orElseThrow(() -> new RuntimeException("Invalid API Key"));

        VerificationSession session = new VerificationSession();
        session.setCompany(company);
        session.setTrackingCode(trackingCode);
        session.setNationalCode(nationalCode);
        session.setMobileNumber(mobileNumber);
        session.setFirstName(firstName);
        session.setLastName(lastName);
        session.setCurrentStep(VerificationStepEnum.SHAHKAR);
        session.setIsCompleted(false);

        VerificationSession sessionSaved = sessionRepository.save(session);

        return StartVerificationResponse.builder()
                .companyName(company.getName())
                .trackingCode(sessionSaved.getTrackingCode())
                .firstName(sessionSaved.getFirstName())
                .lastName(sessionSaved.getLastName())
                .nationalCode(sessionSaved.getNationalCode())
                .mobileNumber(sessionSaved.getMobileNumber())
                .currentStep(sessionSaved.getCurrentStep())
                .isCompleted(sessionSaved.getIsCompleted())
                .build();
    }
}
