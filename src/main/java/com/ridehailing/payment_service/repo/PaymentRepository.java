package com.ridehailing.payment_service.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ridehailing.payment_service.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
	Optional<Payment> findByReference(String reference); // for idempotency check
}
