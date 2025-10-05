package com.kyc.verification.config;

import com.kyc.verification.security.ApiKeyAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private final String API_KEY_HEADER = "X-Api-Key";

    private final AuthenticationManager authenticationManager;

    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // اگر کاربر قبلاً توسط فیلتر JWT احراز هویت شده باشد (برای مسیرهای ادمین)، ادامه می دهیم.
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            filterChain.doFilter(request, response);
            return;
        }

        // ۲. فقط مسیرهای Verification را چک کنید
        if (!request.getRequestURI().startsWith("/api/v1/verification/")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String apiKey = request.getHeader(API_KEY_HEADER);

        // ۳. بررسی وجود کلید
        if (apiKey == null || apiKey.isBlank()) {
            // اگر کلید وجود ندارد، اجازه می دهیم تا SecurityConfig آن را رد کند (authenticated() شکست می خورد).
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // ۴. ساخت توکن احراز هویت نشده
            ApiKeyAuthenticationToken unauthenticatedToken = new ApiKeyAuthenticationToken(apiKey);

            // ۵. تلاش برای احراز هویت (AuthenticationManager، پروایدر را فراخوانی می کند)
            Authentication authenticationResult = authenticationManager.authenticate(unauthenticatedToken);

            // ۶. ذخیره نتیجه در Security Context
            SecurityContextHolder.getContext().setAuthentication(authenticationResult);

        } catch (Exception e) {
            // ۷. در صورت عدم موفقیت در احراز هویت (مثلاً BadCredentialsException)
            log.warn("API Key authentication failed: {}", e.getMessage());
            // استفاده از HandlerExceptionResolver برای ارسال پاسخ 401 مناسب
            handlerExceptionResolver.resolveException(request, response, null, e);
            return;
        }

        // ۸. ادامه فرآیند در زنجیره فیلترها
        filterChain.doFilter(request, response);
    }
}