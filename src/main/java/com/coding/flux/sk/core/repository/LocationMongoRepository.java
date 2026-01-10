package com.coding.flux.sk.core.repository;

import com.coding.flux.sk.core.entity.Location;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LocationMongoRepository extends MongoRepository<Location, String> {

    Optional<Location> findTopByVehicleIdOrderByTimestampDesc(String vehicleId);

    List<Location> findByVehicleIdAndTimestampBetween(
            String vehicleId,
            LocalDateTime from,
            LocalDateTime to
    );
}
