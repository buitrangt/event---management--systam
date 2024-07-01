package org.aibles.eventmanagementsystem.dto.response;

public class SignupResponse {
    private String email;
    private String username;
    private String message;

    public SignupResponse(String email, String username, String message) {
        this.email = email;
        this.username = username;
        this.message = message;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
