package br.com.unit.frentedeloja;

import br.com.unit.frentedeloja.model.ItemVenda;
import br.com.unit.frentedeloja.model.Produto;
import br.com.unit.frentedeloja.model.Venda;


// MAIN PARA TESTE

public class Main {
    public static void main(String[] args) {

        Produto p1 = new Produto("519118", "Mouse", 120.00, 10, "imagens/mouse.png");
        Produto p2 = new Produto("154567", "Teclado RGB", 250.00, 25, "imagens/teclado.png");

        Venda venda = new Venda(1, "11111111111", "PIX");

        venda.adicionarItem(new ItemVenda(p1, 1));
        venda.adicionarItem(new ItemVenda(p2, 2));

        System.out.println("CPF: " + venda.getCpfCliente());
        System.out.println("Forma de pagamento: " + venda.getFormaPagamento());
        System.out.println("Total da venda: R$ " + venda.calcularTotal());

    }
}