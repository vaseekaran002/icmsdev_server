package com.perksoft.icms.service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perksoft.icms.contants.Constants;
import com.perksoft.icms.models.WebClientRequest;
import com.perksoft.icms.models.WebClientResponse;
import com.perksoft.icms.payload.response.MusicianResponse;
import com.perksoft.icms.util.CommonUtil;
import com.perksoft.icms.util.WebClientSupport;

@Service
public class MusicianService {
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private WebClientSupport webClientSupport;
	
	@Value("${perksoft.icms.staks-club.endpoints.musician-info}")
	private String musicianInfoResourceUrl;
	
	public ResponseEntity<String> getMusician(String musicianId) throws JsonMappingException, JsonProcessingException {
		
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
		
		return commonUtil.generateEntityResponse("musicians", Constants.SUCCESS, mapResponse(response.getData()));
	}
	
	private MusicianResponse mapResponse(String data) throws JsonMappingException, JsonProcessingException {
		JsonNode jsonNode = objectMapper.readTree(data);
		
		MusicianResponse response = new MusicianResponse();
		response.setRadaptiveId(jsonNode.get("id").asText());
		response.setStaksPayId(jsonNode.get("sid").asText());
		response.setUserName(jsonNode.get("userName").asText());
		response.setArtistName(jsonNode.get("artistName").asText());
		response.setFacebookLink(jsonNode.get("facebookLink").asText());
		response.setHometown(jsonNode.get("hometown").asText());
		response.setGenres(jsonNode.get("genres").asText());
		
		return response;
	}

	
	
}
