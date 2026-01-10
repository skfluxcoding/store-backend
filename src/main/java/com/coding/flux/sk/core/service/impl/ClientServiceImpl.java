package com.coding.flux.sk.core.service.impl;

import com.coding.flux.sk.common.exception.AlreadyExistsException;
import com.coding.flux.sk.common.exception.NotFoundException;
import com.coding.flux.sk.core.dto.ClientRequest;
import com.coding.flux.sk.core.dto.ClientResponse;
import com.coding.flux.sk.core.dto.RepClientGetAll;
import com.coding.flux.sk.core.entity.Client;
import com.coding.flux.sk.core.repository.ClientMongoRepository;
import com.coding.flux.sk.core.service.ClientService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientMongoRepository clientMongoRepository;

    public ClientServiceImpl(ClientMongoRepository clientMongoRepository) {
        this.clientMongoRepository = clientMongoRepository;
    }

    @Override
    public List<ClientResponse> findAll() {
        return clientMongoRepository.findAllByEnabledTrueOrderByCreatedAtDesc()
                .stream()
                .map(item -> new ClientResponse(
                        item.getIdClient(),
                        item.getFirstName(),
                        item.getLastName(),
                        item.getEmail(),
                        item.getPhone(),
                        item.getAddress(),
                        item.getEnabled(),
                        item.getCreatedAt(),
                        item.getUpdatedAt()
                ))
                .toList();
    }

    @Override
    public ClientResponse create(ClientRequest dto) {
        if (clientMongoRepository.existsByEnabledTrueAndEmailIgnoreCase(dto.email())) {
            throw new AlreadyExistsException("Email " + dto.email() + " already exists");
        }

        var client = Client.builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .email(dto.email().toLowerCase())
                .phone(dto.phone())
                .address(dto.address())
                .enabled(true)
                .createdAt(LocalDateTime.now())
                .build();

        var saved = clientMongoRepository.save(client);

        return new ClientResponse(
                saved.getIdClient(),
                saved.getFirstName(),
                saved.getLastName(),
                saved.getEmail(),
                saved.getPhone(),
                saved.getAddress(),
                saved.getEnabled(),
                saved.getCreatedAt(),
                saved.getUpdatedAt()
        );
    }

    @Override
    public ClientResponse update(String id, ClientRequest dto) {
        var client = clientMongoRepository.findByEnabledTrueAndIdClient(id)
                .orElseThrow(() -> new NotFoundException("Client not found"));

        if (clientMongoRepository.existsByEnabledTrueAndEmailIgnoreCaseAndIdClientNot(dto.email(), id)) {
            throw new AlreadyExistsException("Email " + dto.email() + " already exists");
        }

        client.setFirstName(dto.firstName());
        client.setLastName(dto.lastName());
        client.setEmail(dto.email().toLowerCase());
        client.setPhone(dto.phone());
        client.setAddress(dto.address());
        client.setUpdatedAt(LocalDateTime.now());

        var saved = clientMongoRepository.save(client);

        return new ClientResponse(
                saved.getIdClient(),
                saved.getFirstName(),
                saved.getLastName(),
                saved.getEmail(),
                saved.getPhone(),
                saved.getAddress(),
                saved.getEnabled(),
                saved.getCreatedAt(),
                saved.getUpdatedAt()
        );
    }

    @Override
    public ClientResponse findById(String id) {
        var client = clientMongoRepository.findByEnabledTrueAndIdClient(id)
                .orElseThrow(() -> new NotFoundException("Client " + id + " not found"));

        return new ClientResponse(
                client.getIdClient(),
                client.getFirstName(),
                client.getLastName(),
                client.getEmail(),
                client.getPhone(),
                client.getAddress(),
                client.getEnabled(),
                client.getCreatedAt(),
                client.getUpdatedAt()
        );
    }

    @Override
    public void deleteById(String id) {
        var client = clientMongoRepository.findByEnabledTrueAndIdClient(id)
                .orElseThrow(() -> new NotFoundException("Client not found"));

        client.setEnabled(false);
        client.setUpdatedAt(LocalDateTime.now());

        clientMongoRepository.save(client);
    }

    @Override
    public byte[] generateReportGetAllClient() throws JRException {
        var data = clientMongoRepository.findAllByEnabledTrueOrderByCreatedAtDesc()
                .stream()
                .map(item -> new RepClientGetAll(
                        item.getIdClient(),
                        item.getFirstName(),
                        item.getLastName(),
                        item.getEmail(),
                        item.getPhone()
                ))
                .toList();

        InputStream jasperStream =
                getClass().getResourceAsStream("/reports/GetAllClient.jasper");

        if (jasperStream == null) {
            throw new JRException("Report file GetAllClient.jasper not found");
        }

        JasperReport jasperReport =
                (JasperReport) JRLoader.loadObject(jasperStream);

        var dataSource =
                new JRBeanCollectionDataSource(data);

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("REPORT_TITLE", "Listado de Clientes");

        return JasperRunManager.runReportToPdf(
                jasperReport,
                parameters,
                dataSource
        );
    }
}
