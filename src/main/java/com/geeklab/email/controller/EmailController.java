package com.geeklab.email.controller;

import com.geeklab.email.DTO.ContactRequestDTO;
import com.geeklab.email.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class EmailController {
    private final EmailService mailService;

    public EmailController(EmailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/contact")
    public ResponseEntity<?> sendMail (@Valid @RequestBody ContactRequestDTO requestDTO) throws Exception{
        mailService.sendEmails(requestDTO);
        return ResponseEntity.ok().build();
    }
}
