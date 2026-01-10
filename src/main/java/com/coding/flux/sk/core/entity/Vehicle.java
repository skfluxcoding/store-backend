package com.coding.flux.sk.core.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "vehicles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    private String id;

    // Código único del vehículo (ej: VH-001)
    private String code;

    // Nombre descriptivo
    private String name;

    // Indica si el vehículo está habilitado
    private Boolean active;

    // Fecha de creación
    private LocalDateTime createdAt;
}
