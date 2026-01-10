package com.coding.flux.sk.core.dto;


import java.time.LocalDateTime;

public record LocationWsResponse(
        String vehicleId,
        String vehicleCode,
        Double latitude,
        Double longitude,
        Double speed,
        Double heading,
        LocalDateTime timestamp
) {
}