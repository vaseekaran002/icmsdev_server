package com.perksoft.icms.payload.request;

import lombok.Data;

@Data
public class RoleRequest {
	
	private Long id;
	private String tenantId;
	private String name;
	private String description;
	private String status;

}
