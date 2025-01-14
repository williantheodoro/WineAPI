package org.example.domain.model;

public class Compra {
    private String codigo;
    private int quantidade;
    private String nome;
    private String cpf;
    private double valorTotal;

    private Vinho vinho;

    public Compra(String codigo, int quantidade, String nome, String cpf) {
        this.codigo = codigo;
        this.quantidade = quantidade;
        this.nome = nome;
        this.cpf = cpf;
    }

    public Compra() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Vinho getVinho() {
        return vinho;
    }

    public void setVinho(Vinho vinho) {
        this.vinho = vinho;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
