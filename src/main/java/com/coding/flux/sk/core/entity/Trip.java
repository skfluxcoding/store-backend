package com.coding.flux.sk.core.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "trips")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trip {

    @Id
    private String id;

    // Vehículo asociado al viaje
    private String vehicleId;

    // Inicio del viaje
    private LocalDateTime startTime;

    // Fin del viaje (null mientras esté activo)
    private LocalDateTime endTime;

    // Indica si el viaje está activo
    private Boolean active;
}
