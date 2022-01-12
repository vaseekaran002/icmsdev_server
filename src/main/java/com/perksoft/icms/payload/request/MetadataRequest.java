package com.perksoft.icms.payload.request;

import java.util.List;

import lombok.Data;

@Data
public class MetadataRequest {
	
	private Long id;
	private String componentName;
	private Integer componentOrder;
	private String displayName;
	private String status;
	private List<String>roles;
	private String tenantid;	
	
}
