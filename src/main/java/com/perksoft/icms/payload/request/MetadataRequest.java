package com.perksoft.icms.payload.request;

import java.util.Set;

import com.perksoft.icms.models.Role;

public class MetadataRequest {
	
	private Long id;
	private String componentName;
	private Integer componentOrder;
	private String displayName;
	private String status;
	private Set<Role> roles;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	public Integer getComponentOrder() {
		return componentOrder;
	}
	public void setComponentOrder(Integer componentOrder) {
		this.componentOrder = componentOrder;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	
}
