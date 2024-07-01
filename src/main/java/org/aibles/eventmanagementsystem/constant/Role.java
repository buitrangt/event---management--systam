package org.aibles.eventmanagementsystem.constant;

public enum Role {
    ADMIN("admin"),
    USER("user"),
    CUSTOMER("customer");

    public static final String USER_ROLE_ID = "user";

    private final String id;

    Role(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
