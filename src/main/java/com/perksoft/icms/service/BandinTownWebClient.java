package com.perksoft.icms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perksoft.icms.models.BandInTownEvent;

@Component
public class BandinTownWebClient {

	private final WebClient client;

	private ObjectMapper objectMapper;

	public BandinTownWebClient(WebClient.Builder builder) {
		this.client = builder.baseUrl("https://rest.bandsintown.com/artists").build();
	}

	public List<BandInTownEvent> getEventsFromBandsInTown() {
		String jsonString = this.client.get().uri("/Jeverson/events?app_id=2b752db3edfcd5f8743cdfe0e073bd26&date=past")
				.accept(MediaType.APPLICATION_JSON).exchange().block().bodyToMono(String.class).block();
		List<BandInTownEvent> eventsList = new ArrayList<>();
		
		try {
			eventsList = objectMapper.readValue(jsonString, new TypeReference<List<BandInTownEvent>>(){});
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Events size====" + eventsList.size());
		return eventsList;
	}
}
