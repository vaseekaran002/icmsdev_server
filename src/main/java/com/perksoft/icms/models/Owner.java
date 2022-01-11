package com.perksoft.icms.models;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "owners")
public class Owner {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private UUID userId;
	
	private UUID pageId;
	
	private UUID addedBy;
	
	private String status;

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public UUID getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(UUID addedBy) {
		this.addedBy = addedBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
