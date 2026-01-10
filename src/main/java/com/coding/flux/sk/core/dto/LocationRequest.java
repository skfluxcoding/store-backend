package com.coding.flux.sk.core.dto;

import lombok.Data;

@Data
public class LocationRequest {

        private String vehicleId;
        private Double latitude;
        private Double longitude;
        private Double speed;
        private Double heading;
}
