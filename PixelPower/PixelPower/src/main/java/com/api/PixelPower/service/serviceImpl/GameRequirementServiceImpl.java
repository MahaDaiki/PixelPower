package com.api.PixelPower.service.serviceImpl;

import com.api.PixelPower.dto.request.GameRequirementsRequestDTO;
import com.api.PixelPower.dto.response.GameRequirementsResponseDTO;
import com.api.PixelPower.entity.Configuration;
import com.api.PixelPower.entity.OperatingSystem;
import com.api.PixelPower.service.serviceInt.ConfigurationServiceInt;
import com.api.PixelPower.service.serviceInt.GameRequirementServiceInt;
import com.api.PixelPower.service.serviceInt.SteamServiceInt;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@AllArgsConstructor
public class GameRequirementServiceImpl implements GameRequirementServiceInt {
    private final SteamServiceInt steamService;
    private final ConfigurationServiceInt configurationService;

    @Override
        public GameRequirementsResponseDTO parseRequirements(int appId, Long userId) {
            GameRequirementsResponseDTO responseDTO = new GameRequirementsResponseDTO();


            OperatingSystem userOsEnum = configurationService.getPrimaryUserOs(userId);
            if (userOsEnum == null) {
                throw new RuntimeException("User does not have a primary configuration.");
            }

            String userOs = userOsEnum.name().toLowerCase();


            Map<String, Object> gameDetails = steamService.getGameDetails(appId);
            if (gameDetails == null || !gameDetails.containsKey(String.valueOf(appId))) {
                return responseDTO;
            }

            Map<String, Object> appData = (Map<String, Object>) gameDetails.get(String.valueOf(appId));
            if (appData == null || !(boolean) appData.get("success") || !appData.containsKey("data")) {
                return responseDTO;
            }

            Map<String, Object> data = (Map<String, Object>) appData.get("data");

            responseDTO.setGameName((String) data.get("name"));


            Map<String, String> osRequirements = getOsRequirements(data, userOs);
            if (osRequirements != null) {
                String minimumRequirements = osRequirements.get("minimum");
                String recommendedRequirements = osRequirements.get("recommended");


                if (minimumRequirements != null) {
                    parseAndSetRequirements(minimumRequirements, responseDTO, "min");
                }

                if (recommendedRequirements != null) {
                    parseAndSetRequirements(recommendedRequirements, responseDTO, "max");
                }
            } else {
                throw new RuntimeException("No OS requirements found for the game on " + userOs);
            }

            return responseDTO;
        }


        private Map<String, String> getOsRequirements(Map<String, Object> data, String os) {
            if ("windows".equalsIgnoreCase(os) && data.containsKey("pc_requirements")) {
                return (Map<String, String>) data.get("pc_requirements");
            } else if ("macos".equalsIgnoreCase(os) && data.containsKey("mac_requirements")) {
                return (Map<String, String>) data.get("mac_requirements");
            } else if ("linux".equalsIgnoreCase(os) && data.containsKey("linux_requirements")) {
                return (Map<String, String>) data.get("linux_requirements");
            }
            return null;
        }

        @Override
        public void parseAndSetRequirements(String html, GameRequirementsResponseDTO responseDTO, String type) {
            if (html == null || html.isEmpty()) {
                return;
            }

            Document document = Jsoup.parse(html);


            String cpu = extractCpu(document);
            String gpu = extractGpu(document);
            String ram = extractRam(document);
            String os = extractOs(document);
            String storage = extractStorage(document);

            if ("min".equals(type)) {
                responseDTO.setMinCpu(cpu);
                responseDTO.setMinGpu(gpu);
                responseDTO.setRam(ram);
                responseDTO.setOs(os);
                responseDTO.setStorage(storage);
            } else {
                responseDTO.setMaxCpu(cpu);
                responseDTO.setMaxGpu(gpu);
                responseDTO.setRam(ram);
                responseDTO.setOs(os);
                responseDTO.setStorage(storage);
            }
        }

        private String extractCpu(Document document) {
            String cpu = document.select("li:contains(Processor:)").text().replace("Processor:", "").trim();
            return cpu.isEmpty() ? "N/A" : cpu;
        }

        private String extractGpu(Document document) {
            String gpu = document.select("li:contains(Graphics:)").text().replace("Graphics:", "").trim();
            return gpu.isEmpty() ? "N/A" : gpu;
        }

        private String extractRam(Document document) {
            String ram = document.select("li:contains(Memory:)").text().replace("Memory:", "").trim();
            return ram.isEmpty() ? "N/A" : ram;
        }

        private String extractOs(Document document) {
            String osText = document.select("li:contains(OS:)").text();
            if (osText.contains("OS:")) {
                osText = osText.replace("OS:", "").trim();
            }
            return osText.isEmpty() ? "N/A" : osText;
        }

        private String extractStorage(Document document) {
            String storage = document.select("li:contains(Storage:)").text().replace("Storage:", "").trim();
            return storage.isEmpty() ? "N/A" : storage;
        }
    }


