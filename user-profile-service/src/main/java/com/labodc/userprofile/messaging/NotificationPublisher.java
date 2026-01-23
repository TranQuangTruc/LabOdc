package com.labodc.userprofile.messaging;

import com.labodc.userprofile.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationPublisher {

    private final RabbitTemplate rabbitTemplate;

    public NotificationPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(NotificationMessage msg) {
        rabbitTemplate.convertAndSend(RabbitConfig.NOTIFICATION_QUEUE, msg);
    }
}
