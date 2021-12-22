package com.perksoft.icms.payload.request;

import java.util.UUID;

public class PageFollowerRequest {
	
	private Long totalFollowerCount;
	private UUID currentFollowerId;
	public Long getTotalFollowerCount() {
		return totalFollowerCount;
	}
	public void setTotalFollowerCount(Long totalFollowerCount) {
		this.totalFollowerCount = totalFollowerCount;
	}
	public UUID getCurrentFollowerId() {
		return currentFollowerId;
	}
	public void setCurrentFollowerId(UUID currentFollowerId) {
		this.currentFollowerId = currentFollowerId;
	}
	
	

}
