package com.perksoft.icms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
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
	
	@Value("${perksoft.icms.staks-club.endpoints.musician-by-filter}")
	private String musicianByFilterResourceUrl;
	
	@Value("${perksoft.icms.staks-club.endpoints.musician-members}")
	private String musicianMembersResourceUrl;
  
	public ResponseEntity<String> getMusicianByRadaptiveId(String musicianId) throws JsonProcessingException {
		
		Map<String, String> uriParams = new HashMap<>();
		uriParams.put("musicianId", musicianId);

		Consumer<HttpHeaders> requestHeaders = httpHeaders -> addRequestHeaders(httpHeaders);
		
		WebClientRequest request = new WebClientRequest(
				HttpMethod.GET,
				musicianInfoResourceUrl, 
				MediaType.APPLICATION_JSON_VALUE, 
				uriParams,
				null,
				requestHeaders, 
				"");
		WebClientResponse response = new WebClientResponse();
		
		webClientSupport.processRequest(request, response);
		
		JsonNode jsonNode = objectMapper.readTree(response.getData());
		return commonUtil.generateEntityResponse("musicians", Constants.SUCCESS, mapResponse(jsonNode));
	}
	
	public ResponseEntity<String> getMusicianByStaksId(String staksId) throws JsonProcessingException {
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
		queryParams.add("filter", String.format("sid=%s", staksId));

		Consumer<HttpHeaders> requestHeaders = httpHeaders -> httpHeaders.add("Authorization", "Basic RmFuT25lOkZhbjEyMyFAIw==");
		
		WebClientRequest request = new WebClientRequest(
				HttpMethod.GET,
				musicianByFilterResourceUrl, 
				MediaType.APPLICATION_JSON_VALUE, 
				null,
				queryParams,
				requestHeaders, 
				"");
		WebClientResponse response = new WebClientResponse();
		
		webClientSupport.processRequest(request, response);
		
		JsonNode jsonNode = objectMapper.readTree(response.getData());
		return commonUtil.generateEntityResponse("musicians", Constants.SUCCESS, mapResponse(jsonNode));
	}
	
	public ResponseEntity<String> getMusicianMembers(String musicianId) throws JsonProcessingException {
		Map<String, String> uriParams = new HashMap<>();
		uriParams.put("musicianId", musicianId);

		Consumer<HttpHeaders> requestHeaders = httpHeaders -> addRequestHeaders(httpHeaders);
		
		WebClientRequest request = new WebClientRequest(
				HttpMethod.GET,
				musicianMembersResourceUrl, 
				MediaType.APPLICATION_JSON_VALUE, 
				uriParams,
				null,
				requestHeaders, 
				"");
		WebClientResponse response = new WebClientResponse();
		
		webClientSupport.processRequest(request, response);
		
		JsonNode jsonNode = objectMapper.readTree(response.getData());
		return commonUtil.generateEntityResponse("musicians", Constants.SUCCESS, mapResponses(jsonNode));
	}
	
	private MusicianResponse mapResponse(JsonNode jsonNode) throws JsonProcessingException {
		
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
	
	private List<MusicianResponse> mapResponses(JsonNode jsonNodes) throws JsonProcessingException {
		List<MusicianResponse> response = new ArrayList<>();
		for (JsonNode node : jsonNodes) {
			response.add(mapResponse(node));
		}
		
		return response;
	}
	
	private void addRequestHeaders(HttpHeaders httpHeaders) {
		httpHeaders.add("Authorization", "Basic U3Rha3NQYXJ0bmVyLUFkbWluOmE0OTM4OGFkMw==");
	}
	
}
