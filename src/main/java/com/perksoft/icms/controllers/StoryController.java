package com.perksoft.icms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.perksoft.icms.models.Story;
import com.perksoft.icms.payload.request.StoryRequest;
import com.perksoft.icms.payload.response.MessageResponse;
import com.perksoft.icms.payload.response.StoryResponse;
import com.perksoft.icms.repository.StoryRepository;
import com.perksoft.icms.service.StoryService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/{tenantid}/story") 
public class StoryController {

	@Autowired
	public StoryRepository storyRepository;
	
	@Autowired
	public StoryService storyService;
	
	@PostMapping("/update")
	public ResponseEntity<?> updateStory(@RequestBody StoryRequest storyRequest){
		Story story =  storyService.createStory(storyRequest);
		return ResponseEntity.ok(story);
	}
	
	@GetMapping("/list")
	public ResponseEntity<?> getAllStoriesByPageId(@RequestParam(value = "pageId") String pageId){
		List<StoryResponse> storyResponses =  storyService.getStroiesByPageId(pageId);
		return ResponseEntity.ok(storyResponses);
	}
	
}
