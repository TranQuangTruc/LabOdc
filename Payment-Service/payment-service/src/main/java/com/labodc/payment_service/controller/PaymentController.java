package com.labodc.payment_service.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.labodc.payment_service.dto.PaymentRequestDTO;
import com.labodc.payment_service.dto.PaymentResponseDTO;
import com.labodc.payment_service.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/enterprise-pay")
    public PaymentResponseDTO enterprisePay(@RequestBody PaymentRequestDTO request) {
        return paymentService.processEnterprisePayment(request);
    }

    @PostMapping("/lab-advance")
    public PaymentResponseDTO labAdvance(@RequestBody PaymentRequestDTO request) {
        return paymentService.advancePaymentByLab(request);
    }
}
