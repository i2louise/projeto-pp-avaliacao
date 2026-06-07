package br.com.unit.frentedeloja.controller;

import br.com.unit.frentedeloja.model.Produto;
import br.com.unit.frentedeloja.util.ArquivoUtil;

import java.util.List;

public class ProdutoController {

    public boolean cadastrarProduto(Produto produto) {

        if (produtoExiste(produto.getCodigoBarras())) {
            return false;
        }

        ArquivoUtil.salvarProduto(produto);
        return true;
    }

    public List<Produto> listarProdutos() {
        return ArquivoUtil.listarProdutos();
    }

    public Produto buscarPorCodigo(String codigoBarras) {
        return ArquivoUtil.buscarProdutoPorCodigo(codigoBarras);
    }

    public void salvarTodosProdutos(List<Produto> produtos){
        ArquivoUtil.salvarTodosProdutos(produtos);
    }

    public boolean alterarProduto(Produto produtoAlterado) {
        List<Produto> produtos = ArquivoUtil.listarProdutos();

        for (int i = 0; i < produtos.size(); i++) {

            if (produtos.get(i).getCodigoBarras().equals(produtoAlterado.getCodigoBarras())) {
                produtos.set(i, produtoAlterado);

                ArquivoUtil.salvarTodosProdutos(produtos);

                return true;
            }
        }

        return false;
    }

    public boolean removerProduto(String codigoBarras) {
        List<Produto> produtos = ArquivoUtil.listarProdutos();

        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getCodigoBarras().equals(codigoBarras)) {
                produtos.remove(i);
                ArquivoUtil.salvarTodosProdutos(produtos);
                return true;
            }
        }

        return false;
    }

    public boolean produtoExiste(String codigoBarras) {
        Produto produto = ArquivoUtil.buscarProdutoPorCodigo(codigoBarras);
        return produto != null;
    }
}
