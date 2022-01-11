package com.perksoft.icms.payload.response;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.perksoft.icms.models.Group;
import com.perksoft.icms.models.MetaData;
import com.perksoft.icms.models.Post;

import lombok.Data;

@Data
public class UserResponse {

	private UUID id;
	private String username;
	private String email;
	private Set<Group> groups;
	private String firstName;
	private String lastName;
	private String mobileNumber;
    private Set<MetaData> metaData;
    private List<Post> posts;
    private byte[] profileImage;
}
