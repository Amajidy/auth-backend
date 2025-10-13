package com.kyc.verification.company.exception;

import com.kyc.verification.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CompanyExceptionHandler {

    @ExceptionHandler(MobileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiResponse<Void> handleException(Exception ex) {
        return ApiResponse.error(ex.getMessage());
    }

}
