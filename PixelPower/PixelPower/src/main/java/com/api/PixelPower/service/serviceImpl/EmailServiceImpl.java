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
//        String resetLink = "http://localhost:4200/reset-password?token=" + token;

        String subject = "⭐Reset Password Request⭐";
        String message = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Password Reset</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: monospace;\n" +
                "            background-color: #211133;\n" +
                "            color: #e0e0e0;\n" +
                "            padding: 20px;\n" +
                "            line-height: 1.6;\n" +
                "        }\n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            border: 4px solid #3f51b5;\n" +
                "            padding: 20px;\n" +
                "            background-color: rgba(33, 17, 51, 0.9);\n" +
                "        }\n" +
                "        .header {\n" +
                "            text-align: center;\n" +
                "            margin-bottom: 20px;\n" +
                "            border-bottom: 2px solid #3f51b5;\n" +
                "            padding-bottom: 10px;\n" +
                "        }\n" +
                "        .title {\n" +
                "            color: #00bcd4;\n" +
                "            font-size: 24px;\n" +
                "            letter-spacing: 3px;\n" +
                "            text-shadow: 0 0 5px rgba(0, 188, 212, 0.5);\n" +
                "        }\n" +
                "        .token-container {\n" +
                "            text-align: center;\n" +
                "            margin: 25px 0;\n" +
                "            padding: 15px;\n" +
                "            background-color: rgba(0, 0, 0, 0.3);\n" +
                "            border: 2px solid #3f51b5;\n" +
                "        }\n" +
                "        .token {\n" +
                "            font-size: 24px;\n" +
                "            letter-spacing: 5px;\n" +
                "            color: #ffc107;\n" +
                "            font-weight: bold;\n" +
                "            text-shadow: 0 0 5px rgba(255, 193, 7, 0.5);\n" +
                "        }\n" +
                "        .instructions {\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "        .expiry {\n" +
                "            color: #f44336;\n" +
                "            font-weight: bold;\n" +
                "        }\n" +
                "        .footer {\n" +
                "            margin-top: 30px;\n" +
                "            padding-top: 15px;\n" +
                "            border-top: 1px solid #3f51b5;\n" +
                "            font-size: 12px;\n" +
                "            color: #9e9e9e;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"header\">\n" +
                "            <div class=\"title\">PASSWORD RESET REQUEST</div>\n" +
                "        </div>\n" +
                "        <div class=\"instructions\">\n" +
                "            <p>Hello,</p>\n" +
                "            <p>We received a request to reset your PixelPower account password. Use the token below to complete the password reset process:</p>\n" +
                "        </div>\n" +
                "        <div class=\"token-container\">\n" +
                "            <div class=\"token\">⭐ " + token + " ⭐</div>\n" +
                "        </div>\n" +
                "        <p>Enter this token on the password reset page to create a new password.</p>\n" +
                "        <p class=\"expiry\">This token will expire in 30 minutes for security reasons.</p>\n" +
                "        <p>If you didn't request a password reset, please ignore this email or contact support if you have concerns about your account security.</p>\n" +
                "        <div class=\"footer\">\n" +
                "            <p>This is an automated message. Please do not reply to this email.</p>\n" +
                "            <p>© " + java.time.Year.now().getValue() + " PixelPower. All rights reserved.</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";

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
