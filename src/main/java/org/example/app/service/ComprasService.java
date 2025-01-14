package org.example.app.service;

import org.example.domain.model.Compra;
import org.example.domain.model.Vinho;
import org.example.domain.model.dto.ClienteFielDTO;

import java.util.List;
import java.util.Optional;

public interface ComprasService {

    List<Compra> compras();

    Optional<Compra> maiorCompraPorAno(int ano);

    List<ClienteFielDTO> getTopClientesFieis();

    Optional<Vinho> recomendacaoVinhoPorTipo(String cpfCliente);
}
