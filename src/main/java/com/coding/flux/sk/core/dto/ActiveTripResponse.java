package com.coding.flux.sk.core.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ActiveTripResponse(
        String tripId,
        String vehicleId,
        List<double[]> route,
        LastLocation lastLocation
) {
    public record LastLocation(
            double latitude,
            double longitude,
            double speed,
            double heading,
            LocalDateTime timestamp
    ) {}
}
