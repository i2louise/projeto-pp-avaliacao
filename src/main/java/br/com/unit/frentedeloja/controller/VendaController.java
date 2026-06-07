package br.com.unit.frentedeloja.controller;

import br.com.unit.frentedeloja.model.ItemVenda;
import br.com.unit.frentedeloja.model.Produto;
import br.com.unit.frentedeloja.model.Venda;
import br.com.unit.frentedeloja.util.ArquivoUtil;
import br.com.unit.frentedeloja.util.ValidacaoCPF;

import java.util.List;

public class VendaController {

    public Venda iniciarVenda(String cpfCliente, String formaPagamento) {

        if (!ValidacaoCPF.validarCPF(cpfCliente)) {
            return null;
        }

        Venda venda = new Venda();

        venda.setIdVenda(ArquivoUtil.gerarProxIdVenda());
        venda.setCpfCliente(cpfCliente);
        venda.setFormaPagamento(formaPagamento);

        return venda;
    }

    public boolean adicionarProdutoNaVenda(Venda venda, String codigoBarras, int quantidade) {

        if (venda == null) {
            return false;
        }

        Produto produto = ArquivoUtil.buscarProdutoPorCodigo(codigoBarras);

        if (produto == null) {
            return false;
        }

        if (produto.getQtdEstoque() < quantidade) {
            return false;
        }

        ItemVenda item = new ItemVenda(produto, quantidade);
        venda.adicionarItem(item);

        return true;
    }

        public double calcularTotal(Venda venda) {
        return venda.calcularTotal();
        }

        public boolean concluirVenda(Venda venda) {
            if (venda == null) {
                return false;
            }

            if (venda.getItens().isEmpty()) {
                return false;
            }

            ArquivoUtil.concluirVenda(venda);
            return true;
        }

        public List<Venda> listarVendas() {
            return ArquivoUtil.listarVendas();
        }

        public List<ItemVenda> detalharVenda(int idVenda) {
            return ArquivoUtil.listarItensVenda(idVenda);
        }
}