package com.perksoft.icms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.perksoft.icms.models.WebClientResponse;
import com.perksoft.icms.service.MusicianService;

@RestController
public class MusicianController {

	@Autowired
	private MusicianService service;
	
	@GetMapping(value = "/musician/{musicianId}")
	public ResponseEntity<String> getMusician(@PathVariable(name = "musicianId") String musicianId) {
		WebClientResponse response = service.getMUsician(musicianId);
		return new ResponseEntity<String>(response.getData(), response.getStatusCode());
	}
	
	
}
