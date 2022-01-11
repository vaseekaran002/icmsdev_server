package com.perksoft.icms.models;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tenant")
public class Tenant {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
