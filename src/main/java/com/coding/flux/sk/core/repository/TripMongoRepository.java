package com.coding.flux.sk.core.repository;

import com.coding.flux.sk.core.entity.Trip;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TripMongoRepository extends MongoRepository<Trip, String> {

    // Buscar viaje activo por vehículo
    Optional<Trip> findByVehicleIdAndActiveTrue(String vehicleId);

    // Listar viajes de un vehículo
    List<Trip> findByVehicleIdOrderByStartTimeDesc(String vehicleId);

    List<Trip> findByActiveTrue();

}
