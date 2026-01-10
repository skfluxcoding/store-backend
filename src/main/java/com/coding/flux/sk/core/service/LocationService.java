package com.coding.flux.sk.core.service;


import com.coding.flux.sk.common.config.LocationPublisher;
import com.coding.flux.sk.core.dto.LocationRequest;
import com.coding.flux.sk.core.dto.LocationWsResponse;
import com.coding.flux.sk.core.entity.Location;
import com.coding.flux.sk.core.repository.LocationMongoRepository;
import com.coding.flux.sk.core.entity.Vehicle;
import com.coding.flux.sk.core.repository.VehicleMongoRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LocationService {

    private final VehicleMongoRepository vehicleRepository;
    private final LocationMongoRepository locationRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final LocationPublisher locationPublisher;

    public LocationService(
            VehicleMongoRepository vehicleRepository,
            LocationMongoRepository locationRepository,
            SimpMessagingTemplate messagingTemplate,
            LocationPublisher locationPublisher
    ) {
        this.vehicleRepository = vehicleRepository;
        this.locationRepository = locationRepository;
        this.messagingTemplate = messagingTemplate;
        this.locationPublisher = locationPublisher;
    }

    public void saveLocation(String token, LocationRequest dto) {

        Vehicle vehicle = vehicleRepository.findByTokenAndActiveTrue(token)
                .orElseThrow(() -> new RuntimeException("Invalid vehicle token"));

        Location location = Location.builder()
                .vehicleId(vehicle.getId())
                .latitude(dto.latitude())
                .longitude(dto.longitude())
                .speed(dto.speed())
                .heading(dto.heading())
                .timestamp(dto.timestamp() != null ? dto.timestamp() : LocalDateTime.now())
                .build();

        locationRepository.save(location);
        locationPublisher.publish(location);

        // Emitir SOLO última posición
        LocationWsResponse wsResponse = new LocationWsResponse(
                vehicle.getId(),
                vehicle.getCode(),
                location.getLatitude(),
                location.getLongitude(),
                location.getSpeed(),
                location.getHeading(),
                location.getTimestamp()
        );

        messagingTemplate.convertAndSend("/topic/locations", wsResponse);
    }
}
