package com.perksoft.icms.payload.request;

import java.util.UUID;

public class CommentRequest {

	private Long commentId;
	private byte[] comments;
	private String status;
	private Integer totalLikes;
	private Integer totalDisLikes;
	private UUID tenantId;
	private Long postId;
	private UUID userId;
    private UUID pageId;
    
    
    
	public UUID getPageId() {
		return pageId;
	}

	public void setPageId(UUID pageId) {
		this.pageId = pageId;
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

	public UUID getTenantId() {
		return tenantId;
	}

	public void setTenantId(UUID tenantId) {
		this.tenantId = tenantId;
	}

	public byte[] getComments() {
		return comments;
	}

	public void setComments(byte[] comments) {
		this.comments = comments;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	
	
	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

}