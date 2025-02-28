package com.api.PixelPower.service.serviceInt;

public interface BenchmarkCalculatorServiceInt {
    int calculateCpuScore(String cpuString);
    int calculateGpuScore(String gpuString);
}
