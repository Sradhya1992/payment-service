package com.ridehailing.payment_service.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PaymentRequest {

	@NotNull(message = "Trip ID is required")
	private String tripId;

	@NotNull(message = "Amount is required")
	@Positive(message = "Amount must be positive")
	private BigDecimal amount;

	@NotNull(message = "Payment method is required")
	private String method; // e.g., CARD, UPI, WALLET

	@NotNull(message = "Transaction reference is required")
	private String transactionRef; // ensures idempotency

	// Constructors
	public PaymentRequest() {
	}

	public PaymentRequest(String tripId, BigDecimal amount, String method, String transactionRef) {
		this.tripId = tripId;
		this.amount = amount;
		this.method = method;
		this.transactionRef = transactionRef;
	}

	public String getTripId() {
		return tripId;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getTransactionRef() {
		return transactionRef;
	}

	public void setTransactionRef(String transactionRef) {
		this.transactionRef = transactionRef;
	}

}
