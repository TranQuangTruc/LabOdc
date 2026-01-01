package com.labodc.userprofile.messaging;

import com.labodc.userprofile.security.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class ProfileEventPublisher {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    public void publishProfileCreated(Long userId, String email, String role) {
        Map<String, Object> event = new HashMap<>();
        event.put("userId", userId);
        event.put("email", email);
        event.put("role", role);
        event.put("eventType", "PROFILE_CREATED");
        event.put("timestamp", System.currentTimeMillis());
        
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.PROFILE_EXCHANGE,
                RabbitMQConfig.PROFILE_CREATED_ROUTING_KEY,
                event
        );
        
        log.info("Published profile created event for user: {}", userId);
    }
    
    public void publishProfileUpdated(Long userId, String email, String role) {
        Map<String, Object> event = new HashMap<>();
        event.put("userId", userId);
        event.put("email", email);
        event.put("role", role);
        event.put("eventType", "PROFILE_UPDATED");
        event.put("timestamp", System.currentTimeMillis());
        
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.PROFILE_EXCHANGE,
                RabbitMQConfig.PROFILE_UPDATED_ROUTING_KEY,
                event
        );
        
        log.info("Published profile updated event for user: {}", userId);
    }
}