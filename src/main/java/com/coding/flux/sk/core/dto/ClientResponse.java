package com.coding.flux.sk.core.dto;

import java.time.LocalDateTime;

public record ClientResponse(
        String clientId,
        String firstName,
        String lastName,
        String email,
        String phone,
        String address,
        Boolean enabled,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
