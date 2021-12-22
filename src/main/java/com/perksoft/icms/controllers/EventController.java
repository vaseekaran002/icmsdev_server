package com.perksoft.icms.controllers;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perksoft.icms.payload.request.EventRequest;
import com.perksoft.icms.payload.response.APIResponse;
import com.perksoft.icms.payload.response.BandEventResponse;
import com.perksoft.icms.payload.response.EventResponse;
import com.perksoft.icms.repository.EventRepository;
import com.perksoft.icms.service.EventService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/{tenantid}/events")
public class EventController {

	@Autowired
	public EventRepository eventRepository;

	@Autowired
	public EventService eventService;

	@PostMapping("/update")
	public ResponseEntity<?> updateEvent(@PathVariable("tenantid") String tenantId,
			@RequestBody EventRequest eventRequest) throws ParseException {
		eventRequest.setTenantId(UUID.fromString(tenantId));
		// should get pageId in pathvariable or requestBody?
		EventResponse eventResponse = eventService.updateEvent(eventRequest);
		return ResponseEntity.ok(eventResponse);
	}

	@GetMapping("/list")
	public ResponseEntity<?> getAllEvents(@PathVariable("tenantid") String tenantId) {
		List<EventResponse> eventList = eventService.getAllEventsByTenantId(tenantId);
		return ResponseEntity.ok(eventList);
	}

	@GetMapping("/bands")
	public ResponseEntity<?> getAllBandInTownEvents(@PathVariable("tenantid") String tenantId) {
		BandEventResponse bandEventResponse = eventService.getBandEvents("", "");
		APIResponse apiResponse = new APIResponse();
		apiResponse.setData(bandEventResponse);
		apiResponse.setMessage("Success");
		return ResponseEntity.ok(apiResponse);
	}

	@GetMapping("/{eventId}")
	public ResponseEntity<?> getEvevtById(@PathVariable("eventId") Long eventId) {
		EventResponse event = eventService.getEventById(eventId);
		return ResponseEntity.ok(event);
	}

}
