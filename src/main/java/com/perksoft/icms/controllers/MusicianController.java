package com.perksoft.icms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.perksoft.icms.contants.Constants;
import com.perksoft.icms.models.Contract;
import com.perksoft.icms.models.Invoice;
import com.perksoft.icms.models.Musician;
import com.perksoft.icms.service.MusicianService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
@Api(value = "Musician service")
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
			@RequestParam(name = "city", required = false) String city,
			@RequestParam(name = "genre", required = false) String genre
			) 
			throws JsonProcessingException 
	{
		return service.getMusician(staksId, artistName, city, genre);		
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
	
	@PostMapping(value = "/musician/{musicianId}/contract")
	public ResponseEntity<String> createContract(@PathVariable(name = "musicianId") String musicianId,
			@RequestBody Contract contract) 
			throws JsonProcessingException 
	{
		return service.createContract(musicianId, contract);		
	}
	
	@GetMapping(value = "/musician/{musicianId}/contract/{contractId}")
	public ResponseEntity<String> getContract(@PathVariable(name = "musicianId") String musicianId,
			@PathVariable(name = "contractId") String contractId) 
			throws JsonProcessingException 
	{
		return service.getContract(musicianId, contractId);		
	}
	
	@PostMapping(value = "/musician/{musicianId}/invoice")
	public ResponseEntity<String> createIvoice(@PathVariable(name = "musicianId") String musicianId,
			@RequestBody Invoice invoice) 
			throws JsonProcessingException 
	{
		return service.createInvoice(musicianId, invoice);		
	}
	
	@GetMapping(value = "/musician/{musicianId}/invoice/{invoiceId}")
	public ResponseEntity<String> getInvoice(@PathVariable(name = "musicianId") String musicianId,
			@PathVariable(name = "invoiceId") String invoiceId) 
			throws JsonProcessingException 
	{
		return service.getInvoice(musicianId, invoiceId);		
	}
	
	@GetMapping(value = "/musician/{musicianId}/invoices")
	public ResponseEntity<String> getMusicianInvoices(@PathVariable(name = "musicianId") String musicianId) 
			throws JsonProcessingException 
	{
		return service.getMusicianRelatedRecords(musicianId, Constants.RECORD_TYPE_INVOICE);		
	}
	
	@GetMapping(value = "/musician/{musicianId}/contracts")
	public ResponseEntity<String> getMusicianContracts(@PathVariable(name = "musicianId") String musicianId) 
			throws JsonProcessingException 
	{
		return service.getMusicianRelatedRecords(musicianId, Constants.RECORD_TYPE_CONTRACT);	
	}
	
}
