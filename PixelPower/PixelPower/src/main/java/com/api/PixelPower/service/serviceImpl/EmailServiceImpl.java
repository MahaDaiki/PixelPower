package com.api.PixelPower.service.serviceImpl;

import com.api.PixelPower.service.serviceInt.EmailServiceInt;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailServiceInt {
    private final JavaMailSender mailSender;
    @Override
    public void sendResetPasswordEmail(String to, String token) {
        String resetLink = "http://localhost:4200/reset-password?token=" + token;

        String subject = "⭐Reset Password Request⭐";
        String message = "<p>Use the following token to reset your password:</p>"
                + "<b> ⭐ " + token + " ⭐ </b>";

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(message, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
