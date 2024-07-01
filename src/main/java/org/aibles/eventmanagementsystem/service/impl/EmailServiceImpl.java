package org.aibles.eventmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aibles.eventmanagementsystem.exception.exception.InternalErrorException;
import org.aibles.eventmanagementsystem.service.EmailService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;

    @Override
    public void send(String subject, String to, String template, Map<String, Object> properties) {
        log.info("(send)subject: {}, to: {}, template: {}, properties: {}", subject, to, template, properties);
        try {
            var message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(getContent(template, properties), true);
            emailSender.send(message);
        } catch (Exception ex) {
            log.error("(send)subject: {}, to: {}, ex: {} ", subject, to, ex.getMessage());
            throw new InternalErrorException("Failed to send email with template: " + template);
        }
    }

    @Override
    public void send(String subject, String to, String content, List<String> attachments) {
        log.info("(send)subject: {}, to: {}, content: {}, attachments: {}", subject, to, content, attachments);
        try {
            var message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            for (String filePath : attachments) {
                FileSystemResource file = new FileSystemResource(new File(filePath));
                helper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
            }

            emailSender.send(message);
        } catch (Exception ex) {
            log.error("(send)subject: {}, to: {}, ex: {} ", subject, to, ex.getMessage());
            throw new InternalErrorException("Failed to send email with attachments: " + attachments);
        }
    }

    private String getContent(String template, Map<String, Object> properties) {
        Context context = new Context();
        context.setVariables(properties);
        return templateEngine.process(template, context);
    }

    @Override
    public void sendActivationFailedEmail(String email) {
        String subject = "Account Activation Failed";
        String template = "activation-failed-template"; // Tên template email của bạn
        Map<String, Object> properties = Map.of(
                "email", email,
                "message", "Your account activation has failed due to too many incorrect OTP attempts."
        );
        send(subject, email, template, properties);
    }
}
