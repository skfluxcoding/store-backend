package com.coding.flux.sk.core.service;

import com.coding.flux.sk.core.dto.VehicleFindAll;
import com.coding.flux.sk.core.entity.Vehicle;
import com.coding.flux.sk.core.repository.VehicleMongoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VehicleService {

    private final VehicleMongoRepository vehicleRepository;

    public VehicleService(VehicleMongoRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    // Crear vehículo
    public Vehicle crearVehiculo(String code, String name) {

        if (vehicleRepository.existsByCode(code)) {
            throw new RuntimeException("Ya existe un vehículo con el código " + code);
        }

        Vehicle vehicle = Vehicle.builder()
                .code(code)
                .name(name)
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();

        return vehicleRepository.save(vehicle);
    }

    // Obtener vehículo activo
    public Vehicle obtenerVehiculoActivo(String vehicleId) {
        return vehicleRepository.findByIdAndActiveTrue(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado o inactivo"));
    }

    public List<VehicleFindAll> getAll() {
        return vehicleRepository.findAllByActiveTrue()
                .stream().map(item -> new VehicleFindAll(item.getId(), item.getCode(), item.getName()))
                .toList();
    }
}
