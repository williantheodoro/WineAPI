package org.example.infra.dataprovider.impl;

import lombok.Setter;
import org.example.domain.model.Cliente;
import org.example.domain.ClienteProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

@Service
@Setter
public class ClienteProviderImpl implements ClienteProvider {
    @Value("${url.clientes}")
    private String urlClientes;

    private final RestTemplate restTemplate;

    public ClienteProviderImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Cliente> getClientes() {
        Cliente[] clientes = restTemplate.getForObject(urlClientes, Cliente[].class);
        return Arrays.asList(clientes);
    }
}
