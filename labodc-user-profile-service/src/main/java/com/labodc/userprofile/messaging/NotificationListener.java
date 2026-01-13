package com.labodc.userprofile.messaging;

import com.labodc.userprofile.dto.notification.NotificationMessage;
import com.labodc.userprofile.entity.Notification;
import com.labodc.userprofile.repository.NotificationRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    private final NotificationRepository repo;

    public NotificationListener(NotificationRepository repo) {
        this.repo = repo;
    }

    @RabbitListener(queues = "notification.queue")
    public void receive(NotificationMessage message) {
        Notification n = new Notification();
        n.setUserId(message.getUserId());
        n.setTitle(message.getTitle());
        n.setContent(message.getContent());
        n.setRead(false);

        repo.save(n);
    }
}
