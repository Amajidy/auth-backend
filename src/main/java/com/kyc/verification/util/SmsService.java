package com.kyc.verification.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class SmsService {

    private static final MediaType URL_ENCODED_MEDIA_TYPE = MediaType.parse("application/x-www-form-urlencoded");
    private final OkHttpClient client;

    @Value("${sms.api.key}")
    private String apiKey;

    @Value("${sms.api.url}")
    private String apiUrl;

    @Value("${sms.template.otp}")
    private String templateOtp;

    public SmsService() {
        this.client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    public boolean sentOtp(String mobileNumber, String otpCode, String companyName) {

        String bodyContent = "receptor=" + mobileNumber +
                "&template=" + templateOtp +
                "&type=1" +
                "&param1=" + companyName +
                "&param2=" + otpCode;

        RequestBody body = RequestBody.create(bodyContent, URL_ENCODED_MEDIA_TYPE);

        Request request = new Request.Builder()
                .url(apiUrl)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("apikey", apiKey)
                .addHeader("cache-control", "no-cache")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String errorBody = response.body() != null ? response.body().string() : "No response body";
                log.error("SMS API request failed. Code: {}, Error: {}", response.code(), errorBody);
                return false;
            }
            log.info("OTP sent successfully to {}", mobileNumber);
            return true;
        } catch (IOException e) {
            log.error("Error communicating with SMS gateway for mobile {}: {}", mobileNumber, e.getMessage());
            return false;
        }
    }
}
