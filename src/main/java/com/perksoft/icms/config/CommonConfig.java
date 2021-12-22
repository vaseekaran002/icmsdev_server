package com.perksoft.icms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Configuration
@EnableAsync
public class CommonConfig {
	
	@Bean
	public Gson getGson() {
		Gson gson = null;
		
		try {
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.serializeNulls();
			gsonBuilder.setPrettyPrinting();
			gson = gsonBuilder.create();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return gson;
	}

}
