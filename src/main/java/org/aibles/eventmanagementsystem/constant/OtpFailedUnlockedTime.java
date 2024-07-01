package org.aibles.eventmanagementsystem.constant;

public enum OtpFailedUnlockedTime {

    FIVE(5, 120),
    TEN(10, 300),
    FIFTEEN(15, 1800);

    private final int attempts;
    private final int cooldownTime;


    OtpFailedUnlockedTime(int attempts, int cooldownTime) {
        this.attempts = attempts;
        this.cooldownTime = cooldownTime;
    }

    public int getAttempts() {
        return attempts;
    }

    public int getCooldownTime() {
        return cooldownTime;
    }
}
