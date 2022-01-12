package com.perksoft.icms.models;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse.Headers;

import lombok.Data;

@Data
public class WebClientResponse {
	
	private HttpStatus statusCode;
	private Headers headers;
	private String data;
	
}
