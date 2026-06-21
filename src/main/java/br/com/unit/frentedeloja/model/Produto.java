package br.com.unit.frentedeloja.model;

public class Produto {
    private String codigoBarras;
    private String nome;
    private double preco;
    private int qtdEstoque;
    private String caminhoImagem;

    public Produto(String codigoBarras, String nome, double preco, int qtdEstoque, String caminhoImagem) {
        this.codigoBarras = codigoBarras;
        this.nome = nome;
        this.preco = preco;
        this.qtdEstoque = qtdEstoque;
        this.caminhoImagem = caminhoImagem;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public String getCaminhoImagem() {
        return caminhoImagem;
    }

    @Override
    public String toString() {
        return codigoBarras + " - " + nome;
    }
}
