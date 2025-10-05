package com.kyc.verification.stepVerification.model.enums;

import lombok.Getter;

@Getter
public enum VerificationStepEnum {

    SHAHKAR(1, "SHAHKAR"),
    VIDEO(2, "VIDEO"),
    SIGN(3, "SIGN");

    private final int stepNumber;
    private final String description;

    VerificationStepEnum(int stepNumber, String description) {
        this.stepNumber = stepNumber;
        this.description = description;
    }

    public static VerificationStepEnum fromStepNumber(int number) {
        for (VerificationStepEnum step : values()) {
            if (step.getStepNumber() == number) {
                return step;
            }
        }
        throw new IllegalArgumentException("Invalid step number: " + number);
    }
}