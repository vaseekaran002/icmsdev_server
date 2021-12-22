package com.perksoft.icms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.perksoft.icms.models.BandInTownEvent;

@Component
public class BandinTownWebClient {

	private final WebClient client;

	@Autowired
	private Gson gson;

	public BandinTownWebClient(WebClient.Builder builder) {
		this.client = builder.baseUrl("https://rest.bandsintown.com/artists").build();
	}

	public List<BandInTownEvent> getEventsFromBandsInTown() {
		String jsonString = this.client.get().uri("/Jeverson/events?app_id=2b752db3edfcd5f8743cdfe0e073bd26&date=past")
				.accept(MediaType.APPLICATION_JSON).exchange().block().bodyToMono(String.class).block();
		System.out.println("===jsonString===" + jsonString);
		List<BandInTownEvent> eventsList = gson.fromJson(jsonString, new TypeToken<List<BandInTownEvent>>() {
		}.getType());
		System.out.println("Events size====" + eventsList.size());
		return eventsList;
	}
}
