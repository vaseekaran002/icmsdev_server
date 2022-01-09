package com.perksoft.icms.payload.request;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TenantRequest {

	private UUID id;

	@NotBlank
	@Size(max = 50)
	private String name;

	@NotBlank
	@Size(max = 15)
	private String status;

	@Size(max = 50)
	private String description;

	private byte[] logo;

	private byte[] favIcon;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String tenantname) {
		this.name = tenantname;
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
