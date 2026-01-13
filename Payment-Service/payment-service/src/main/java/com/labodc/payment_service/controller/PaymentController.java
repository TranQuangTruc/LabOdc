package com.labodc.payment_service.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.labodc.payment_service.dto.PaymentRequestDTO;
import com.labodc.payment_service.dto.PaymentResponseDTO;
import com.labodc.payment_service.service.PaymentService;

@RestController
@CrossOrigin
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

    @GetMapping("/{id}")
    public PaymentResponseDTO getPayment(@PathVariable UUID id) {
        return paymentService.getPaymentById(id);
    }
}
