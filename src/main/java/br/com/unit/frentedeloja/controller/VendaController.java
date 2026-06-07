package br.com.unit.frentedeloja.controller;

import br.com.unit.frentedeloja.model.Venda;
import br.com.unit.frentedeloja.util.ArquivoUtil;

public class VendaController {

    public Venda iniciarVenda(String cpfCliente, String formaPagamento) {
        Venda venda = new Venda();

        venda.setIdVenda(ArquivoUtil.gerarProxIdVenda());
        venda.setCpfCliente(cpfCliente);
        venda.setFormaPagamento(formaPagamento);

        return venda;


        // FINALIZAR RESTO !!!!!!!!!!!!!
    }


}
