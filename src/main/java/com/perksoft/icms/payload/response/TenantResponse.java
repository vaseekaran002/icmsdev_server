package com.perksoft.icms.payload.response;

import java.util.UUID;

public class TenantResponse {

	private UUID id;
	private String tenantname;
	private String status;
	private String description;
    private byte[] logo;
	private byte[] favIcon;
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getTenantname() {
		return tenantname;
	}
	public void setTenantname(String tenantname) {
		this.tenantname = tenantname;
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
	public byte[] getLogo() {
		return logo;
	}
	public void setLogo(byte[] logo) {
		this.logo = logo;
	}
	public byte[] getFavIcon() {
		return favIcon;
	}
	public void setFavIcon(byte[] favIcon) {
		this.favIcon = favIcon;
	}
	
	
}
