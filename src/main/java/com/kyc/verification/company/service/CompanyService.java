package com.kyc.verification.company.service;

import com.kyc.verification.company.model.dto.CompanyRegistrationDto;
import com.kyc.verification.company.model.dto.UserProfileDto;
import com.kyc.verification.company.model.entity.Company;
import com.kyc.verification.company.repository.CompanyRepository;
import com.kyc.verification.common.OtpService;
import com.kyc.verification.util.ApiKeyGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final OtpService otpService;
    private final ApiKeyGenerator apiKeyGenerator;

    public Company registerCompany(CompanyRegistrationDto registrationDto) {
        Company company = new Company();
        company.setName(registrationDto.getCompanyName());
        company.setAdminEmail(registrationDto.getAdminEmail());
        company.setAdminMobileNumber(registrationDto.getAdminMobileNumber());
        company.setCallbackUrl(registrationDto.getCallbackUrl());
        company.setIsActive(true);

        company.setApiKey(apiKeyGenerator.generateNewApiKey());

        return companyRepository.save(company);
    }

    public boolean initiateOtpLogin(String mobileNumber) {
        Optional<Company> companyOpt = companyRepository.findByAdminMobileNumber(mobileNumber);

        if (companyOpt.isPresent()) {
            Company company = companyOpt.get();
            String otpCode = otpService.generateAndStoreOtp(company, mobileNumber);

            System.out.println("OTP sent to " + mobileNumber + ": " + otpCode);

            return true;
        }
        return false;
    }

    public Optional<Company> validateOtpAndLogin(String mobileNumber, String otpCode) {
        Optional<Long> companyIdOpt = otpService.validateOtp(mobileNumber, otpCode);

        return companyIdOpt.flatMap(companyRepository::findById);
    }

    public Optional<Company> findCompanyByApiKey(String apiKey) {
        return companyRepository.findByApiKey(apiKey);
    }

    public Optional<Company> findByAdminMobileNumber(String adminMobileNumber) {
        return companyRepository.findByAdminMobileNumber(adminMobileNumber);
    }

    public UserProfileDto getCompanyProfile(String adminMobileNumber) {
        Optional<Company> companyOpt = companyRepository.findByAdminMobileNumber(adminMobileNumber);
        if (companyOpt.isPresent()) {
            Company company = companyOpt.get();
            return UserProfileDto.builder()
                    .companyName(company.getName())
                    .adminEmail(company.getAdminEmail())
                    .adminMobileNumber(company.getAdminMobileNumber())
                    .callbackUrl(company.getCallbackUrl())
                    .createdAt(company.getCreatedAt())
                    .isActive(company.getIsActive())
                    .apiKey(company.getApiKey())
                    .build();
        }
        return null;
    }
}