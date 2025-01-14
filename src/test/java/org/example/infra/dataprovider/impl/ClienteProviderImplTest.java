package org.example.infra.dataprovider.impl;

import org.example.domain.model.Cliente;
import org.example.domain.ClienteProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClienteProviderImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ClienteProviderImpl clienteProviderImpl;

    private String urlClientes = "http://api.clientes.com";
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        clienteProviderImpl = new ClienteProviderImpl(restTemplate);
        clienteProviderImpl.setUrlClientes("http://api.clientes.com");

        cliente = new Cliente("João Silva", "123456789");
    }

    @Test
    void getClientes_shouldReturnListOfClientes_whenRestTemplateReturnsClientes() {
        Cliente[] clientesArray = {cliente};
        when(restTemplate.getForObject(urlClientes, Cliente[].class)).thenReturn(clientesArray);

        List<Cliente> result = clienteProviderImpl.getClientes();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(cliente, result.get(0));
    }

    @Test
    void getClientes_shouldThrowException_whenRestTemplateThrowsException() {
        when(restTemplate.getForObject(urlClientes, Cliente[].class)).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> clienteProviderImpl.getClientes());
    }
}