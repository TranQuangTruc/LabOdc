package com.labodc.userprofile.security;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    
    public static final String PROFILE_EXCHANGE = "labodc.profile.exchange";
    public static final String PROFILE_CREATED_QUEUE = "labodc.profile.created.queue";
    public static final String PROFILE_UPDATED_QUEUE = "labodc.profile.updated.queue";
    public static final String PROFILE_CREATED_ROUTING_KEY = "profile.created";
    public static final String PROFILE_UPDATED_ROUTING_KEY = "profile.updated";
    
    @Bean
    public TopicExchange profileExchange() {
        return new TopicExchange(PROFILE_EXCHANGE);
    }
    
    @Bean
    public Queue profileCreatedQueue() {
        return new Queue(PROFILE_CREATED_QUEUE, true);
    }
    
    @Bean
    public Queue profileUpdatedQueue() {
        return new Queue(PROFILE_UPDATED_QUEUE, true);
    }
    
    @Bean
    public Binding profileCreatedBinding() {
        return BindingBuilder
                .bind(profileCreatedQueue())
                .to(profileExchange())
                .with(PROFILE_CREATED_ROUTING_KEY);
    }
    
    @Bean
    public Binding profileUpdatedBinding() {
        return BindingBuilder
                .bind(profileUpdatedQueue())
                .to(profileExchange())
                .with(PROFILE_UPDATED_ROUTING_KEY);
    }
    
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}