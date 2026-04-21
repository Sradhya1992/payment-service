package com.ridehailing.payment_service.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ridehailing.payment_service.dto.PaymentRequest;
import com.ridehailing.payment_service.dto.PaymentResponse;

public interface PaymentService {

	public PaymentResponse processCharge(PaymentRequest req);

	public PaymentResponse processRefund(UUID paymentId);

}
