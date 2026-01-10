package com.coding.flux.sk.core.service;

import com.coding.flux.sk.common.config.LocationPublisher;
import com.coding.flux.sk.core.dto.LocationWsResponse;
import com.coding.flux.sk.core.entity.Location;
import com.coding.flux.sk.core.entity.Trip;
import com.coding.flux.sk.core.repository.LocationMongoRepository;
import com.coding.flux.sk.core.repository.TripMongoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LocationService {

    private final LocationMongoRepository locationRepository;
    private final TripMongoRepository tripRepository;
    private final LocationPublisher locationPublisher;

    public LocationService(
            LocationMongoRepository locationRepository,
            TripMongoRepository tripRepository,
            LocationPublisher locationPublisher
    ) {
        this.locationRepository = locationRepository;
        this.tripRepository = tripRepository;
        this.locationPublisher = locationPublisher;
    }

    public void guardarUbicacion(
            String vehicleId,
            Double latitude,
            Double longitude,
            Double speed,
            Double heading
    ) {

        Trip trip = tripRepository.findByVehicleIdAndActiveTrue(vehicleId)
                .orElseThrow(() ->
                        new RuntimeException("No hay viaje activo para el vehículo")
                );

        Location location = Location.builder()
                .vehicleId(vehicleId)
                .tripId(trip.getId())
                .latitude(latitude)
                .longitude(longitude)
                .speed(speed)
                .heading(heading)
                .timestamp(LocalDateTime.now())
                .build();

        locationRepository.save(location);

        // 🔴 Evento incremental (WebSocket)
        LocationWsResponse wsResponse = new LocationWsResponse(
                trip.getId(),
                vehicleId,
                latitude,
                longitude,
                speed,
                heading,
                location.getTimestamp()
        );

        locationPublisher.publish(wsResponse);
    }

    // REST: reconstrucción de ruta
    public List<Location> obtenerRutaPorViaje(String tripId) {
        return locationRepository.findByTripIdOrderByTimestampAsc(tripId);
    }
}
