package com.coding.flux.sk.core.controller;

import com.coding.flux.sk.core.dto.LocationRequest;
import com.coding.flux.sk.core.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    public ResponseEntity<Void> saveLocation(
            @RequestHeader("X-VEHICLE-TOKEN") String token,
            @Valid @RequestBody LocationRequest request
    ) {
        locationService.saveLocation(token, request);
        return ResponseEntity.ok().build();
    }
}
