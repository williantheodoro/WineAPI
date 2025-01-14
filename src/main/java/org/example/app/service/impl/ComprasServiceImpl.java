package org.example.app.service.impl;

import org.example.app.service.ComprasService;
import org.example.domain.model.Compra;
import org.example.domain.model.Vinho;
import org.example.domain.model.dto.ClienteFielDTO;
import org.example.domain.usecase.impl.ComprasUseCaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComprasServiceImpl implements ComprasService {

    private final ComprasUseCaseImpl comprasUseCaseImpl;

    @Autowired
    public ComprasServiceImpl(ComprasUseCaseImpl comprasUseCaseImpl) {
        this.comprasUseCaseImpl = comprasUseCaseImpl;
    }

    @Override
    public List<Compra> compras() {
        return comprasUseCaseImpl.listaOrdenadaDeCompra();
    }

    @Override
    public Optional<Compra> maiorCompraPorAno(int ano) {
        return comprasUseCaseImpl.findMaiorCompraByAno(ano);
    }

    @Override
    public List<ClienteFielDTO> getTopClientesFieis() {
        return comprasUseCaseImpl.topClientesFieis();
    }

    @Override
    public Optional<Vinho> recomendacaoVinhoPorTipo(String cpfCliente) {
        return comprasUseCaseImpl.recomendacaoPorTipo(cpfCliente);
    }
}
