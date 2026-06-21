package br.com.unit.frentedeloja.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venda {

    private int idVenda;
    private String cpfCliente;
    private LocalDateTime dataVenda;
    private String formaPagamento;
    private List<ItemVenda> itens;

    public Venda() {
        this.itens = new ArrayList<>();
        this.dataVenda = LocalDateTime.now();
    }

    public void adicionarItem(ItemVenda itemVenda) {
        itens.add(itemVenda);
    }

    public double calcularTotal() {
        double total = 0;

        for (ItemVenda itemVenda : itens) {
            total += itemVenda.calcularSubtotal();
        }

        return total;
    }

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public LocalDateTime getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDateTime dataVenda) {
        this.dataVenda = dataVenda;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }
}