package org.example.app.controller;

import org.example.app.service.ComprasService;
import org.example.domain.model.Compra;
import org.example.domain.model.Vinho;
import org.example.domain.model.dto.ClienteFielDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LojaVinhoController {
    private final ComprasService comprasService;

    @Autowired
    public LojaVinhoController(ComprasService comprasService) {
        this.comprasService = comprasService;
    }

    @GetMapping("/compras")
    public ResponseEntity<List<Compra>> getCompras() {
        return ResponseEntity.ok(comprasService.compras());
    }

    @GetMapping("/maior_compra/{ano}")
    public ResponseEntity<Compra> getMaiorCompraByAno(@PathVariable int ano) {
        return comprasService.maiorCompraPorAno(ano)
                .map(compra -> ResponseEntity.ok(compra))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/clientes-fieis")
    public ResponseEntity<List<ClienteFielDTO>> getTopClientesFieis() {
        return ResponseEntity.ok(comprasService.getTopClientesFieis());
    }

    @GetMapping("/recomendacao/cliente/tipo")
    public ResponseEntity<Vinho> recomendacaoVinhoPorTipo(@RequestParam String cpfCliente) {
        return comprasService.recomendacaoVinhoPorTipo(cpfCliente)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
