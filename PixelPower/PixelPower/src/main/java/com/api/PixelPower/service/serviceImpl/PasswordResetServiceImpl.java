package com.api.PixelPower.service.serviceImpl;

import com.api.PixelPower.dto.request.PasswordResetRequestDTO;
import com.api.PixelPower.dto.ResetPasswordDTO;
import com.api.PixelPower.entity.PasswordResetToken;
import com.api.PixelPower.entity.User;
import com.api.PixelPower.repository.PasswordResetTokenRepository;
import com.api.PixelPower.repository.UserRepository;
import com.api.PixelPower.service.serviceInt.EmailServiceInt;
import com.api.PixelPower.service.serviceInt.PasswordResetServiceInt;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetServiceInt {
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final EmailServiceInt emailService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void sendResetPasswordEmail(PasswordResetRequestDTO requestDTO) {
        Optional<User> userfind = userRepository.findByEmail(requestDTO.getEmail());
        if (userfind.isEmpty()) {
            throw new RuntimeException("User not found with email: " + requestDTO.getEmail());
        }

        User user = userfind.get();
        String token = generateRandomToken(5);


        Optional<PasswordResetToken> existingToken = tokenRepository.findByUser(user);
        existingToken.ifPresent(tokenRepository::delete);


        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(30));
        tokenRepository.save(resetToken);


        emailService.sendResetPasswordEmail(requestDTO.getEmail(), token);
    }



    @Override
    public void resetPassword(ResetPasswordDTO resetPasswordDTO) {
        Optional<PasswordResetToken> tokenOpt = tokenRepository.findByToken(resetPasswordDTO.getToken());
        if (tokenOpt.isEmpty() || tokenOpt.get().getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Invalid or expired token");
        }

        PasswordResetToken resetToken = tokenOpt.get();
        User user = resetToken.getUser();

        user.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
        userRepository.save(user);

        tokenRepository.delete(resetToken);
    }
    private String generateRandomToken(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new SecureRandom();
        StringBuilder token = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            token.append(characters.charAt(random.nextInt(characters.length())));
        }

        return token.toString();
    }
}
