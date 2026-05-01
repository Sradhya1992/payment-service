package com.ridehailing.payment_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ridehailing.payment_service.dto.PaymentRequest;
import com.ridehailing.payment_service.dto.PaymentResponse;
import com.ridehailing.payment_service.service.PaymentService;

@RestController
@RequestMapping("/v1/payments")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@GetMapping
	public ResponseEntity<List<PaymentResponse>> listPayments(
			@RequestParam(defaultValue = "25") int limit) {
		return ResponseEntity.ok(paymentService.listPayments(limit));
	}

	@GetMapping("/{id}")
	public ResponseEntity<PaymentResponse> getPayment(@PathVariable Long id) {
		return ResponseEntity.ok(paymentService.getPayment(id));
	}
	
	@PostMapping("/charge")
    public ResponseEntity<PaymentResponse> charge(@RequestBody PaymentRequest request) {
        PaymentResponse response = paymentService.processCharge(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/refund")
    public ResponseEntity<PaymentResponse> refund(@PathVariable Long id) {
        PaymentResponse response = paymentService.processRefund(id);
        return ResponseEntity.ok(response);
    }


}
