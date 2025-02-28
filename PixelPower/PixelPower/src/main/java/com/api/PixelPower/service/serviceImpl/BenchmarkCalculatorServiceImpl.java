package com.api.PixelPower.service.serviceImpl;

import com.api.PixelPower.service.serviceInt.BenchmarkCalculatorServiceInt;
import org.springframework.stereotype.Service;

import java.util.regex.*;

@Service
public class BenchmarkCalculatorServiceImpl implements BenchmarkCalculatorServiceInt {
    @Override
    public int calculateCpuScore(String cpuString) {
        try {
            String cpuNormalized = cpuString.toUpperCase().trim();

            Pattern intelPattern = Pattern.compile(".*I(\\d)[-\\s]*(\\d{4,5})([A-Z]*)");
            Matcher intelMatcher = intelPattern.matcher(cpuNormalized);

            if (intelMatcher.find()) {
                int series = Integer.parseInt(intelMatcher.group(1));
                int modelNumber = Integer.parseInt(intelMatcher.group(2));
                String suffix = intelMatcher.group(3);

                int generation = modelNumber / 1000;
                int tier = modelNumber % 1000;

                int baseScore = generation * 1200;
                double seriesMultiplier = 0.6 + (series * 0.2);
                int tierScore = tier / 8;
                double suffixMultiplier = 1.0;
                if (suffix.contains("K")) suffixMultiplier += 0.1;
                if (suffix.contains("X")) suffixMultiplier += 0.15;

                return (int)((baseScore + tierScore) * seriesMultiplier * suffixMultiplier);
            }

            Pattern ryzenPattern = Pattern.compile(".*RYZEN\\s*(\\d)\\s*(\\d{3,4})([A-Z]*)");
            Matcher ryzenMatcher = ryzenPattern.matcher(cpuNormalized);

            if (ryzenMatcher.find()) {
                int series = Integer.parseInt(ryzenMatcher.group(1));
                int modelNumber = Integer.parseInt(ryzenMatcher.group(2));
                String suffix = ryzenMatcher.group(3);

                int baseScore = series * 1300;
                int modelScore = modelNumber;
                double suffixMultiplier = 1.0;
                if (suffix.contains("X")) suffixMultiplier += 0.1;
                if (suffix.contains("XT")) suffixMultiplier += 0.15;

                return (int)((baseScore + modelScore) * suffixMultiplier);
            }
            return 5000;
        } catch (Exception e) {
            return 3000;
        }
    }

    @Override
    public int calculateGpuScore(String gpuString) {
        try {
            String gpuNormalized = gpuString.toUpperCase().trim();

            Pattern nvidiaPattern = Pattern.compile(".*(RTX|GTX)\\s*(\\d{4})\\s*(?:Ti|SUPER)?");
            Matcher nvidiaMatcher = nvidiaPattern.matcher(gpuNormalized);

            if (nvidiaMatcher.find()) {
                String series = nvidiaMatcher.group(1);
                int modelNumber = Integer.parseInt(nvidiaMatcher.group(2));

                boolean isTi = gpuNormalized.contains("TI");
                boolean isSuper = gpuNormalized.contains("SUPER");

                int generation = modelNumber / 1000;
                int tier = modelNumber % 100;

                int baseScore = switch (generation) {
                    case 4 -> 16000;
                    case 3 -> 11000;
                    case 2 -> 7000;
                    case 1 -> 5000;
                    default -> 3000;
                };

                double typeMultiplier = series.equals("RTX") ? 1.3 : 1.0;
                int tierScore = tier * 120;
                double suffixMultiplier = isTi ? 1.15 : isSuper ? 1.1 : 1.0;

                return (int)((baseScore + tierScore) * typeMultiplier * suffixMultiplier);
            }

            Pattern radeonPattern = Pattern.compile(".*RADEON.*?(RX)?\\s*(\\d{3,4})\\s*(?:XT)?");
            Matcher radeonMatcher = radeonPattern.matcher(gpuNormalized);

            if (radeonMatcher.find()) {
                String prefix = radeonMatcher.group(1);
                int modelNumber = Integer.parseInt(radeonMatcher.group(2));
                boolean isXT = gpuNormalized.contains("XT");

                int generation = modelNumber / 1000;
                int tier = modelNumber % 1000;

                int baseScore = modelNumber >= 5000 ? 9500 : modelNumber >= 400 ? 3500 : 2500;
                int tierScore = tier * 80;
                double prefixMultiplier = (prefix != null && prefix.equals("RX")) ? 1.1 : 1.0;
                double suffixMultiplier = isXT ? 1.15 : 1.0;

                return (int)((baseScore + tierScore) * prefixMultiplier * suffixMultiplier);
            }
            return 5000;
        } catch (Exception e) {
            return 3000;
        }
    }
    }

