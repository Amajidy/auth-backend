package com.kyc.verification.util;


import com.kyc.verification.company.model.entity.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OtpService {

    private static final long OTP_EXPIRATION_SECONDS = 120; // 2 min
    private final StringRedisTemplate redisTemplate;
    private final SmsService smsService;

    public String generateAndStoreOtp(Company company, String mobileNumber) {
        String otpCode = String.format("%06d", new SecureRandom().nextInt(999999));

        boolean isSent = smsService.sentOtp(mobileNumber, otpCode, company.getName());
        if (!isSent) {
            throw new RuntimeException("SMS not sent");
        }

        String otpValue = otpCode + ":" + company.getId();
        String key = "otp:" + mobileNumber;

        redisTemplate.opsForValue().set(key, otpValue, OTP_EXPIRATION_SECONDS, TimeUnit.SECONDS);


        return otpCode;
    }

    public Optional<Long> validateOtp(String mobileNumber, String otpCode) {
        String key = "otp:" + mobileNumber;
        String storedValue = redisTemplate.opsForValue().get(key);

        if (storedValue != null) {
            String[] parts = storedValue.split(":");
            String storedOtp = parts[0];
            Long storedCompanyId = Long.parseLong(parts[1]);

            if (storedOtp.equals(otpCode)) {
                redisTemplate.delete(key);
                return Optional.of(storedCompanyId);
            }
        }
        return Optional.empty();
    }

}

