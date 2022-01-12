package com.perksoft.icms.models;

import java.util.Map;
import java.util.function.Consumer;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WebClientRequest {
	
	private HttpMethod verb;
	private String resourceUrl;
	private String contentType;
	private Map<String, String> uriVariables;
	private MultiValueMap<String, String> queryParams;
	private Consumer<HttpHeaders> headers;
	private String data;
	
}
