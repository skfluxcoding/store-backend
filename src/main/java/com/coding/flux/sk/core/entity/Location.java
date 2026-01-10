package com.coding.flux.sk.core.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
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

    @Indexed
    private String vehicleId;

    private Double latitude;
    private Double longitude;
    private Double speed;
    private Double heading;

    @Indexed
    private LocalDateTime timestamp;
}
