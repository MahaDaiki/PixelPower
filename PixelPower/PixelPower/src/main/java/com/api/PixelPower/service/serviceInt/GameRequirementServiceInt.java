package com.api.PixelPower.service.serviceInt;

import com.api.PixelPower.dto.request.GameRequirementsRequestDTO;
import com.api.PixelPower.dto.response.GameRequirementsResponseDTO;

import javax.swing.text.Document;

public interface GameRequirementServiceInt {
    GameRequirementsResponseDTO parseRequirements(int appId, Long userId);
    void parseAndSetRequirements(String html, GameRequirementsResponseDTO responseDTO, String type);
}
