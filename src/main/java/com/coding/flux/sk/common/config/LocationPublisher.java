package com.coding.flux.sk.common.config;

import com.coding.flux.sk.core.entity.Location;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class LocationPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public LocationPublisher(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void publish(Location location) {
        messagingTemplate.convertAndSend("/topic/locations", location);
    }
}
