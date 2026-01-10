package com.coding.flux.sk.core.service;

import com.coding.flux.sk.core.entity.Trip;
import com.coding.flux.sk.core.entity.Vehicle;
import com.coding.flux.sk.core.repository.TripMongoRepository;
import com.coding.flux.sk.core.repository.VehicleMongoRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TripService {

    private final TripMongoRepository tripRepository;
    private final VehicleMongoRepository vehicleRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public TripService(
            TripMongoRepository tripRepository,
            VehicleMongoRepository vehicleRepository,
            SimpMessagingTemplate messagingTemplate
    ) {
        this.tripRepository = tripRepository;
        this.vehicleRepository = vehicleRepository;
        this.messagingTemplate = messagingTemplate;
    }

    // Iniciar viaje
    public Trip iniciarViaje(String vehicleId) {

        Vehicle vehicle = vehicleRepository.findByIdAndActiveTrue(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehículo no válido"));

        // Verificar si ya tiene viaje activo
        tripRepository.findByVehicleIdAndActiveTrue(vehicleId)
                .ifPresent(t -> {
                    throw new RuntimeException("El vehículo ya tiene un viaje activo");
                });

        Trip trip = Trip.builder()
                .vehicleId(vehicleId)
                .startTime(LocalDateTime.now())
                .active(true)
                .build();

        return tripRepository.save(trip);
    }

    // Finalizar viaje
    public void finalizarViaje(String vehicleId) {

        Trip trip = tripRepository.findByVehicleIdAndActiveTrue(vehicleId)
                .orElseThrow(() -> new RuntimeException("No hay viaje activo para este vehículo"));

        trip.setActive(false);
        trip.setEndTime(LocalDateTime.now());

        tripRepository.save(trip);

        // 🔔 Emitir evento por WebSocket
        Map<String, String> evento = new HashMap<>();
        evento.put("tripId", trip.getId());
        evento.put("status", "ENDED");

        messagingTemplate.convertAndSend("/topic/trips", evento);
    }

    // Listar viajes de un vehículo
    public List<Trip> listarViajesPorVehiculo(String vehicleId) {
        return tripRepository.findByVehicleIdOrderByStartTimeDesc(vehicleId);
    }

    // Obtener viaje activo
    public Trip obtenerViajeActivo(String vehicleId) {
        return tripRepository.findByVehicleIdAndActiveTrue(vehicleId)
                .orElseThrow(() -> new RuntimeException("El vehículo no tiene viaje activo"));
    }
}
