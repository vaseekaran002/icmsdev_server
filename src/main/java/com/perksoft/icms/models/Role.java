package com.perksoft.icms.models;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 20)
	private String name;

	private String roleDescription;

	private UUID tenantId;

	private String status;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
	private Set<MetaData> metadata = new HashSet<>();

	public Role() {

	}

	public Role(String name) {
		this.name = name;
	}

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

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public UUID getTenantId() {
		return tenantId;
	}

	public void setTenantId(UUID tenantId) {
		this.tenantId = tenantId;
	}

	public Set<MetaData> getMetadata() {
		return metadata;
	}

	public void setMetadata(Set<MetaData> metadata) {
		this.metadata = metadata;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}