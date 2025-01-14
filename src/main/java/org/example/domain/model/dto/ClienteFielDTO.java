package org.example.domain.model.dto;

import org.example.domain.model.Vinho;

public class ClienteFielDTO {

    private String cpf;
    private String nome;
    private int comprasRecorrentes;
    private double totalValor;

    public ClienteFielDTO(String cpf, String nome, int comprasRecorrentes, double totalValor) {
        this.cpf = cpf;
        this.nome = nome;
        this.comprasRecorrentes = comprasRecorrentes;
        this.totalValor = totalValor;
    }

    public ClienteFielDTO() {
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public int getComprasRecorrentes() {
        return comprasRecorrentes;
    }

    public double getTotalValor() {
        return totalValor;
    }

    public void setComprasRecorrentes(int comprasRecorrentes) {
        this.comprasRecorrentes = comprasRecorrentes;
    }

    public void addCompraRecorrente(Vinho vinho, int quantidade, double preco) {
        // Incrementa o contador de compras recorrentes se o vinho não foi adicionado anteriormente
        this.comprasRecorrentes++;
        this.totalValor += quantidade * preco;
    }
}
