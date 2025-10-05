package com.kyc.verification.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class ApiKeyGenerator {

    private static final int KEY_LENGTH_BYTES = 32;

    private final SecureRandom secureRandom = new SecureRandom();

    public String generateNewApiKey() {
        byte[] keyBytes = new byte[KEY_LENGTH_BYTES];
        secureRandom.nextBytes(keyBytes);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(keyBytes);
    }
}