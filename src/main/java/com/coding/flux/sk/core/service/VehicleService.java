package com.coding.flux.sk.core.service;

import com.coding.flux.sk.core.dto.VehicleRequest;
import com.coding.flux.sk.core.dto.VehicleResponse;
import com.coding.flux.sk.core.entity.Vehicle;
import com.coding.flux.sk.core.repository.VehicleMongoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VehicleService {

    private final VehicleMongoRepository vehicleRepository;

    public VehicleService(VehicleMongoRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public VehicleResponse create(VehicleRequest request) {

        String token = UUID.randomUUID().toString();

        Vehicle vehicle = Vehicle.builder()
                .code(request.code())
                .name(request.name())
                .token(token)
                .active(true)
                .build();

        Vehicle saved = vehicleRepository.save(vehicle);

        return new VehicleResponse(
                saved.getId(),
                saved.getCode(),
                saved.getName(),
                saved.getToken(),
                saved.getActive()
        );
    }
}
