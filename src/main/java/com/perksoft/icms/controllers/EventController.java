package com.perksoft.icms.controllers;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perksoft.icms.contants.Constants;
import com.perksoft.icms.exception.IcmsCustomException;
import com.perksoft.icms.payload.request.EventRequest;
import com.perksoft.icms.payload.response.APIResponse;
import com.perksoft.icms.payload.response.BandEventResponse;
import com.perksoft.icms.payload.response.EventResponse;
import com.perksoft.icms.repository.EventRepository;
import com.perksoft.icms.service.EventService;
import com.perksoft.icms.util.CommonUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/{tenantid}/events")
public class EventController {

	private static final Logger LOGGER=LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	public EventRepository eventRepository;

	@Autowired
	public EventService eventService;

	@ApiOperation(value = "Used to update Events for a Page", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updates commnets"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@PostMapping("/update")
	public ResponseEntity<?> updateEvent(@PathVariable("tenantid") String tenantId,
			@RequestBody EventRequest eventRequest) throws ParseException {
		LOGGER.info("Started Creating/Updating Event for tenant {}", tenantId);
		ResponseEntity<String> responseEntity = null;
		try {
			eventRequest.setTenantId(UUID.fromString(tenantId));
			EventResponse eventResponse = eventService.updateEvent(eventRequest);
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
					eventResponse);
		}
		catch(IcmsCustomException e) {
			LOGGER.info("Error occurred while Creating/Updating Event {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			LOGGER.info("Error occurred while Creating/Updating Event {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		LOGGER.info("End of Creating/Updating Event and response {}", responseEntity);
		return responseEntity;
	}

	@GetMapping("/list")
	public ResponseEntity<?> getAllEvents(@PathVariable("tenantid") String tenantId) {
		LOGGER.info("Started fetching Event for tenant {}", tenantId);
		ResponseEntity<String> responseEntity = null;
		try {
			List<EventResponse> eventList = eventService.getAllEventsByTenantId(tenantId);
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
					eventList);
		}
		catch(IcmsCustomException e) {
			LOGGER.info("Error occurred while fetching Event {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			LOGGER.info("Error occurred while fetching Event {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		LOGGER.info("End of fetching Event and response {}", responseEntity);
		return responseEntity;
	}

	@GetMapping("/bands")
	public ResponseEntity<?> getAllBandInTownEvents(@PathVariable("tenantid") String tenantId) {
		LOGGER.info("Started fetching Band for tenant {}", tenantId);
		ResponseEntity<String> responseEntity = null;
		try {
			BandEventResponse bandEventResponse = eventService.getBandEvents("", "");
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
					bandEventResponse);
		}
		catch(IcmsCustomException e) {
			LOGGER.info("Error occurred while fetching Band {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			LOGGER.info("Error occurred while fetching Band {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		LOGGER.info("End of fetching Band and response {}", responseEntity);
		return responseEntity;
	}

	@GetMapping("/{eventId}")
	public ResponseEntity<?> getEvevtById(@PathVariable("eventId") Long eventId) {
		LOGGER.info("Started fetching  event {}", eventId);
		ResponseEntity<String> responseEntity = null;
		try {
			EventResponse event = eventService.getEventById(eventId);
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
					event);
		}
		catch(IcmsCustomException e) {
			LOGGER.info("Error occurred while fetching event {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			LOGGER.info("Error occurred while fetching event {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		LOGGER.info("End of fetching event and response {}", responseEntity);
		return responseEntity;
	}

}
