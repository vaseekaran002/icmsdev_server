package com.perksoft.icms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.perksoft.icms.service.MusicianService;

@RestController
public class MusicianController {
	
	@Autowired
	private MusicianService service;
	
	@GetMapping(value = "/musician/{radaptiveId}")
	public ResponseEntity<String> getMusicianByRadaptiveId(@PathVariable(name = "radaptiveId") String musicianId) 
			throws JsonProcessingException 
	{
		return service.getMusicianByRadaptiveId(musicianId);		
	}
	
	@GetMapping(value = "/musician/staks/{staksId}")
	public ResponseEntity<String> getMusicianByStaksId(@PathVariable(name = "staksId") String staksId) 
			throws JsonMappingException, JsonProcessingException 
	{
		return service.getMusicianByStaksId(staksId);		
	}
	
	
}
