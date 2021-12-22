package com.perksoft.icms.payload.request;

import java.util.UUID;

import javax.validation.constraints.NotBlank;

public class StoryRequest {

	
    private Long id;
	
	@NotBlank
	private byte[] storyImage;
	
	private UUID userId;
	
	private UUID pageId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getStoryImage() {
		return storyImage;
	}

	public void setStoryImage(byte[] storyImage) {
		this.storyImage = storyImage;
	}

	
	
	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public UUID getPageId() {
		return pageId;
	}

	public void setPageId(UUID pageId) {
		this.pageId = pageId;
	}
	
	
}
