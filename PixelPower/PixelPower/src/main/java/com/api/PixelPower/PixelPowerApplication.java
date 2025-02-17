package com.api.PixelPower;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PixelPowerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PixelPowerApplication.class, args);
	}

}
