package org.example.app.controller;

import org.example.app.service.ComprasService;
import org.example.domain.model.Compra;
import org.example.domain.model.Vinho;
import org.example.domain.model.dto.ClienteFielDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class LojaVinhoControllerTest {

    @Mock
    private ComprasService comprasService;

    @InjectMocks
    private LojaVinhoController lojaVinhoController;

    private Compra compra;
    private Vinho vinho;
    private ClienteFielDTO clienteFielDTO;

    @BeforeEach
    void setUp() {
        compra = new Compra();  // Criação de um objeto Compra para os testes
        vinho = new Vinho();  // Criação de um objeto Vinho para os testes
        clienteFielDTO = new ClienteFielDTO();  // Criação de um objeto ClienteFielDTO para os testes
    }

    @Test
    void getCompras_shouldReturnOk() {
        List<Compra> compras = Collections.singletonList(compra);

        when(comprasService.compras()).thenReturn(compras);

        ResponseEntity<List<Compra>> response = lojaVinhoController.getCompras();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(compras, response.getBody());
    }

    @Test
    void getMaiorCompraByAno_shouldReturnOk_whenCompraIsPresent() {
        int ano = 2025;
        when(comprasService.maiorCompraPorAno(ano)).thenReturn(Optional.of(compra));

        ResponseEntity<Compra> response = lojaVinhoController.getMaiorCompraByAno(ano);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(compra, response.getBody());
    }

    @Test
    void getMaiorCompraByAno_shouldReturnNoContent_whenCompraIsNotPresent() {
        int ano = 2025;
        when(comprasService.maiorCompraPorAno(ano)).thenReturn(Optional.empty());

        ResponseEntity<Compra> response = lojaVinhoController.getMaiorCompraByAno(ano);

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void getTopClientesFieis_shouldReturnOk() {
        List<ClienteFielDTO> clientesFieis = Collections.singletonList(clienteFielDTO);

        when(comprasService.getTopClientesFieis()).thenReturn(clientesFieis);

        ResponseEntity<List<ClienteFielDTO>> response = lojaVinhoController.getTopClientesFieis();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(clientesFieis, response.getBody());
    }

    @Test
    void recomendacaoVinhoPorTipo_shouldReturnOk_whenVinhoIsPresent() {
        String cpfCliente = "123456789";
        when(comprasService.recomendacaoVinhoPorTipo(cpfCliente)).thenReturn(Optional.of(vinho));

        ResponseEntity<Vinho> response = lojaVinhoController.recomendacaoVinhoPorTipo(cpfCliente);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(vinho, response.getBody());
    }

    @Test
    void recomendacaoVinhoPorTipo_shouldReturnNotFound_whenVinhoIsNotPresent() {
        String cpfCliente = "123456789";
        when(comprasService.recomendacaoVinhoPorTipo(cpfCliente)).thenReturn(Optional.empty());

        ResponseEntity<Vinho> response = lojaVinhoController.recomendacaoVinhoPorTipo(cpfCliente);

        assertEquals(404, response.getStatusCodeValue());
    }
}