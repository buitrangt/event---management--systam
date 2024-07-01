package org.aibles.eventmanagementsystem.constant;

public enum OtpTimeToLive {
    OTP_ACTIVATE_TTL(3);

    private final long value;

    OtpTimeToLive(long value) {
        this.value = value;
    }
    public long getValue() {
        return value;
    }
}
