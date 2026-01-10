package com.coding.flux.sk.core.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record LocationRequest(

        @NotNull
        Double latitude,

        @NotNull
        Double longitude,

        Double speed,
        Double heading,

        LocalDateTime timestamp
) {
}