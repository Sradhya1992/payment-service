package com.ridehailing.payment_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.ridehailing.payment_service.entity.Payment;

public class PaymentResponse {
	
	private UUID paymentId;
	private UUID tripId;
	private BigDecimal amount;
	private String method;
	private String status;
	private String reference;
	private LocalDateTime createdAt;
	
	
	// Constructors
    public PaymentResponse(UUID paymentId, UUID tripId, BigDecimal amount, String method,
                           String status, String reference, LocalDateTime createdAt) {
        this.paymentId = paymentId;
        this.tripId = tripId;
        this.amount = amount;
        this.method = method;
        this.status = status;
        this.reference = reference;
        this.createdAt = createdAt;
    }

    // Static mapper for convenience
    public static PaymentResponse fromEntity(Payment payment) {
        return new PaymentResponse(
                payment.getPaymentId(),
                payment.getTripId(),
                payment.getAmount(),
                payment.getMethod(),
                payment.getStatus(),
                payment.getReference(),
                payment.getCreatedAt()
        );
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
