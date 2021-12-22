package com.perksoft.icms.models;

public class BandInTownEvent {

	private String datetime;
	private String title;
	private String description;
	private BandInTownVenue venue;

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BandInTownVenue getVenue() {
		return venue;
	}

	public void setVenue(BandInTownVenue venue) {
		this.venue = venue;
	}

}