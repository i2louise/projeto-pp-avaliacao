package br.com.unit.frentedeloja.controller;

import br.com.unit.frentedeloja.model.Produto;
import br.com.unit.frentedeloja.util.ArquivoUtil;

import java.util.List;

public class ProdutoController {

    public void cadastrarProduto(Produto produto){
        ArquivoUtil.salvarProduto(produto);
    }

    public List<Produto> listarProdutos(){
        return ArquivoUtil.listarProdutos();
    }

    public Produto buscarPorCodigo(String codigoBarras) {
        return ArquivoUtil.buscarProdutoPorCodigo(codigoBarras);
    }

    public void atualizarProduto(List<Produto> produtos){
        ArquivoUtil.salvarTodosProdutos(produtos);
    }
}
