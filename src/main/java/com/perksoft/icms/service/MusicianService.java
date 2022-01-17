package com.perksoft.icms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;
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
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.perksoft.icms.contants.Constants;
import com.perksoft.icms.models.Contract;
import com.perksoft.icms.models.Musician;
import com.perksoft.icms.models.WebClientRequest;
import com.perksoft.icms.models.WebClientResponse;
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
	
	@Value("${perksoft.icms.staks-club.endpoints.musician-new}")
	private String musicianNewResourceUrl;
	
	@Value("${perksoft.icms.staks-club.endpoints.musician-update}")
	private String musicianUpdateResourceUrl;
	
	@Value("${perksoft.icms.staks-club.endpoints.musician-info}")
	private String musicianInfoResourceUrl;
	
	@Value("${perksoft.icms.staks-club.endpoints.musician-by-filter}")
	private String musicianByFilterResourceUrl;
	
	@Value("${perksoft.icms.staks-club.endpoints.musician-members}")
	private String musicianMembersResourceUrl;
	
	@Value("${perksoft.icms.staks-club.endpoints.musician-subdtls-modify}")
	private String musicianSubDetailsModifyResourceUrl;
	
	@Value("${perksoft.icms.staks-club.endpoints.contract-create}")
	private String contractCreateResourceUrl;
  
	@Value("${perksoft.icms.staks-club.endpoints.contract-view}")
	private String contractViewResourceUrl;
	
	private static final String FORMNAME_MUSICIAN = "Musician";
	
	private static final String FORMNAME_CONTRACT = "Contract";
	
	private static final String ACTION_CREATE = "CREATE";
	
	public ResponseEntity<String> createMusician(Musician musician) throws JsonProcessingException {
		WebClientRequest request;
		String requestBody = mapRequest(musician);
		WebClientResponse response = new WebClientResponse();
		Consumer<HttpHeaders> requestHeaders = httpHeaders -> addRequestHeaders(httpHeaders);
		
		if(StringUtils.isNotBlank(musician.getRadaptiveId())) {
			Map<String, String> uriParams = new HashMap<>();
			uriParams.put("musicianId", musician.getRadaptiveId());
			
			request = new WebClientRequest(
					HttpMethod.PUT,
					musicianUpdateResourceUrl, 
					MediaType.APPLICATION_JSON_VALUE, 
					uriParams,
					null,
					requestHeaders, 
					requestBody);
		} else {
			request = new WebClientRequest(
					HttpMethod.POST,
					musicianNewResourceUrl, 
					MediaType.APPLICATION_JSON_VALUE, 
					null,
					null,
					requestHeaders, 
					requestBody);			
		}
		
		webClientSupport.processRequest(request, response);
		
		return commonUtil.generateEntityResponse("musicians", Constants.SUCCESS, "");
	}

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
	
	public ResponseEntity<String> getMusician(String staksId, String artistName, String city, String genre) throws JsonProcessingException {
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
		
		List<String> filters = new ArrayList<>();
		
		if(StringUtils.isNotBlank(staksId)) {
			filters.add(String.format("sid=%s", staksId));
		} if(StringUtils.isNotBlank(artistName)) {
			filters.add(String.format("artistName=%s", artistName));
		} if(StringUtils.isNotBlank(city)) {
			filters.add(String.format("city=%s", city));
		} if(StringUtils.isNotBlank(genre)) {
			filters.add(String.format("genre=%s", genre));
		}
		
		queryParams.add("filter", StringUtils.join(filters, ","));

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
	
	public ResponseEntity<String> newMusicianMembers(String musicianId, String memberId) throws JsonProcessingException {
		String requestBody = mapRequest(ACTION_CREATE, musicianId, FORMNAME_MUSICIAN, memberId);
		Consumer<HttpHeaders> requestHeaders = httpHeaders -> addRequestHeaders(httpHeaders);
		
		WebClientRequest request = new WebClientRequest(
				HttpMethod.POST,
				musicianSubDetailsModifyResourceUrl, 
				MediaType.APPLICATION_JSON_VALUE, 
				null,
				null,
				requestHeaders, 
				requestBody);
		WebClientResponse response = new WebClientResponse();
		
		webClientSupport.processRequest(request, response);
		
		return commonUtil.generateEntityResponse("musicians", Constants.SUCCESS, "");
	}
	
	public ResponseEntity<String> createContract(String musicianId, Contract contractRequest) throws JsonProcessingException {
		
		String requestBody = objectMapper.writeValueAsString(contractRequest);
		
		Consumer<HttpHeaders> requestHeaders = httpHeaders -> addRequestHeaders(httpHeaders);
		
		WebClientRequest request = new WebClientRequest(
				HttpMethod.POST,
				contractCreateResourceUrl, 
				MediaType.APPLICATION_JSON_VALUE, 
				null,
				null,
				requestHeaders, 
				requestBody);
		WebClientResponse response = new WebClientResponse();
		
		webClientSupport.processRequest(request, response);
		
		JsonNode jsonNode = objectMapper.readTree(response.getData());
		
		linkContract(musicianId, jsonNode.get("contractId").asText());
		
		return commonUtil.generateEntityResponse("musicians", Constants.SUCCESS, "");
	}
	
	public ResponseEntity<String> getContract(String musicianId, String contractId) throws JsonProcessingException {
		Map<String, String> uriParams = new HashMap<>();
		uriParams.put("contractId", contractId);

		Consumer<HttpHeaders> requestHeaders = httpHeaders -> addRequestHeaders(httpHeaders);
		
		WebClientRequest request = new WebClientRequest(
				HttpMethod.GET,
				contractViewResourceUrl, 
				MediaType.APPLICATION_JSON_VALUE, 
				uriParams,
				null,
				requestHeaders, 
				"");
		WebClientResponse response = new WebClientResponse();
		
		webClientSupport.processRequest(request, response);
		
		JsonNode jsonNode = objectMapper.readTree(response.getData());
		return commonUtil.generateEntityResponse("musicians", Constants.SUCCESS, mapContractResponse(jsonNode));
	}
	
	private void linkContract(String musicianId, String contractId) throws JsonProcessingException {
		String requestBody = mapRequest(ACTION_CREATE, musicianId, FORMNAME_CONTRACT, contractId);
		Consumer<HttpHeaders> requestHeaders = httpHeaders -> addRequestHeaders(httpHeaders);
		
		WebClientRequest request = new WebClientRequest(
				HttpMethod.POST,
				musicianSubDetailsModifyResourceUrl, 
				MediaType.APPLICATION_JSON_VALUE, 
				null,
				null,
				requestHeaders, 
				requestBody);
		WebClientResponse response = new WebClientResponse();
		
		webClientSupport.processRequest(request, response);
	}
	
	private Musician mapResponse(JsonNode jsonNode) throws JsonProcessingException {
		
		Musician response = new Musician();
		response.setRadaptiveId(jsonNode.get("id").asText());
		response.setStaksPayId(jsonNode.get("sid").asText());
		response.setUserName(jsonNode.get("userName").asText());
		response.setArtistName(jsonNode.get("artistName").asText());
		response.setFacebookLink(jsonNode.get("facebookLink").asText());
		response.setHometown(jsonNode.get("hometown").asText());
		response.setGenres(jsonNode.get("genres").asText());
		
		return response;
	}
	
	private List<Musician> mapResponses(JsonNode jsonNodes) throws JsonProcessingException {
		List<Musician> response = new ArrayList<>();
		for (JsonNode node : jsonNodes) {
			response.add(mapResponse(node));
		}
		
		return response;
	}
	
	private void addRequestHeaders(HttpHeaders httpHeaders) {
		httpHeaders.add("Authorization", "Basic U3Rha3NQYXJ0bmVyLUFkbWluOmE0OTM4OGFkMw==");
	}
	
	private String mapRequest(Musician musician) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

	    ObjectNode request = mapper.createObjectNode();
	    request.put("sid", musician.getStaksPayId());
	    request.put("userName", musician.getUserName());
	    request.put("artistName", musician.getArtistName());
	    request.put("emailAddress1", musician.getEmailAddress());
	    request.put("facebookLink", musician.getFacebookLink());
	    request.put("streetAddress", musician.getStreetAddress());
	    request.put("hometown", musician.getHometown());
	    request.put("city", musician.getCity());
	    request.put("state", musician.getState());

	    return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
	}
	
	private String mapRequest(String action, String musicianId, String formName, String relationId) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

	    ObjectNode request = mapper.createObjectNode();
	    request.put("action", action);
	    request.put("formName", formName);
	    request.put("relType", "Ticket");
	    request.put("relatedIds", relationId);
	    request.put("ticketId", musicianId);

	    return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
		
	}
	
	private Contract mapContractResponse(JsonNode jsonNode) {
		Contract response = new Contract();
		
		response.setTitle(jsonNode.get("title").asText());
		response.setTimeZone(jsonNode.get("timeZone").asText());
		response.setVenue(jsonNode.get("venue").asText());
		response.setFees(jsonNode.get("fees").asText());
		response.setChannelName(jsonNode.get("channelName").asText());
		
		return response;
		
	}
	
}
