package org.example.domain.usecase.impl;

import org.example.domain.ClienteProvider;
import org.example.domain.VinhosProvider;
import org.example.domain.model.Cliente;
import org.example.domain.model.Compra;
import org.example.domain.model.Vinho;
import org.example.domain.model.dto.ClienteFielDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ComprasUseCaseImplTest {

    @Mock
    private VinhosProvider vinhoProvider;

    @Mock
    private ClienteProvider clienteProvider;

    @InjectMocks
    private ComprasUseCaseImpl comprasUseCaseImpl;

    private Cliente cliente;
    private Vinho vinho;
    private Compra compra;
    private ClienteFielDTO clienteFielDTO;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("João Silva", "123456");
        vinho = new Vinho(1, "Vinho Tinto", 16, "2023", 2020);
        compra = new Compra("1", 2, "1", "123456");
        cliente.addCompra(compra);
        clienteFielDTO = new ClienteFielDTO("123", "João Silva", 1, 200.0);
    }

    @Test
    void listaOrdenadaDeCompra_shouldReturnOrderedListOfCompras() {
        List<Cliente> clientes = Collections.singletonList(cliente);
        List<Vinho> vinhos = Collections.singletonList(vinho);

        when(clienteProvider.getClientes()).thenReturn(clientes);
        when(vinhoProvider.getVinhos()).thenReturn(vinhos);

        List<Compra> result = comprasUseCaseImpl.listaOrdenadaDeCompra();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(compra, result.get(0));
    }

    @Test
    void findMaiorCompraByAno_shouldReturnMaiorCompra_whenCompraIsPresent() {
        int ano = 2020;
        List<Cliente> clientes = Collections.singletonList(cliente);
        List<Vinho> vinhos = Collections.singletonList(vinho);
        Set<Integer> vinhosDoAno = new HashSet<>(Collections.singletonList(vinho.getCodigo()));

        when(clienteProvider.getClientes()).thenReturn(clientes);
        when(vinhoProvider.getVinhos()).thenReturn(vinhos);

        Optional<Compra> result = comprasUseCaseImpl.findMaiorCompraByAno(ano);

        assertTrue(result.isPresent());
        assertEquals(compra, result.get());
    }

    @Test
    void findMaiorCompraByAno_shouldReturnEmpty_whenCompraIsNotPresent() {
        int ano = 2024;
        List<Cliente> clientes = Collections.singletonList(cliente);
        List<Vinho> vinhos = Collections.singletonList(vinho);
        Set<Integer> vinhosDoAno = new HashSet<>(Collections.singletonList(vinho.getCodigo()));

        when(clienteProvider.getClientes()).thenReturn(clientes);
        when(vinhoProvider.getVinhos()).thenReturn(vinhos);

        Optional<Compra> result = comprasUseCaseImpl.findMaiorCompraByAno(ano);

        assertFalse(result.isPresent());
    }

    @Test
    void topClientesFieis_shouldReturnTop3ClientesFieis() {
        List<Cliente> clientes = Collections.singletonList(cliente);
        List<Vinho> vinhos = Collections.singletonList(vinho);
        Map<String, ClienteFielDTO> clienteFielMap = new HashMap<>();
        clienteFielMap.put(cliente.getCpf(), clienteFielDTO);

        when(clienteProvider.getClientes()).thenReturn(clientes);
        when(vinhoProvider.getVinhos()).thenReturn(vinhos);

        List<ClienteFielDTO> result = comprasUseCaseImpl.topClientesFieis();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void recomendacaoPorTipo_shouldReturnVinho_whenVinhoIsPresent() {
        String cpfCliente = "123456";
        List<Cliente> clientes = Collections.singletonList(cliente);
        List<Vinho> vinhos = Collections.singletonList(vinho);

        when(clienteProvider.getClientes()).thenReturn(clientes);
        when(vinhoProvider.getVinhos()).thenReturn(vinhos);

        Optional<Vinho> result = comprasUseCaseImpl.recomendacaoPorTipo(cpfCliente);

        assertTrue(result.isPresent());
        assertEquals(vinho, result.get());
    }

    @Test
    void recomendacaoPorTipo_shouldReturnEmpty_whenClienteNotFound() {
        String cpfCliente = "123456";
        List<Cliente> clientes = Collections.singletonList(cliente);
        List<Vinho> vinhos = Collections.singletonList(vinho);

        Optional<Vinho> result = comprasUseCaseImpl.recomendacaoPorTipo(cpfCliente);

        assertFalse(result.isPresent());
    }
}