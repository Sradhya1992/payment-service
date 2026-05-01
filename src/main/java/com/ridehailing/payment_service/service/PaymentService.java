package com.ridehailing.payment_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ridehailing.payment_service.dto.PaymentRequest;
import com.ridehailing.payment_service.dto.PaymentResponse;

public interface PaymentService {

	public List<PaymentResponse> listPayments(int limit);

	public PaymentResponse getPayment(Long paymentId);

	public PaymentResponse getPaymentByTripId(String tripId);

	public PaymentResponse processCharge(PaymentRequest req);

	public PaymentResponse processRefund(Long paymentId);

}
