package com.coding.flux.sk.core.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryCreate(
        @NotBlank(message = "Name is not blank")
        @Size(min = 3, max = 4, message = "Name length 3 and 4")
        String name,

        @NotBlank(message = "Description is not blank")
        @Size(min = 5, max = 100, message = "Description length 5 and 100")
        String description) {
}
