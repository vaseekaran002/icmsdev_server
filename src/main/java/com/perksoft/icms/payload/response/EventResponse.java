package com.perksoft.icms.payload.response;

import java.util.Date;
import java.util.UUID;

import com.perksoft.icms.models.User;

public class EventResponse {
	
	private Long id;
	private String name;
	private String description;
	private User createdBy;
    private Date startDate;
    private Date endDate;
    private String status;
    private UUID tenantId;
    private UUID pageId;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public User getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public UUID getTenantId() {
		return tenantId;
	}
	public void setTenantId(UUID tenantId) {
		this.tenantId = tenantId;
	}
	public UUID getPageId() {
		return pageId;
	}
	public void setPageId(UUID pageId) {
		this.pageId = pageId;
	}
    
    
}
