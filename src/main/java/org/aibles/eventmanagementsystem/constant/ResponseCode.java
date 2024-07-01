package org.aibles.eventmanagementsystem.constant;

public enum ResponseCode {
    SUCCESS("success"),
    INVALID_REQUEST("invalid_request"),
    EMAIL_NOT_FOUND("email_not_found"),
    ACCOUNT_NOT_FOUND("account_not_found"),
    INVALID_OTP("invalid_otp"),
    OTP_EXPIRED("otp_expired"),
    PASSWORDS_DO_NOT_MATCH("Passwords do not match"),
    EMAIL_ALREADY_EXISTS("Email already exists"),
    USERNAME_ALREADY_EXISTS("Username already exists"),
    ACCOUNT_PERMANENTLY_LOCKED("account_permanently_locked"),
    ACCOUNT_TEMPORARILY_LOCKED("account_temporarily_locked"),
    INVALID_PASSWORD("invalid_password"),
    ACCOUNT_NOT_ACTIVATED("account_not_activated"),
    UNEXPECTED_ERROR("unexpected_error"),
    USER_NOT_FOUND("user_not_found");

    private final String value;

    ResponseCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
