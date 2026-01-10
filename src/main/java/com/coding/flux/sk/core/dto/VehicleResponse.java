package com.coding.flux.sk.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VehicleResponse {

    private String id;
    private String code;
    private String name;
    private Boolean active;
}
