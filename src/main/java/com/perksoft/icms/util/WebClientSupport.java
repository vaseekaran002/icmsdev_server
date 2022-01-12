package com.perksoft.icms.util;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.UriSpec;
import org.springframework.web.util.UriBuilder;

import com.perksoft.icms.models.WebClientRequest;
import com.perksoft.icms.models.WebClientResponse;

@Component
public class WebClientSupport {

	private WebClient client;

	@Autowired
	public WebClientSupport(@Value("${perksoft.icms.staks-club.endpoints.base-url}") String baseurl) {
		client = WebClient.create(baseurl);
	}

	public void processRequest(WebClientRequest request, WebClientResponse response) {
		UriSpec<RequestBodySpec> uriSpec = client.method(request.getVerb());
		
		RequestBodySpec bodySpec = uriSpec.uri(
				  uriBuilder -> buildUri(uriBuilder, request) );
		
		
		RequestHeadersSpec<?> headersSpec = bodySpec.bodyValue(request.getData())
				.headers(request.getHeaders());

		ClientResponse clientResponse = headersSpec.header(
				HttpHeaders.CONTENT_TYPE, request.getContentType())
				.exchange().block();
		
		response.setStatusCode(clientResponse.statusCode());
		response.setHeaders(clientResponse.headers());
		response.setData(clientResponse.bodyToMono(String.class).block());
		
	}
	
	private URI buildUri(UriBuilder uriBuilder, WebClientRequest request) {
		
		uriBuilder = uriBuilder.path(request.getResourceUrl());
		if(request.getQueryParams() !=null) {
			uriBuilder = uriBuilder.queryParams(request.getQueryParams());
		}
		if(request.getUriVariables() != null) {
			return uriBuilder.build(request.getUriVariables());
		} else {
			return uriBuilder.build();
		}
		
		
	}

}
