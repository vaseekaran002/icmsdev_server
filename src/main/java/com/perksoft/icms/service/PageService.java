package com.perksoft.icms.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.perksoft.icms.models.Page;
import com.perksoft.icms.models.User;
import com.perksoft.icms.payload.request.PageFollowerRequest;
import com.perksoft.icms.payload.request.PageRequest;
import com.perksoft.icms.payload.response.MessageResponse;
import com.perksoft.icms.payload.response.PageResponse;
import com.perksoft.icms.repository.PageRepository;
import com.perksoft.icms.repository.UserRepository;

@Service
public class PageService {

	@Autowired
	public PageRepository pageRepository;

	@Autowired
	private UserRepository userRepository;

	public PageResponse updatePage(PageRequest pageRequest) {
//		File file = new File("D:\\photos\\college photos\\sem 1\\IMG_20191129_132254.jpg");
//		byte[] picInBytes = new byte[(int) file.length()];
		Page page = new Page();
		page.setTenantId(pageRequest.getTenantId());
		page.setId(pageRequest.getId());
		page.setName(pageRequest.getName());
		page.setLogo(pageRequest.getLogo());
		page.setDescription(pageRequest.getDescription());
		page.setBanner(pageRequest.getBanner());
		page.setCreatedBy(userRepository.findById(pageRequest.getCreatedBy()).get());
		page.setStatus(pageRequest.getStatus());
		pageRepository.save(page);
		return getPageById(page.getId().toString());
	}

	public PageResponse getPageById(String pageId) {
		Optional<Page> existingPage = pageRepository.findById(UUID.fromString(pageId));

		PageResponse pageResponse = new PageResponse();
		pageResponse.setId(existingPage.get().getId());
		pageResponse.setName(existingPage.get().getName());
		pageResponse.setDescription(existingPage.get().getDescription());
		pageResponse.setLogo(existingPage.get().getLogo());
		pageResponse.setBanner(existingPage.get().getBanner());
		pageResponse.setCreateBy(existingPage.get().getCreatedBy());
		pageResponse.setStatus(existingPage.get().getStatus());
		pageResponse.setFollowersCount(Long.valueOf(existingPage.get().getFollowers().size()));
		return pageResponse;
	}

	public List<PageResponse> getAllPageByTenantId(String tenantId) {
		List<Page> pages = pageRepository.findAllByTenantId(UUID.fromString(tenantId));
		List<PageResponse> pageResponses = converToResponse(pages);
		return pageResponses;
	}

	public List<PageResponse> getAllPageByUserId(String userId) {
		Optional<User> existingUser = userRepository.findById(UUID.fromString(userId));
		List<Page> pages = pageRepository.findAllByCreatedBy(existingUser.get());
		List<PageResponse> pageResponses = converToResponse(pages);
		return pageResponses;
	}

	public ResponseEntity<?> followerRequest(String pageId,PageFollowerRequest pageFollowerRequest) {
		if (isFollowed(pageId,pageFollowerRequest.getCurrentFollowerId())) {
			return ResponseEntity.ok(new MessageResponse("you already are a follower of this page "));
		} else {
			Optional<Page> existingPage = pageRepository.findById(UUID.fromString(pageId));
			Optional<User> existingUser = userRepository.findById(pageFollowerRequest.getCurrentFollowerId());
			existingPage.get().getFollowers().add(existingUser.get());
			pageRepository.save(existingPage.get());
			return ResponseEntity.ok(new MessageResponse("you started following this page"));

		}
	}

	public boolean isFollowed(String pageId, UUID userId) {
		Optional<Page> existingPage = pageRepository.findById(UUID.fromString(pageId));
		Iterator<User> iter = existingPage.get().getFollowers().iterator();
		while (iter.hasNext()) {
			if (iter.next().getId() == userId) {
				return true;
			}

		}
		return false;
	}

	public List<PageResponse> converToResponse(List<Page> pages) {
		List<PageResponse> pageResponses = new ArrayList<>();
		pageResponses = pages.stream().map(p -> {
			PageResponse pageResponse = new PageResponse();
			pageResponse.setId(p.getId());
			pageResponse.setName(p.getName());
			pageResponse.setLogo(p.getLogo());
			pageResponse.setDescription(p.getDescription());
			pageResponse.setBanner(p.getBanner());
			pageResponse.setCreateBy(p.getCreatedBy());
			pageResponse.setTenantId(p.getTenantId());
			pageResponse.setStatus(p.getStatus());
			pageResponse.setFollowersCount(Long.valueOf(p.getFollowers().size()));
			return pageResponse;
		}).collect(Collectors.toList());
		return pageResponses;
	}

}
