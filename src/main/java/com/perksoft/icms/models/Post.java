package com.perksoft.icms.models;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "posts")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 20)
	private String postTitle;

	@Lob
	@Type(type = "org.hibernate.type.ImageType")
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

	private LocalDateTime createdTime;

	private LocalDateTime updatedTime;
	
	private UUID pageId;
	
	private Long groupId;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User createdBy;

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER,mappedBy = "post")
	private Set<Comment> comments;
	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	
	

}
