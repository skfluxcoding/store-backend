package com.coding.flux.sk.core.service;

import com.coding.flux.sk.core.dto.ActiveTripResponse;
import com.coding.flux.sk.core.entity.Location;
import com.coding.flux.sk.core.entity.Trip;
import com.coding.flux.sk.core.repository.LocationMongoRepository;
import com.coding.flux.sk.core.repository.TripMongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripQueryService {

    private final TripMongoRepository tripRepository;
    private final LocationMongoRepository locationRepository;

    public TripQueryService(
            TripMongoRepository tripRepository,
            LocationMongoRepository locationRepository
    ) {
        this.tripRepository = tripRepository;
        this.locationRepository = locationRepository;
    }

    public List<ActiveTripResponse> getActiveTrips() {

        return tripRepository.findByActiveTrue()
                .stream()
                .map(this::buildActiveTrip)
                .toList();
    }

    private ActiveTripResponse buildActiveTrip(Trip trip) {

        // Ruta completa
        List<double[]> route = locationRepository
                .findByTripIdOrderByTimestampAsc(trip.getId())
                .stream()
                .map(l -> new double[]{l.getLatitude(), l.getLongitude()})
                .toList();

        // Última posición
        Location last = locationRepository
                .findFirstByTripIdOrderByTimestampDesc(trip.getId())
                .orElseThrow();

        return new ActiveTripResponse(
                trip.getId(),
                trip.getVehicleId(),
                route,
                new ActiveTripResponse.LastLocation(
                        last.getLatitude(),
                        last.getLongitude(),
                        last.getSpeed(),
                        last.getHeading(),
                        last.getTimestamp()
                )
        );
    }
}
