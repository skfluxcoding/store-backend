package com.coding.flux.sk.common.config;

import com.coding.flux.sk.core.dto.LocationWsResponse;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class LocationPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public LocationPublisher(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void publish(LocationWsResponse location) {
        messagingTemplate.convertAndSend("/topic/locations", location);
    }
}
