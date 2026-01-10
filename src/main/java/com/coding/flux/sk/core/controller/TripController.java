package com.coding.flux.sk.core.controller;

import com.coding.flux.sk.core.dto.EndTripRequest;
import com.coding.flux.sk.core.dto.StartTripRequest;
import com.coding.flux.sk.core.entity.Trip;
import com.coding.flux.sk.core.service.TripService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    // Iniciar viaje
    @PostMapping("/start")
    public ResponseEntity<Trip> iniciarViaje(@RequestBody StartTripRequest request) {
        Trip trip = tripService.iniciarViaje(request.getVehicleId());
        return ResponseEntity.ok(trip);
    }

    // Finalizar viaje
    @PostMapping("/end")
    public ResponseEntity<Void> finalizarViaje(@RequestBody EndTripRequest request) {
        tripService.finalizarViaje(request.getVehicleId());
        return ResponseEntity.ok().build();
    }

    // Listar viajes por vehículo
    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<Trip>> listarViajes(@PathVariable String vehicleId) {
        return ResponseEntity.ok(tripService.listarViajesPorVehiculo(vehicleId));
    }
}

