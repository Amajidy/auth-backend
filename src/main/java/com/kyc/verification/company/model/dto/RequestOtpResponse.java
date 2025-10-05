package com.kyc.verification.company.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RequestOtpResponse {
    private Boolean isSuccess;
}
