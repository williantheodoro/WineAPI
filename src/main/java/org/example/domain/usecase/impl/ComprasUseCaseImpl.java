package org.example.domain.usecase.impl;

import org.example.domain.ClienteProvider;
import org.example.domain.VinhosProvider;
import org.example.domain.model.Cliente;
import org.example.domain.model.Compra;
import org.example.domain.model.Vinho;
import org.example.domain.model.dto.ClienteFielDTO;
import org.example.domain.usecase.ComprasUseCase;

import java.util.*;
import java.util.stream.Collectors;

public class ComprasUseCaseImpl implements ComprasUseCase {

    private final VinhosProvider vinhoProvider;
    private final ClienteProvider clienteProvider;

    public ComprasUseCaseImpl(VinhosProvider vinhoProvider, ClienteProvider clienteProvider) {
        this.vinhoProvider = vinhoProvider;
        this.clienteProvider = clienteProvider;
    }

    public List<Compra> listaOrdenadaDeCompra() {
        List<Cliente> clientes = clienteProvider.getClientes();
        List<Vinho> vinhos = vinhoProvider.getVinhos();

        return clientes.stream()
                .flatMap(cliente -> cliente.getCompras().stream()
                        .map(compra -> mapCompraParaVinho(compra, cliente, vinhos)))
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingDouble(Compra::getValorTotal))
                .collect(Collectors.toList());
    }

    public Optional<Compra> findMaiorCompraByAno(int ano) {
        List<Vinho> vinhos = vinhoProvider.getVinhos();
        Set<Integer> vinhosDoAno = vinhos.stream()
                .filter(vinho -> vinho.getAno_compra() == ano)
                .map(Vinho::getCodigo)
                .collect(Collectors.toSet());

        return clienteProvider.getClientes().stream()
                .flatMap(cliente -> cliente.getCompras().stream()
                        .filter(compra -> vinhosDoAno.contains(Integer.parseInt(compra.getCodigo())))
                        .map(compra -> mapCompraParaVinho(compra, cliente, vinhos)))
                .filter(Objects::nonNull)
                .max(Comparator.comparingDouble(Compra::getValorTotal));
    }

    public List<ClienteFielDTO> topClientesFieis() {
        List<Cliente> clientes = clienteProvider.getClientes();
        List<Vinho> vinhos = vinhoProvider.getVinhos();

        Map<String, Vinho> vinhosPorCodigo = vinhos.stream()
                .collect(Collectors.toMap(vinho -> String.valueOf(vinho.getCodigo()), vinho -> vinho));

        Map<String, ClienteFielDTO> clienteFielMap = new HashMap<>();

        for (Cliente cliente : clientes) {
            for (Compra compra : cliente.getCompras()) {
                Vinho vinho = vinhosPorCodigo.get(compra.getCodigo());
                if (vinho != null) {
                    clienteFielMap.computeIfAbsent(cliente.getCpf(), cpf ->
                                    new ClienteFielDTO(cliente.getCpf(), cliente.getNome(), 0, 0.0))
                            .addCompraRecorrente(vinho, compra.getQuantidade(), vinho.getPreco());
                }
            }
        }

        return obterTop3ClientesFieis(clienteFielMap);
    }

    private List<ClienteFielDTO> obterTop3ClientesFieis(Map<String, ClienteFielDTO> clienteFielMap) {
        return clienteFielMap.values().stream()
                .sorted(Comparator.comparingInt(ClienteFielDTO::getComprasRecorrentes)
                        .thenComparingDouble(ClienteFielDTO::getTotalValor).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    public Optional<Vinho> recomendacaoPorTipo(String cpfCliente) {
        Cliente cliente = getClientePorCpf(cpfCliente);
        if (cliente == null) {
            return Optional.empty();
        }

        Map<String, Long> tipoVinhoCount = cliente.getCompras().stream()
                .map(compra -> getVinhoPorCodigo(vinhoProvider.getVinhos(), compra.getCodigo()))
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Vinho::getTipo_vinho, Collectors.counting()));

        return tipoVinhoCount.entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .flatMap(tipo -> vinhoProvider.getVinhos().stream()
                        .filter(vinho -> vinho.getTipo_vinho().equals(tipo))
                        .findFirst());
    }

    private Compra mapCompraParaVinho(Compra compra, Cliente cliente, List<Vinho> vinhos) {
        Vinho vinho = getVinhoPorCodigo(vinhos, compra.getCodigo());
        if (vinho == null) return null;

        compra.setVinho(vinho);
        compra.setValorTotal(compra.getQuantidade() * vinho.getPreco());
        compra.setNome(cliente.getNome());
        compra.setCpf(cliente.getCpf());
        return compra;
    }

    private Vinho getVinhoPorCodigo(List<Vinho> vinhos, String codigo) {
        return vinhos.stream()
                .filter(vinho -> String.valueOf(vinho.getCodigo()).equals(codigo))
                .findFirst()
                .orElse(null);
    }

    private Cliente getClientePorCpf(String cpfCliente) {
        return clienteProvider.getClientes().stream()
                .filter(cliente -> cliente.getCpf().equals(cpfCliente))
                .findFirst()
                .orElse(null);
    }
}