package com.coding.flux.sk.core.controller;

import com.coding.flux.sk.core.dto.ActiveTripResponse;
import com.coding.flux.sk.core.service.TripQueryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripQueryController {

    private final TripQueryService tripQueryService;

    public TripQueryController(TripQueryService tripQueryService) {
        this.tripQueryService = tripQueryService;
    }

    @GetMapping("/active")
    public List<ActiveTripResponse> getActiveTrips() {
        return tripQueryService.getActiveTrips();
    }
}
