package com.perksoft.icms.models;

import lombok.Data;

@Data
public class Invoice {
	private String channelName;
	private String title;
	private String contractDescription;
	private String totalFeesDue;
	private String description;
	private String requestedDueDate;
	private String dueDate;
	private String contractId;
}
