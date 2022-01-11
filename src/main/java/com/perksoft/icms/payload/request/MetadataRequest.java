package com.perksoft.icms.payload.request;

import java.util.Set;

import com.perksoft.icms.models.Role;

import lombok.Data;

@Data
public class MetadataRequest {
	
	private Long id;
	private String componentName;
	private Integer componentOrder;
	private String displayName;
	private String status;
	private Set<String>roles;
	private String tenantid;
	
	
	
}
