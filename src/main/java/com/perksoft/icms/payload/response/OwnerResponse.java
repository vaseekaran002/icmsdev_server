package com.perksoft.icms.payload.response;

import java.util.UUID;

import lombok.Data;

@Data
public class OwnerResponse {
	
	private Long ownerId;
	private UUID userId;
	private UUID pageId;
	private UUID addedBy;
	private String status;

}
