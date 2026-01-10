package com.coding.flux.sk.core.repository;

import com.coding.flux.sk.core.entity.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface VehicleMongoRepository extends MongoRepository<Vehicle, String> {

    Optional<Vehicle> findByTokenAndActiveTrue(String token);

    boolean existsByToken(String token);
}
