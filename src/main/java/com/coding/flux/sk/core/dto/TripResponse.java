package com.coding.flux.sk.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TripResponse {

    private String tripId;
    private String vehicleId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean active;
}
