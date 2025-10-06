package com.kyc.verification.stepVerification.model.dto.steps;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VideoStepRequest {

    @NotBlank(message = "Tracking code is mandatory")
    private String trackingCode;

    @NotNull(message = "video data (Base64 string) is mandatory")
    private String videoData;

}
