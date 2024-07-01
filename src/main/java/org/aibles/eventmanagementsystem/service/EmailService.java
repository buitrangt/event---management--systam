package org.aibles.eventmanagementsystem.service;



import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Map;

public interface EmailService {

    @Async
    void send(String subject, String to, String template, Map<String, Object> properties);

    @Async
    void send(String subject, String to, String content, List<String> attachments);
    void sendActivationFailedEmail(String email);

}
