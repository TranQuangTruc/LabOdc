package com.labodc.payment_service.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.labodc.payment_service.dto.PaymentResponseDTO;

@Component
public class PaymentEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public PaymentEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(PaymentResponseDTO dto) {
        rabbitTemplate.convertAndSend("payment.created", dto);
    }
}
