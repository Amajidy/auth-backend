package com.kyc.verification.stepVerification.model.dto.steps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShahkarConvertJson {
    private String nationalCode;
    private String mobileNumber;
}
