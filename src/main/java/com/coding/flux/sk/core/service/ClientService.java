package com.coding.flux.sk.core.service;

import com.coding.flux.sk.core.dto.ClientRequest;
import com.coding.flux.sk.core.dto.ClientResponse;
import net.sf.jasperreports.engine.JRException;

import java.util.List;

public interface ClientService {

    List<ClientResponse> findAll();

    ClientResponse create(ClientRequest dto);

    ClientResponse update(String id, ClientRequest dto);

    ClientResponse findById(String id);

    void deleteById(String id);

    // reports
    byte[] generateReportGetAllClient() throws JRException;
}
