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

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
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

    public void setCaminhoImagem(String caminhoImagem) {
        this.caminhoImagem = caminhoImagem;
    }

    @Override
    public String toString() {
        return nome + " - R$ " + preco;
    }
}
