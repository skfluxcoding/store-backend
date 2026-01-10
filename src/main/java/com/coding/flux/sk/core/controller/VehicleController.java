package com.coding.flux.sk.core.controller;

import com.coding.flux.sk.core.dto.VehicleCreateRequest;
import com.coding.flux.sk.core.dto.VehicleResponse;
import com.coding.flux.sk.core.entity.Vehicle;
import com.coding.flux.sk.core.service.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<VehicleResponse> crearVehiculo(
            @RequestBody VehicleCreateRequest request
    ) {
        var vehicle = vehicleService.crearVehiculo(
                request.getCode(),
                request.getName()
        );

        return ResponseEntity.ok(
                new VehicleResponse(
                        vehicle.getId(),
                        vehicle.getCode(),
                        vehicle.getName(),
                        vehicle.getActive()
                )
        );
    }

}
