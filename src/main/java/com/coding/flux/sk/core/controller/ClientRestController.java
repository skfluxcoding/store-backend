package com.coding.flux.sk.core.controller;

import com.coding.flux.sk.core.dto.ClientRequest;
import com.coding.flux.sk.core.dto.ClientResponse;
import com.coding.flux.sk.core.service.ClientService;
import jakarta.validation.Valid;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientRestController {

    private final ClientService clientService;

    public ClientRestController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<ClientResponse>> findAll() {
        var clients = clientService.findAll();
        return ResponseEntity.ok(clients);
    }

    @PostMapping
    public ResponseEntity<ClientResponse> create(@Valid @RequestBody ClientRequest dto) {
        var client = clientService.create(dto);

        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(client.clientId())
                .toUri();

        return ResponseEntity.created(location).body(client);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findById(@PathVariable String id) {
        var client = clientService.findById(id);
        return ResponseEntity.ok(client);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(@PathVariable String id,
                                                 @Valid @RequestBody ClientRequest dto) {
        var client = clientService.update(id, dto);
        return ResponseEntity.ok(client);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        clientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reports/generateReportGetAllClient")
    public ResponseEntity<byte[]> generateReportGetAllClient() throws JRException {
        byte[] pdf = clientService.generateReportGetAllClient();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=clients.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
