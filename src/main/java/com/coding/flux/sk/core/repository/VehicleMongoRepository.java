package com.coding.flux.sk.core.repository;

import com.coding.flux.sk.core.entity.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleMongoRepository extends MongoRepository<Vehicle, String> {

    // Buscar vehículo activo por ID
    Optional<Vehicle> findByIdAndActiveTrue(String id);

    // Verificar si existe un código de vehículo
    boolean existsByCode(String code);

    List<Vehicle> findAllByActiveTrue();
}
