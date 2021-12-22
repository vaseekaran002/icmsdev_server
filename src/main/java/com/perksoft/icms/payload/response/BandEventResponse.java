package com.perksoft.icms.payload.response;

import java.util.List;

import com.perksoft.icms.models.BandInTownEvent;

public class BandEventResponse {

	List<BandInTownEvent> past;

	List<BandInTownEvent> upcoming;

	public List<BandInTownEvent> getPast() {
		return past;
	}

	public void setPast(List<BandInTownEvent> past) {
		this.past = past;
	}

	public List<BandInTownEvent> getUpcoming() {
		return upcoming;
	}

	public void setUpcoming(List<BandInTownEvent> upcoming) {
		this.upcoming = upcoming;
	}

}
