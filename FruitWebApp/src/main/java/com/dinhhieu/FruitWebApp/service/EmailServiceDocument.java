package com.dinhhieu.FruitWebApp.service;

import com.dinhhieu.FruitWebApp.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceDocument {
    private final Logger logger = LoggerFactory.getLogger(EmailService.class);
    @Autowired
    private JavaMailSender mailSender;

    public void send(String to, String from, String subject, String body, boolean isHtml) {
        try {
            mailSender.send(msg -> {
                MimeMessageHelper message = new MimeMessageHelper(msg,
                        true);
                message.setTo(to);
                message.setFrom(from);
                message.setSubject(subject);
                message.setText(body, isHtml);
            });
        } catch (Throwable t) {
            String msg = "Error sending email for " + to;
            logger.error(msg, t);
            throw new ServiceException(msg, t);
        }
    }
}
