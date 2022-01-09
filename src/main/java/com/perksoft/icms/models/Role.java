package com.perksoft.icms.models;



import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name = "roles")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole name;

    private String roleDescription;
    
    @JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY  , mappedBy = "roles")
	private Set<Tenant> tenants = new HashSet<>();
    
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "roles")
    private Set<MetaData> metadata = new HashSet<>();
	
	public Role() {

	}

	public Role(ERole name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ERole getName() {
		return name;
	}

	public void setName(ERole name) {
		this.name = name;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public Set<Tenant> getTenants() {
		return tenants;
	}

	public void setTenants(Set<Tenant> tenants) {
		this.tenants = tenants;
	}
	
}