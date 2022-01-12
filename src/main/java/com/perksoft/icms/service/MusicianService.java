package com.perksoft.icms.service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.perksoft.icms.models.WebClientRequest;
import com.perksoft.icms.models.WebClientResponse;
import com.perksoft.icms.util.WebClientSupport;

@Service
public class MusicianService {

	@Autowired
	private WebClientSupport webClientSupport;
	
	@Value("${perksoft.icms.staks-club.endpoints.musician-info}")
	private String musicianInfoResourceUrl;
	
	public WebClientResponse getMUsician(String musicianId) {
		
		Map<String, String> uriParams = new HashMap<String, String>();
		uriParams.put("musicianId", musicianId);

		Consumer<HttpHeaders> requestHeaders = httpHeaders -> httpHeaders.add("Authorization", "Basic RmFuT25lOkZhbjEyMyFAIw==");
		
		WebClientRequest request = new WebClientRequest(
				HttpMethod.GET,
				musicianInfoResourceUrl, 
				MediaType.APPLICATION_JSON_VALUE, 
				uriParams,
				requestHeaders, 
				"");
		WebClientResponse response = new WebClientResponse();
		
		webClientSupport.processRequest(request, response);
		
		return response;
		
	}

	
	
}
