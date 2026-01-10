package com.coding.flux.sk.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class LocationResponse {

    private Double latitude;
    private Double longitude;
    private Double speed;
    private Double heading;
    private LocalDateTime timestamp;
}
