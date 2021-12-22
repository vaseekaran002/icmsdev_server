package com.perksoft.icms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootICMSApplication {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(SpringBootICMSApplication.class);
	
	public static void main(String[] args) {
		LOGGER.info("Started SpringBootICMSApplication !!!");
		SpringApplication.run(SpringBootICMSApplication.class, args);
	}

}
