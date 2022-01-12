package com.perksoft.icms.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "username") })
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@NotBlank
	@Size(max = 255)
	private String username;

	@NotBlank
	@Size(max = 255)
	@Email
	private String email;

	@NotBlank
	@Size(max = 120)
	private String password;

	@Size(max = 255)
	private String firstName;

	@Size(max = 255)
	private String lastName;

	@Size(max = 15)
	private String mobileNumber;

	private UUID tenantId;

	private byte[] profileImage;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "users")
	private Set<Group> groups = new HashSet<>();

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "followers")
	private Set<Page> followingPages = new HashSet<>();

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "createdBy")
	private List<Post> posts = new ArrayList<>();

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "createdBy")
	private List<Page> ownPages = new ArrayList<>();

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "createdBy")
	private List<Event> events = new ArrayList<>();

	public User() {
	}

	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public UUID getTenantId() {
		return tenantId;
	}

	public void setTenantId(UUID tenantId) {
		this.tenantId = tenantId;
	}

	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public byte[] getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(byte[] profileImage) {
		this.profileImage = profileImage;
	}

	public Set<Page> getFollowingPages() {
		return followingPages;
	}

	public void setFollowingPages(Set<Page> followingPages) {
		this.followingPages = followingPages;
	}

	public List<Page> getOwnPages() {
		return ownPages;
	}

	public void setOwnPages(List<Page> pages) {
		this.ownPages = pages;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

}
