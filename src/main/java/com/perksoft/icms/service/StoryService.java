package com.perksoft.icms.service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perksoft.icms.models.Story;
import com.perksoft.icms.payload.request.StoryRequest;
import com.perksoft.icms.payload.response.StoryResponse;
import com.perksoft.icms.repository.StoryRepository;

@Service
public class StoryService {

	@Autowired
	public StoryRepository storyRepository;
	
	public Story createStory(StoryRequest storyRequest) {
//		File file = new File("D:\\photos\\college photos\\sem 1\\IMG_20191129_132254.jpg");
//		byte[] picInBytes = new byte[(int) file.length()];
		Story story = new Story();
		story.setId(storyRequest.getId());
		story.setStoryImage(storyRequest.getStoryImage());
		story.setPageId(storyRequest.getPageId());
		story.setUserId(storyRequest.getUserId());
		
		if (Objects.isNull(storyRequest.getId())) {
			story.setCreatedTime(LocalDateTime.now());
			story.setUpdatedTime(LocalDateTime.now());
		} else {
			story.setCreatedTime(story.getCreatedTime());
			story.setUpdatedTime(LocalDateTime.now());
		}
	     return	storyRepository.save(story);
	
	}
	
	public List<StoryResponse> getStroiesByPageId(String pageId){
		List<Story> stories = storyRepository.findAllByPageId(UUID.fromString(pageId));
		List<StoryResponse> storyResponses = converToStoryResponse(stories);
		return storyResponses;
		
	}
	
	public List<StoryResponse> converToStoryResponse(List<Story> stories) {
		List<StoryResponse> storyResponses = new ArrayList<>();
		storyResponses = stories.stream().map(s -> {
			StoryResponse storyResponse = new StoryResponse();
			storyResponse.setId(s.getId());
			storyResponse.setStoryImage(s.getStoryImage());
			storyResponse.setPageId(s.getPageId());
			storyResponse.setUserId(s.getUserId());
			storyResponse.setCreatedTime(s.getCreatedTime());
			storyResponse.setUpdatedTime(s.getUpdatedTime());
			return storyResponse;
		}).collect(Collectors.toList());
		return storyResponses;
	}
}
