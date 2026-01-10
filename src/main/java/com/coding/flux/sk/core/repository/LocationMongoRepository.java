package com.coding.flux.sk.core.repository;

import com.coding.flux.sk.core.entity.Location;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface LocationMongoRepository extends MongoRepository<Location, String> {

    // Obtener todas las ubicaciones de un viaje (ruta completa)
    List<Location> findByTripIdOrderByTimestampAsc(String tripId);

    // Última ubicación de un vehículo
    List<Location> findTop1ByVehicleIdOrderByTimestampDesc(String vehicleId);


    Optional<Location> findFirstByTripIdOrderByTimestampDesc(String tripId);

}
