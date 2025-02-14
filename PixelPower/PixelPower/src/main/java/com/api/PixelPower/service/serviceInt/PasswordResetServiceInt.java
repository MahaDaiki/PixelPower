package com.api.PixelPower.service.serviceInt;

import com.api.PixelPower.dto.request.PasswordResetRequestDTO;
import com.api.PixelPower.dto.ResetPasswordDTO;

public interface PasswordResetServiceInt {
    void sendResetPasswordEmail(PasswordResetRequestDTO requestDTO);
    void resetPassword(ResetPasswordDTO resetPasswordDTO);

}
