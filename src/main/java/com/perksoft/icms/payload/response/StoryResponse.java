package com.perksoft.icms.payload.response;

import java.time.LocalDateTime;
import java.util.UUID;


public class StoryResponse {

	private Long id;
	private byte[] storyImage;
	private UUID userId;
	private UUID pageId;
	private LocalDateTime createdTime;
	private LocalDateTime updatedTime;
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
	public LocalDateTime getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}
	public LocalDateTime getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(LocalDateTime updatedTime) {
		this.updatedTime = updatedTime;
	}
	
	
}
