package com.perksoft.icms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.perksoft.icms.models.Musician;
import com.perksoft.icms.service.MusicianService;

@RestController
public class MusicianController {
	
	@Autowired
	private MusicianService service;
	
	@PostMapping(value = "/musician")
	public ResponseEntity<String> createMusician(@RequestBody Musician musician) 
			throws JsonProcessingException 
	{
		return service.createMusician(musician);		
	}
	
	@GetMapping(value = "/musician/{musicianId}")
	public ResponseEntity<String> getMusicianByRadaptiveId(@PathVariable(name = "musicianId") String musicianId) 
			throws JsonProcessingException 
	{
		return service.getMusicianByRadaptiveId(musicianId);		
	}
	
	@GetMapping(value = "/musician")
	public ResponseEntity<String> getMusician(
			@RequestParam(name = "staksId", required = false) String staksId,
			@RequestParam(name = "artistName", required = false) String artistName,
			@RequestParam(name = "location", required = false) String location,
			@RequestParam(name = "genre", required = false) String genre
			) 
			throws JsonProcessingException 
	{
		return service.getMusician(staksId, artistName, location, genre);		
	}
	
	@GetMapping(value = "/musician/{musicianId}/members")
	public ResponseEntity<String> getMusicianMembers(@PathVariable(name = "musicianId") String musicianId) 
			throws JsonProcessingException 
	{
		return service.getMusicianMembers(musicianId);		
	}
	
	@PostMapping(value = "/musician/{musicianId}/members/{memberId}")
	public ResponseEntity<String> newMusicianMembers(@PathVariable(name = "musicianId") String musicianId, @PathVariable(name = "memberId") String memberId) 
			throws JsonProcessingException 
	{
		return service.newMusicianMembers(musicianId, memberId);		
	}
	
	
}
