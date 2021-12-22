package com.perksoft.icms.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
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

import com.perksoft.icms.models.Page;
import com.perksoft.icms.models.User;
import com.perksoft.icms.payload.request.PageFollowerRequest;
import com.perksoft.icms.payload.request.PageRequest;
import com.perksoft.icms.payload.response.PageResponse;
import com.perksoft.icms.repository.PageRepository;
import com.perksoft.icms.service.PageService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/{tenantid}/pages")
public class PageController {

	@Autowired
	public PageService pageService;

	@Autowired
	public PageRepository pageRepository;

	@PostMapping("/update")
	public ResponseEntity<?> updatePage(@PathVariable("tenantid") String tenantId,
			@RequestBody PageRequest pageRequest) {
		pageRequest.setTenantId(UUID.fromString(tenantId));
		PageResponse pageResponse = pageService.updatePage(pageRequest);
		return ResponseEntity.ok(pageResponse);
	}

	@GetMapping("/{pageId}")
	public ResponseEntity<?> getPageById(@PathVariable("pageId") String pageId) {
		PageResponse pageResponse = pageService.getPageById(pageId);
		return ResponseEntity.ok(pageResponse);
	}

	@GetMapping("/list")
	public ResponseEntity<?> getPagesByTenantId(@PathVariable("tenantid") String tenantId) {
		List<PageResponse> pageResponses = pageService.getAllPageByTenantId(tenantId);
		return ResponseEntity.ok(pageResponses);
	}

	@GetMapping("/{userId}/list")
	public ResponseEntity<?> getPagesByUserId(@PathVariable(name = "userId") String userId) {
		List<PageResponse> pageResponses = pageService.getAllPageByUserId(userId);
		return ResponseEntity.ok(pageResponses);
	}

	@PostMapping("/{pageId}/followers/update")
	public ResponseEntity<?> followRequest(@PathVariable("pageId") String pageId,@RequestBody PageFollowerRequest pageFollowerRequest) {
		return pageService.followerRequest(pageId, pageFollowerRequest);
	}

	@GetMapping("/{pageId}/followers")
	public List<User> getTopFollwers(@PathVariable("pageId") String pageId) {
		List<User> topUsers = new ArrayList<User>();
		Optional<Page> existingPage = pageRepository.findById(UUID.fromString(pageId));
		Iterator<User> iter = existingPage.get().getFollowers().iterator();
		int i = 0;

		while (i < 10 && iter.hasNext()) {
			topUsers.add(iter.next());
			i++;
		}
		return topUsers;
	}
	
	

}
