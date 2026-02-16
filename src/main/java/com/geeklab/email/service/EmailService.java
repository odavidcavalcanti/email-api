package com.geeklab.email.service;

import com.geeklab.email.DTO.ContactRequestDTO;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String fromEmail;

    @Value("${app.mail.to}")
    private String toEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private void sendToAdmin(ContactRequestDTO requestDTO) throws Exception {
        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, StandardCharsets.UTF_8.name());

        messageHelper.setFrom(fromEmail);
        messageHelper.setTo(toEmail);
        messageHelper.setSubject("Novo contato - 3D Geek Lab - " + requestDTO.email());
        messageHelper.setReplyTo(requestDTO.email());

        String body =
                "Name: " + requestDTO.name() + "\n" +
                "E-mail: " + requestDTO.email() + "\n\n" +
                "Message:\n" + requestDTO.messageText() + "\n\n" +
                "Date: " + ZonedDateTime.now() + "\n";

        messageHelper.setText(body,false);
        mailSender.send(mailMessage);
    }

    private void sendAutoReply(ContactRequestDTO contactRequestDTO) throws Exception{
        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, StandardCharsets.UTF_8.name());

        messageHelper.setFrom(fromEmail);
        messageHelper.setTo(contactRequestDTO.email());
        messageHelper.setSubject("We received your message - 3D Geek Lab");

        String body =
                "Hello, " + contactRequestDTO.name() + "!\n\n" +
                "We received your message, and will return as soon as possible.\n\n" +
                "Thank you for contacting us.\n-3D Geek Lab ";

        messageHelper.setText(body);
        mailSender.send(mailMessage);
    }

    public void sendEmails (ContactRequestDTO requestDTO) throws Exception {
        sendToAdmin(requestDTO);
        sendAutoReply(requestDTO);
    }

}
