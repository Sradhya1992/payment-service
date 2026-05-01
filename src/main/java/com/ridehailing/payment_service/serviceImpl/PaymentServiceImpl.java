package com.ridehailing.payment_service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ridehailing.payment_service.dto.PaymentRequest;
import com.ridehailing.payment_service.dto.PaymentResponse;
import com.ridehailing.payment_service.entity.Payment;
import com.ridehailing.payment_service.repo.PaymentRepository;
import com.ridehailing.payment_service.service.PaymentService;
import com.ridehailing.payment_service.tripClient.TripClient;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PaymentServiceImpl implements PaymentService {

	private final PaymentRepository repo;
	private final TripClient tripClient; // Feign/WebClient

	public PaymentServiceImpl(PaymentRepository repo, TripClient tripClient) {
		this.repo = repo;
		this.tripClient = tripClient;
	}

	public List<PaymentResponse> listPayments(int limit) {
		int pageSize = Math.max(1, Math.min(limit, 100));
		return repo.findAll(PageRequest.of(0, pageSize, Sort.by(Sort.Direction.DESC, "createdAt")))
				.stream()
				.map(PaymentResponse::fromEntity)
				.toList();
	}

	public PaymentResponse getPayment(Long paymentId) {
		Payment payment = repo.findById(paymentId)
				.orElseThrow(() -> new EntityNotFoundException("Payment not found"));
		return PaymentResponse.fromEntity(payment);
	}

	public PaymentResponse getPaymentByTripId(String tripId) {
		Payment payment = repo.findFirstByTripIdOrderByCreatedAtDesc(tripId)
				.orElseThrow(() -> new EntityNotFoundException("Payment not found for trip"));
		return PaymentResponse.fromEntity(payment);
	}

	public PaymentResponse processCharge(PaymentRequest req) {
		// 1. Verify trip completion
		String status = tripClient.getTripStatus(req.getTripId());
		if (!status.equals("COMPLETED")) {
			throw new IllegalStateException("Trip not completed");
		}

		// 2. Idempotency check
		Optional<Payment> existing = repo.findByReference(req.getTransactionRef());
		if (existing.isPresent()) {
			return PaymentResponse.fromEntity(existing.get());
		}

		// 3. Process payment (mock gateway)
		Payment payment = new Payment(req.getTripId(), req.getAmount(), req.getMethod(), "SUCCESS",
				req.getTransactionRef());
		repo.save(payment);
		return PaymentResponse.fromEntity(payment);
	}

	public PaymentResponse processRefund(Long paymentId) {
		Payment payment = repo.findById(paymentId)
				.orElseThrow(() -> new EntityNotFoundException(("Payment not found")));
		payment.setStatus("REFUNDED");
		repo.save(payment);
		return PaymentResponse.fromEntity(payment);
	}

}
