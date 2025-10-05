package com.kyc.verification.stepVerification.service;

import com.kyc.verification.company.model.dto.UserReportDto;
import com.kyc.verification.company.model.entity.Company;
import com.kyc.verification.company.service.CompanyService;
import com.kyc.verification.stepVerification.model.dto.StartVerificationResponse;
import com.kyc.verification.stepVerification.model.entity.VerificationSession;
import com.kyc.verification.stepVerification.repository.VerificationSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VerificationService {

    private final CompanyService companyService;
    private final VerificationSessionRepository sessionRepository;

    public Optional<StartVerificationResponse> findSessionIfExist(String apiKey, String trackingCode) {
        Company company = companyService.findCompanyByApiKey(apiKey)
                .orElseThrow(() -> new RuntimeException("Invalid API Key"));

        Optional<VerificationSession> session = this.findSessionByTrackingCode(trackingCode);

        if (session.isPresent()) {
            VerificationSession sessionFind = session.get();
            return Optional.ofNullable(StartVerificationResponse.builder()
                    .companyName(company.getName())
                    .trackingCode(sessionFind.getTrackingCode())
                    .firstName(sessionFind.getFirstName())
                    .lastName(sessionFind.getLastName())
                    .nationalCode(sessionFind.getNationalCode())
                    .mobileNumber(sessionFind.getMobileNumber())
                    .currentStep(sessionFind.getCurrentStep())
                    .isCompleted(sessionFind.getIsCompleted())
                    .build());
        }

        return Optional.empty();
    }

    public List<UserReportDto> getReportsByCompanyAdminPrincipal(String adminMobileNumber) {

        Company company = companyService.findByAdminMobileNumber(adminMobileNumber)
                .orElseThrow(() -> new RuntimeException("شرکت مرتبط با کاربر لاگین شده پیدا نشد."));

        List<VerificationSession> sessions = sessionRepository.findAllByCompany(company);

        return sessions.stream()
                .map(UserReportDto::fromSession)
                .collect(Collectors.toList());
    }

    private Optional<VerificationSession> findSessionByTrackingCode(String trackingCode) {
        return this.sessionRepository.findByTrackingCode(trackingCode);
    }
}
