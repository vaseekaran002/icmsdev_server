package com.perksoft.icms.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.UriSpec;

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
				  uriBuilder -> uriBuilder.path(request.getResourceUrl()).build(request.getUriVariables()));
		
		
		RequestHeadersSpec<?> headersSpec = bodySpec.bodyValue(request.getData())
				.headers(request.getHeaders());

		ClientResponse clientResponse = headersSpec.header(
				HttpHeaders.CONTENT_TYPE, request.getContentType())
				.exchange().block();
		
		response.setStatusCode(clientResponse.statusCode());
		response.setHeaders(clientResponse.headers());
		response.setData(clientResponse.bodyToMono(String.class).block());
		
	}

}
