package com.perksoft.icms.payload.request;

import java.util.UUID;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.perksoft.icms.models.User;

public class PostRequest {

	private Long postId;

	@Size(max = 20)
	private String postTitle;

	@Lob
	private byte[] postDescription;

	@NotBlank
	@Size(max = 10)
	private String status;

	@NotBlank
	@Size(max = 10)
	private String postType;

	private Integer totalLikes;

	private Integer totalDisLikes;

	private UUID tenantId;

	private UUID createdBy;
	
	private UUID pageId;
	
	private Long groupId;
	
	

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public UUID getPageId() {
		return pageId;
	}

	public void setPageId(UUID pageId) {
		this.pageId = pageId;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public byte[] getPostDescription() {
		return postDescription;
	}

	public void setPostDescription(byte[] postDescription) {
		this.postDescription = postDescription;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
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

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public UUID getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UUID createdBy) {
		this.createdBy = createdBy;
	}

	

	

}