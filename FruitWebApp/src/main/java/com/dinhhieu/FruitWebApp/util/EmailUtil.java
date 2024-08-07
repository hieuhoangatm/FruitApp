package com.dinhhieu.FruitWebApp.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendOtpEmail(String email, String otp) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Verify OTP");

        mimeMessageHelper.setText("""
                <div>
                    <a href = "http://localhost:8080/api/v1/auth/verify-account?email=%s&otp=%s" target ="_blank">CLick link to verify
                </div>
                """.formatted(email,otp),true);

        javaMailSender.send(mimeMessage);
    }


    public void sendSetPasswordEmail(String email) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Set password");

        mimeMessageHelper.setText("""
                <div>
                    <a href = "http://localhost:8080/api/v1/auth/set-password?email=%s" target ="_blank">CLick link to set password
                </div>
                """.formatted(email),true);

        javaMailSender.send(mimeMessage);
    }
}
