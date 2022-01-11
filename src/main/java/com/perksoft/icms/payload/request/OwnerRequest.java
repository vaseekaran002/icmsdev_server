package com.perksoft.icms.payload.request;

import java.util.UUID;

import lombok.Data;

@Data
public class OwnerRequest {

	private Long ownerId;
	private UUID userId;
	private UUID pageId;
	private UUID addedBy;
	private String status;
 
}
