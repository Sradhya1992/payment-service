package com.ridehailing.payment_service.dto;

public class TripStatusResponse {

	private String tripId;
	private String status; // REQUESTED, ACCEPTED, ONGOING, COMPLETED, CANCELLED

	public String getTripId() {
		return tripId;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
