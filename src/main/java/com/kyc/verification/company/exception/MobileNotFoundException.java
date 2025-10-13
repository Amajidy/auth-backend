package com.kyc.verification.company.exception;

public class MobileNotFoundException extends RuntimeException {
    public MobileNotFoundException() {
        super("شماره موبابل یافت نشد");
    }
}
