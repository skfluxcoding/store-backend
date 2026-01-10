package com.coding.flux.sk.core.controller;

import com.coding.flux.sk.core.dto.LocationRequest;
import com.coding.flux.sk.core.service.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    // Enviar ubicación
    @PostMapping
    public ResponseEntity<Void> enviarUbicacion(@RequestBody LocationRequest request) {

        locationService.guardarUbicacion(
                request.getVehicleId(),
                request.getLatitude(),
                request.getLongitude(),
                request.getSpeed(),
                request.getHeading()
        );

        return ResponseEntity.ok().build();
    }

    @GetMapping("/trip/{tripId}")
    public ResponseEntity<?> obtenerRuta(@PathVariable String tripId) {
        return ResponseEntity.ok(locationService.obtenerRutaPorViaje(tripId));
    }

}
