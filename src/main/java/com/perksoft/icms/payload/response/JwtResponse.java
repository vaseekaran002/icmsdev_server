package com.perksoft.icms.payload.response;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.perksoft.icms.models.MetaData;

import lombok.Data;

@Data
public class JwtResponse {
	private String accessToken;
	private String type = "Bearer";
	private UUID id;
	private String username;
	private String email;
	private List<String> roles;
	private Set<MetaData> metadata;

	public JwtResponse(String accessToken, UUID id, String username, String email, List<String> roles,
			Set<MetaData> metadatas) {
		this.accessToken = accessToken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
		this.metadata = metadatas;
	}

}
