package com.coding.flux.sk.core.dto;

public record RepClientGetAll(
        String clientId,
        String firstName,
        String lastName,
        String email,
        String phone
) {}
