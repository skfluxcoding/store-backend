package com.coding.flux.sk.core.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "vehicles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    private String id;

    @Indexed(unique = true)
    private String code;

    private String name;

    @Indexed(unique = true)
    private String token;

    private Boolean active = true;
}
