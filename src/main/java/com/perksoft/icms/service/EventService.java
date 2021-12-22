package com.perksoft.icms.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perksoft.icms.models.BandInTownEvent;
import com.perksoft.icms.models.Event;
import com.perksoft.icms.payload.request.EventRequest;
import com.perksoft.icms.payload.response.BandEventResponse;
import com.perksoft.icms.payload.response.EventResponse;
import com.perksoft.icms.repository.EventRepository;
import com.perksoft.icms.repository.UserRepository;

@Service
public class EventService {

	@Autowired
	public EventRepository eventRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	public BandinTownWebClient bandinTownWebClient;

	public EventResponse updateEvent(EventRequest eventRequest) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Event newEvent = new Event();
		newEvent.setId(eventRequest.getId());
		newEvent.setName(eventRequest.getName());
		newEvent.setDescription(eventRequest.getDescription());
		newEvent.setStartDate(format.parse(eventRequest.getStartDate()));
		newEvent.setEndDate(format.parse(eventRequest.getEndDate()));
		newEvent.setCreatedBy(userRepository.findById(eventRequest.getCreatedBy()).get());
		newEvent.setPageId(eventRequest.getPageId());
		newEvent.setTenantId(eventRequest.getTenantId());
		eventRepository.save(newEvent);
		return getEventById(newEvent.getId());
	}

	public List<EventResponse> getAllEventsByTenantId(String tenantId) {
		List<Event> eventList = eventRepository.findAllByTenantId(UUID.fromString(tenantId));
		List<EventResponse> eventResponses = converToResponse(eventList);
		return eventResponses;
	}

	public EventResponse getEventById(Long eventId) {
		Optional<Event> existingEvent = eventRepository.findById(eventId);
		EventResponse newEvent = new EventResponse();
		newEvent.setId(existingEvent.get().getId());
		newEvent.setName(existingEvent.get().getName());
		newEvent.setDescription(existingEvent.get().getDescription());
		newEvent.setStartDate(existingEvent.get().getStartDate());
		newEvent.setEndDate(existingEvent.get().getEndDate());
		newEvent.setCreatedBy(existingEvent.get().getCreatedBy());
		newEvent.setPageId(existingEvent.get().getPageId());
		newEvent.setTenantId(existingEvent.get().getTenantId());
		return newEvent;

	}

	public List<EventResponse> converToResponse(List<Event> events) {
		List<EventResponse> eventResponses = new ArrayList<>();
		eventResponses = events.stream().map(e -> {
			EventResponse newEvent = new EventResponse();
			newEvent.setId(e.getId());
			newEvent.setName(e.getName());
			newEvent.setDescription(e.getDescription());
			newEvent.setStartDate(e.getStartDate());
			newEvent.setEndDate(e.getEndDate());
			newEvent.setCreatedBy(e.getCreatedBy());
			newEvent.setPageId(e.getPageId());
			newEvent.setTenantId(e.getTenantId());
			return newEvent;
		}).collect(Collectors.toList());
		return eventResponses;
	}

	public BandEventResponse getBandEvents(String eventType, String artistName) {
		BandEventResponse bandEventResponse = new BandEventResponse();
		List<BandInTownEvent> bandsEvents = bandinTownWebClient.getEventsFromBandsInTown();

		for (BandInTownEvent bandInTownEvent : bandsEvents) {
			System.out.println("===bands datetime===" + bandInTownEvent.getDatetime());

		}
		bandEventResponse.setPast(bandsEvents);
		bandEventResponse.setUpcoming(bandsEvents);
		return bandEventResponse;
	}

}
