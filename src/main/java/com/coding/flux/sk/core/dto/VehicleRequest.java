package com.coding.flux.sk.core.dto;

import jakarta.validation.constraints.NotBlank;

public record VehicleRequest(

        @NotBlank
        String code,

        @NotBlank
        String name
) {}
