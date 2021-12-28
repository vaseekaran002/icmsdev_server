package com.perksoft.icms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class SpringBootICMSApplication {
		
	public static void main(String[] args) {
		log.info("Started SpringBootICMSApplication !!!");
		SpringApplication.run(SpringBootICMSApplication.class, args);
	}

}
