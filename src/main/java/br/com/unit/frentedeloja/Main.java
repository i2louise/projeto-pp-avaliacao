package br.com.unit.frentedeloja;

import br.com.unit.frentedeloja.controller.ProdutoController;
import br.com.unit.frentedeloja.controller.VendaController;
import br.com.unit.frentedeloja.model.ItemVenda;
import br.com.unit.frentedeloja.model.Produto;
import br.com.unit.frentedeloja.model.Venda;
import br.com.unit.frentedeloja.util.ArquivoUtil;

import java.util.List;

// MAIN PARA TESTE PT. 4

public class Main {
    public static void main(String[] args) {

        VendaController vendaController = new VendaController();

        Venda venda = vendaController.iniciarVenda("12345678900", "PIX");

        boolean adicionouMouse = vendaController.adicionarProdutoNaVenda(venda, "519118", 1);

        boolean adicionouTeclado = vendaController.adicionarProdutoNaVenda(venda, "154567", 2);

        if (adicionouMouse && adicionouTeclado) {
            System.out.println("Produto adicionado com sucesso!");
        } else {
            System.out.println("Prouto não encontrado ou fora de estoque.");
        }

        System.out.println("Total da venda: R$ " + vendaController.calcularTotal(venda));

        boolean concluiu = vendaController.concluirVenda(venda);

        if (concluiu) {
            System.out.println("Venda concluida com sucesso!");
        } else {
            System.out.println("Venda não pôde ser concluída.");
        }

        System.out.println("ID da venda: " + venda.getIdVenda());
        System.out.println("CPF: " + venda.getCpfCliente());
        System.out.println("Pagamento: " + venda.getFormaPagamento());
    }
}
