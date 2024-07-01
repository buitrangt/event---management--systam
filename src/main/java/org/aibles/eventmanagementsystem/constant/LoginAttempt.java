package org.aibles.eventmanagementsystem.constant;

public enum LoginAttempt {
    FIVE(5), TEN(10), FIFTEEN(15);

    private final int value;

    LoginAttempt(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
