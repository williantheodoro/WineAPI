package org.example.app.service.impl;

import org.example.app.service.ComprasService;
import org.example.domain.model.Compra;
import org.example.domain.model.Vinho;
import org.example.domain.model.dto.ClienteFielDTO;
import org.example.domain.usecase.impl.ComprasUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ComprasServiceImplTest {

    @Mock
    private ComprasUseCaseImpl comprasUseCaseImpl;

    @InjectMocks
    private ComprasServiceImpl comprasServiceImpl;

    private Compra compra;
    private Vinho vinho;
    private ClienteFielDTO clienteFielDTO;

    @BeforeEach
    void setUp() {
        compra = new Compra();
        vinho = new Vinho();
        clienteFielDTO = new ClienteFielDTO();
    }

    @Test
    void compras_shouldReturnListOfCompras() {
        List<Compra> compras = Collections.singletonList(compra);

        when(comprasUseCaseImpl.listaOrdenadaDeCompra()).thenReturn(compras);

        List<Compra> result = comprasServiceImpl.compras();

        assertNotNull(result);
        assertEquals(compras, result);
        verify(comprasUseCaseImpl, times(1)).listaOrdenadaDeCompra();
    }

    @Test
    void maiorCompraPorAno_shouldReturnCompra_whenCompraIsPresent() {
        int ano = 2025;
        when(comprasUseCaseImpl.findMaiorCompraByAno(ano)).thenReturn(Optional.of(compra));

        Optional<Compra> result = comprasServiceImpl.maiorCompraPorAno(ano);

        assertTrue(result.isPresent());
        assertEquals(compra, result.get());
        verify(comprasUseCaseImpl, times(1)).findMaiorCompraByAno(ano);
    }

    @Test
    void maiorCompraPorAno_shouldReturnEmpty_whenCompraIsNotPresent() {
        int ano = 2025;
        when(comprasUseCaseImpl.findMaiorCompraByAno(ano)).thenReturn(Optional.empty());

        Optional<Compra> result = comprasServiceImpl.maiorCompraPorAno(ano);

        assertFalse(result.isPresent());
        verify(comprasUseCaseImpl, times(1)).findMaiorCompraByAno(ano);
    }

    @Test
    void getTopClientesFieis_shouldReturnListOfClientesFieis() {
        List<ClienteFielDTO> clientesFieis = Collections.singletonList(clienteFielDTO);

        when(comprasUseCaseImpl.topClientesFieis()).thenReturn(clientesFieis);

        List<ClienteFielDTO> result = comprasServiceImpl.getTopClientesFieis();

        assertNotNull(result);
        assertEquals(clientesFieis, result);
        verify(comprasUseCaseImpl, times(1)).topClientesFieis();
    }

    @Test
    void recomendacaoVinhoPorTipo_shouldReturnVinho_whenVinhoIsPresent() {
        String cpfCliente = "123456789";
        when(comprasUseCaseImpl.recomendacaoPorTipo(cpfCliente)).thenReturn(Optional.of(vinho));

        Optional<Vinho> result = comprasServiceImpl.recomendacaoVinhoPorTipo(cpfCliente);

        assertTrue(result.isPresent());
        assertEquals(vinho, result.get());
        verify(comprasUseCaseImpl, times(1)).recomendacaoPorTipo(cpfCliente);
    }

    @Test
    void recomendacaoVinhoPorTipo_shouldReturnEmpty_whenVinhoIsNotPresent() {
        String cpfCliente = "123456789";
        when(comprasUseCaseImpl.recomendacaoPorTipo(cpfCliente)).thenReturn(Optional.empty());

        Optional<Vinho> result = comprasServiceImpl.recomendacaoVinhoPorTipo(cpfCliente);

        assertFalse(result.isPresent());
        verify(comprasUseCaseImpl, times(1)).recomendacaoPorTipo(cpfCliente);
    }
}