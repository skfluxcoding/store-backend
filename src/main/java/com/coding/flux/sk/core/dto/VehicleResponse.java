package com.coding.flux.sk.core.dto;

public record VehicleResponse(
        String id,
        String code,
        String name,
        String token,
        Boolean active
) {
}

