package br.com.unit.frentedeloja;

import br.com.unit.frentedeloja.controller.ProdutoController;
import br.com.unit.frentedeloja.model.ItemVenda;
import br.com.unit.frentedeloja.model.Produto;
import br.com.unit.frentedeloja.model.Venda;
import br.com.unit.frentedeloja.util.ArquivoUtil;

import java.util.List;


/* MAIN PARA TESTE PT. 1

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
 */

/* MAIN PARA TESTE PT. 2

public class Main {
    public static void main(String[] args) {

        ProdutoController controller = new ProdutoController();

        Produto produto = new Produto(
                "519118", "Mouse", 120.00, 10, "resources/imagens/mouse.png"
        );

        controller.cadastrarProduto(produto);


        Produto produto2 = new Produto(
                "154567", "Teclado RGB", 250, 25, "imagens/teclado.png"
        );

        controller.cadastrarProduto(produto2);


        List<Produto> produtos = controller.listarProdutos();

        for (Produto p : produtos) {
            System.out.println(p.getNome());
        }

        ArquivoUtil.resetarDados();
    }

}
 */

// MAIN PARA TESTE PT. 3

public class Main {
    public static void main(String[] args) {
        Produto produto1 = ArquivoUtil.buscarProdutoPorCodigo("519118");
        Produto produto2 = ArquivoUtil.buscarProdutoPorCodigo("154567");

        Venda venda = new Venda();
        venda.setIdVenda(ArquivoUtil.gerarProxIdVenda());
        venda.setCpfCliente("11111111111");
        venda.setFormaPagamento("PIX");

        venda.adicionarItem(new ItemVenda(produto1, 1));
        venda.adicionarItem(new ItemVenda(produto2, 2));

        ArquivoUtil.concluirVenda(venda);

        System.out.println("Venda concluída!");
        System.out.println("Total: R$ " + venda.calcularTotal());
    }
}

