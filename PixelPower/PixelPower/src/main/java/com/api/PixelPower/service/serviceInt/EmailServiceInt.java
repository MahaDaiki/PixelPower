package com.api.PixelPower.service.serviceInt;

public interface EmailServiceInt {

    void sendResetPasswordEmail(String to, String token);
}
