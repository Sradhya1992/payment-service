package com.ridehailing.payment_service.dto;

import java.util.UUID;

public class TripStatusResponse {

	private UUID tripId;
	private String status; // REQUESTED, ACCEPTED, ONGOING, COMPLETED, CANCELLED

	public UUID getTripId() {
		return tripId;
	}

	public void setTripId(UUID tripId) {
		this.tripId = tripId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
