package com.perksoft.icms.payload.response;

import lombok.Data;

@Data
public class RoleResponse {
	
	private Long id;
	private String tenantId;
	private String name;
	private String description;
	private String status;
}
