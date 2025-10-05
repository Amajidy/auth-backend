package com.kyc.verification.security;

import com.kyc.verification.company.model.entity.Company;
import com.kyc.verification.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class ApiKeyAuthenticationProvider implements AuthenticationProvider {

    private final CompanyService companyService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String apiKey = (String) authentication.getCredentials();

        if (apiKey == null) {
            throw new BadCredentialsException("API Key is missing or invalid.");
        }

        Company company = companyService.findCompanyByApiKey(apiKey)
                .orElseThrow(() -> new BadCredentialsException("Invalid API Key."));

        return new ApiKeyAuthenticationToken(
                company.getApiKey(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_CLIENT"))
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ApiKeyAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
