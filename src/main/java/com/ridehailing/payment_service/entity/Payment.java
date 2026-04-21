package com.ridehailing.payment_service.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ride_trip_payments")
public class Payment {

	@Id
	@GeneratedValue
	@Column(name = "payment_id", updatable = false, nullable = false)
	private UUID paymentId;

	@Column(name = "trip_id", nullable = false)
	private UUID tripId;

	@Column(nullable = false)
	private BigDecimal amount;

	@Column(nullable = false)
	private String method; // e.g., CARD, UPI, WALLET

	@Column(nullable = false)
	private String status; // PENDING, SUCCESS, FAILED, REFUNDED

	@Column(unique = true, nullable = false)
	private String reference; // transactionRef for idempotency

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	public Payment() {
	}

	public Payment(UUID tripId, BigDecimal amount, String method, String status, String reference) {
		this.tripId = tripId;
		this.amount = amount;
		this.method = method;
		this.status = status;
		this.reference = reference;
		this.createdAt = LocalDateTime.now();
	}

	public UUID getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(UUID paymentId) {
		this.paymentId = paymentId;
	}

	public UUID getTripId() {
		return tripId;
	}

	public void setTripId(UUID tripId) {
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

}
