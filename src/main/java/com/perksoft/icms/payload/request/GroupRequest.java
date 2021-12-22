package com.perksoft.icms.payload.request;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.perksoft.icms.models.User;

public class GroupRequest {

	 private Long id;
	 
	 @NotBlank
	 @Size(max = 50)
	 private String name;

	 @NotBlank
	 @Size(max= 15)
	 private String status;
	 
	 @Size(max = 50)
	 private String description;
	 
	 @Lob
	 private byte[] groupImage;

	 private UUID tenantid;

	 private Set<User> users;
	 
	 private UUID createdBy;
	 
	 
	public Long getId() {
		return id;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
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

	public void setId(Long id) {
		this.id = id;
	}




	public byte[] getGroupImage() {
		return groupImage;
	}




	public void setGroupImage(byte[] groupImage) {
		this.groupImage = groupImage;
	}

	public UUID getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UUID createdBy) {
		this.createdBy = createdBy;
	}

	
	 
	 
	 
}
