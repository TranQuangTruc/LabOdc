package com.labodc.payment_service.service;

import com.labodc.payment_service.dto.PaymentRequestDTO;
import com.labodc.payment_service.dto.PaymentResponseDTO;

public interface PaymentService {

    PaymentResponseDTO processEnterprisePayment(PaymentRequestDTO request);

    PaymentResponseDTO advancePaymentByLab(PaymentRequestDTO request);
}
