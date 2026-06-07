package br.com.unit.frentedeloja;

import br.com.unit.frentedeloja.controller.ProdutoController;
import br.com.unit.frentedeloja.controller.VendaController;
import br.com.unit.frentedeloja.model.ItemVenda;
import br.com.unit.frentedeloja.model.Produto;
import br.com.unit.frentedeloja.model.Venda;
import br.com.unit.frentedeloja.util.ArquivoUtil;
import br.com.unit.frentedeloja.util.ValidacaoCPF;

import java.util.List;

// MAIN PARA TESTE PT. 4

public class Main {
    public static void main(String[] args) {

        ProdutoController produtoController = new ProdutoController();

        /* ADD PRODUTOS

        Produto produto1 = new Produto("519118", "Mouse", 120.00, 10, "resouces/imagens/mouse.png");

        produtoController.cadastrarProduto(produto1);

        Produto produto2 = new Produto("154567", "Teclado", 250.00, 25, "imagens/teclado.png");

        produtoController.cadastrarProduto(produto2);

         */

       // TESTE C/ CPF VÁLIDO

        VendaController vendaController = new VendaController();

        Venda venda1 = vendaController.iniciarVenda("36538661033", "PIX");

        if (venda1 == null) {
            System.out.println("CPF inválido. Venda não iniciada.");
            return;
        }

        boolean adicionouMouse1 = vendaController.adicionarProdutoNaVenda(venda1, "519118", 3);

        boolean adicionouTeclado1 = vendaController.adicionarProdutoNaVenda(venda1, "154567", 1);

        if (adicionouMouse1 && adicionouTeclado1) {
            System.out.println("Produto adicionado com sucesso!");
        } else {
            System.out.println("Prouto não encontrado ou fora de estoque.");
        }

        System.out.println("Total da venda: R$ " + vendaController.calcularTotal(venda1));

        boolean concluiu1 = vendaController.concluirVenda(venda1);

        if (concluiu1) {
            System.out.println("Venda concluída com sucesso!");
        } else {
            System.out.println("Venda não pôde ser concluída.");
        }

        System.out.println("ID da venda: " + venda1.getIdVenda());
        System.out.println("CPF: " + venda1.getCpfCliente());
        System.out.println("Pagamento: " + venda1.getFormaPagamento());


        // TESTE C/ CPF INVÁLIDO

        Venda venda2 = vendaController.iniciarVenda("16794358107", "PIX");

        if (venda2 == null) {
            System.out.println("CPF inválido. Venda não iniciada.");
            return;
        }

        boolean adicionouMouse2 = vendaController.adicionarProdutoNaVenda(venda2, "519118", 1);

        boolean adicionouTeclado2 = vendaController.adicionarProdutoNaVenda(venda2, "154567", 2);

        if (adicionouMouse2 && adicionouTeclado2) {
            System.out.println("Produto adicionado com sucesso!");
        } else {
            System.out.println("Prouto não encontrado ou fora de estoque.");
        }

        System.out.println("Total da venda: R$ " + vendaController.calcularTotal(venda2));

        boolean concluiu2 = vendaController.concluirVenda(venda2);

        if (concluiu2) {
            System.out.println("Venda concluída com sucesso!");
        } else {
            System.out.println("Venda não pôde ser concluída.");
        }

        System.out.println("ID da venda: " + venda2.getIdVenda());
        System.out.println("CPF: " + venda2.getCpfCliente());
        System.out.println("Pagamento: " + venda2.getFormaPagamento());
    }
}
