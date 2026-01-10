package com.coding.flux.sk.core.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "locations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {

    @Id
    private String id;

    // Vehículo que envía la ubicación
    private String vehicleId;

    // Viaje al que pertenece esta ubicación
    private String tripId;

    // Coordenadas
    private Double latitude;
    private Double longitude;

    // Velocidad en km/h
    private Double speed;

    // Dirección (grados)
    private Double heading;

    // Momento exacto de la ubicación
    private LocalDateTime timestamp;
}
