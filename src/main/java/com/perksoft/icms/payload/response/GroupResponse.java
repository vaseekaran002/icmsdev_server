package com.perksoft.icms.payload.response;

import java.util.Set;
import java.util.UUID;

import com.perksoft.icms.models.User;

public class GroupResponse {

	 private Long id;
	 private String name;
	 private String status;
	 private String description;
	 private UUID tenantid;
	 private Set<User> users;
	 private byte[] groupImage;
	 private UUID createdBy;
	 
	public Set<User> getUsers() {
		return users;
	}
	public byte[] getGroupImage() {
		return groupImage;
	}
	public void setGroupImage(byte[] groupImage) {
		this.groupImage = groupImage;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public UUID getTenantid() {
		return tenantid;
	}
	public void setTenantid(UUID tenantid) {
		this.tenantid = tenantid;
	}
	public UUID getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(UUID createdBy) {
		this.createdBy = createdBy;
	}
	
	 
	 
}
