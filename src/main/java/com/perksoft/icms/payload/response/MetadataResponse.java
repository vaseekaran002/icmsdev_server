package com.perksoft.icms.payload.response;

import java.util.Set;
import java.util.UUID;

import com.perksoft.icms.models.Role;

import lombok.Data;

@Data
public class MetadataResponse {

	private Long id;
	private String componentName;
	private Integer componentOrder;
	private String displayName;
	private String status;
	private Set<Role> roles;
	private UUID tenantId;
}
