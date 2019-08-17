package com.roihunter.medialoader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MedialoaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedialoaderApplication.class, args);
	}

}
