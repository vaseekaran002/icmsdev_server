package com.perksoft.icms.payload.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.perksoft.icms.models.User;

public class CommentResponse {

	private Long id;
	private byte[] comments;
	private String status;
	private Integer totalLikes;
	private Integer totalDisLikes;
	private LocalDateTime createdTime;
	private LocalDateTime updatedTime;
	private User user;
    private UUID pageId;
    
    
	public UUID getPageId() {
		return pageId;
	}

	public void setPageId(UUID pageId) {
		this.pageId = pageId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getComments() {
		return comments;
	}

	public void setComments(byte[] comments) {
		this.comments = comments;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getTotalLikes() {
		return totalLikes;
	}

	public void setTotalLikes(Integer totalLikes) {
		this.totalLikes = totalLikes;
	}

	public Integer getTotalDisLikes() {
		return totalDisLikes;
	}

	public void setTotalDisLikes(Integer totalDisLikes) {
		this.totalDisLikes = totalDisLikes;
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
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    

}
