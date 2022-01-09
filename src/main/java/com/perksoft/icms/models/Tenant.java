package com.perksoft.icms.models;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "tenants")
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
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tenantRoles" , joinColumns = @JoinColumn(name = "tenantId"), inverseJoinColumns = @JoinColumn(name = "roleId"))
	private Set<Role> roles = new HashSet<>();
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tenantMetadata", joinColumns = @JoinColumn(name = "tenantId"), inverseJoinColumns = @JoinColumn(name = "metaDataId"))
	private Set<MetaData> metaData = new HashSet<>();

	public Set<MetaData> getMetaData() {
		return metaData;
	}

	public void setMetaData(Set<MetaData> metaData) {
		this.metaData = metaData;
	}

	public Tenant() {

	}

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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	

}
