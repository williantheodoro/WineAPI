package org.example.domain.usecase;

import org.example.domain.model.Compra;
import org.example.domain.model.Vinho;
import org.example.domain.model.dto.ClienteFielDTO;

import java.util.List;
import java.util.Optional;

public interface ComprasUseCase {

    List<Compra> listaOrdenadaDeCompra();

    Optional<Compra> findMaiorCompraByAno(int ano);

    List<ClienteFielDTO> topClientesFieis();

    public Optional<Vinho> recomendacaoPorTipo(String cpfCliente);
}
