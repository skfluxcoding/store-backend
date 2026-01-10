package com.coding.flux.sk.core.service;

import com.coding.flux.sk.core.dto.ClientRequest;
import com.coding.flux.sk.core.dto.ClientResponse;
import net.sf.jasperreports.engine.JRException;

public interface ClientService
        extends GenericService<ClientResponse, ClientRequest> {
    // reports
    byte[] generateReportGetAllClient() throws JRException;
}
