package com.dinhhieu.FruitWebApp.controller;

import com.dinhhieu.FruitWebApp.dto.ResponData;
import com.dinhhieu.FruitWebApp.dto.response.EmailResponse;
import com.dinhhieu.FruitWebApp.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @GetMapping("/sendEmail")
    public EmailResponse sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String text) {
        emailService.sendSimpleEmail(to, subject, text);
        return new EmailResponse(to, subject, text, "Email sent successfully");
    }
}
