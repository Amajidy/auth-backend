package com.kyc.verification.company.controller;

import com.kyc.verification.common.ApiResponse;
import com.kyc.verification.company.exception.MobileNotFoundException;
import com.kyc.verification.company.model.dto.CompanyRegistrationDto;
import com.kyc.verification.company.model.dto.CompanyResponseDto;
import com.kyc.verification.company.model.dto.LoginRequestDto;
import com.kyc.verification.company.model.dto.RequestOtpResponse;
import com.kyc.verification.company.model.dto.RequestOtpDto;
import com.kyc.verification.company.model.entity.Company;
import com.kyc.verification.company.service.CompanyService;
import com.kyc.verification.common.JwtTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth/company")
@RequiredArgsConstructor
public class CompanyAuthController {

    private final CompanyService companyService;
    private final JwtTokenService jwtTokenService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<CompanyResponseDto>> registerCompany(@Valid @RequestBody CompanyRegistrationDto registrationDto) {
        Company newCompany = companyService.registerCompany(registrationDto);
        CompanyResponseDto response = CompanyResponseDto.fromEntity(newCompany);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @PostMapping("/otp/request")
    public ResponseEntity<ApiResponse<RequestOtpResponse>> requestOtp(@Valid @RequestBody RequestOtpDto requestOtpDto) {
        boolean success = companyService.initiateOtpLogin(requestOtpDto.getMobileNumber());

        RequestOtpResponse response = RequestOtpResponse.builder()
                .isSuccess(success)
                .build();

        if (success) {
            return ResponseEntity.ok(ApiResponse.success(response));
        }

        throw new MobileNotFoundException();
    }

    @PostMapping("/otp/verify")
    public ResponseEntity<?> verifyOtpAndLogin(@Valid @RequestBody LoginRequestDto loginRequestDto) {

        Optional<Company> companyOpt = companyService.validateOtpAndLogin(
                loginRequestDto.getMobileNumber(),
                loginRequestDto.getOtpCode()
        );

        if (companyOpt.isPresent()) {
            Company company = companyOpt.get();

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    company.getAdminMobileNumber(),
                    null,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_COMPANY_ADMIN"))
            );

            String jwtToken = jwtTokenService.generateToken(authentication);

            return ResponseEntity.ok(ApiResponse.success(jwtToken));

        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(ApiResponse.error("Invalid mobile number or OTP code.")));
        }
    }
}