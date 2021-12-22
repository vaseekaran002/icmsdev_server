package com.perksoft.icms.payload.response;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.perksoft.icms.models.Group;
import com.perksoft.icms.models.MetaData;
import com.perksoft.icms.models.Post;

public class UserResponse {

	private UUID id;
	private String username;
	private String email;
	private Set<Group> groups;
	private String firstName;
	private String lastName;
	private String mobileNumber;
    private Set<MetaData> metadatas;
    private List<Post> posts;
    private byte[] profileImage;
	

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

	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	public Set<MetaData> getMetadatas() {
		return metadatas;
	}

	public void setMetadatas(Set<MetaData> metadatas) {
		this.metadatas = metadatas;
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
    
	
	
}
