package br.com.unit.frentedeloja.util;

import br.com.unit.frentedeloja.model.ItemVenda;
import br.com.unit.frentedeloja.model.Produto;
import br.com.unit.frentedeloja.model.Venda;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArquivoUtil {

    private static final String PASTA = "persist";
    private static final String ARQUIVO_PRODUTOS = PASTA + "/produtos.csv";
    private static final String ARQUIVO_VENDAS = PASTA + "/vendas.csv";
    private static final String ARQUIVO_ITENS_VENDA = PASTA + "/itens_venda.csv";

    public static void criarArquivos() {

        try {
            File pasta = new File("persist");

            if (!pasta.exists()) {
                pasta.mkdir();
            }

            File produtos = new File(ARQUIVO_PRODUTOS);
            File vendas = new File(ARQUIVO_VENDAS);
            File itensVendas = new File(ARQUIVO_ITENS_VENDA);

            if (!produtos.exists()) {
                produtos.createNewFile();
            }

            if (!vendas.exists()) {
                vendas.createNewFile();
            }

            if (!itensVendas.exists()) {
                itensVendas.createNewFile();
            }

        } catch (IOException e ) {
            System.out.println("Erro ao manipular os arquivos.");
        }
    }

    //--------------------- PRODUTOS ---------------------

    // METODO P/ SALVAR PRODUTOS

    public static void salvarProduto(Produto produto) {
        criarArquivos();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_PRODUTOS, true))) {

            bw.write(produto.getCodigoBarras() + ";" + produto.getNome() + ";" + produto.getPreco() + ";" + produto.getQtdEstoque() + ";" + produto.getCaminhoImagem());

            bw.newLine();

        } catch (IOException e) {
            System.out.println("Erro ao salvar o produto.");
        }
    }

    // METODO P/ LISTAR PRODUTOS

    public static List<Produto> listarProdutos() {
        criarArquivos();

        List<Produto> produtos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_PRODUTOS))) {

            String linha;

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");

                if (dados.length == 5) {
                    Produto produto = new Produto(
                            dados[0],
                            dados[1],
                            Double.parseDouble(dados[2]),
                            Integer.parseInt(dados[3]),
                            dados[4]
                    );

                    produtos.add(produto);
                }
            }

        } catch (IOException e) {
            System.out.println("Erro ao listar produtos.");
        }

        return produtos;
    }

    // METODO P/ BUSCAR PRODUTO POR CODIGO

    public static Produto buscarProdutoPorCodigo(String codigoBarras) {
        List<Produto> produtos = listarProdutos();

        for (Produto produto : produtos) {
            if (produto.getCodigoBarras().equals(codigoBarras)) {
                return produto;
            }
        }

        return null;
    }

    // METODO P/ SALVAR PRODUTOS

    public static void salvarTodosProdutos(List<Produto> produtos) {
        criarArquivos();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_PRODUTOS))) {

            for (Produto produto : produtos) {
                bw.write(produto.getCodigoBarras() + ";" + produto.getNome() + ";" + produto.getPreco() + ";" + produto.getQtdEstoque() + ";" + produto.getCaminhoImagem());

                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Erro ao atualizar produtos.");
        }
    }

    // --------------------- VENDAS ---------------------

    // METODO P/ GERAR ID

    public static int gerarProxIdVenda() {
        criarArquivos();

        int maiorId = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_VENDAS))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");

                if (dados.length >= 1) {
                    int id = Integer.parseInt(dados[0]);

                    if (id > maiorId) {
                        maiorId = id;
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Erro ao gerar ID da venda.");
        }

        return maiorId + 1;
    }

    // METODO P/ SALVAR VENDAS

    public static void salvarVenda(Venda venda) {
        criarArquivos();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_VENDAS, true))) {

            bw.write(venda.getIdVenda() + ";" + venda.getCpfCliente() + ";" + venda.getFormaPagamento() + ";" + venda.calcularTotal() + ";" + venda.getDataVenda());

            bw.newLine();

        } catch (IOException e) {
            System.out.println("Erro ap salvar venda.");
        }
    }

    // METODO P/ SALVAR ITEM DA VENDA

    public static void salvarItensVenda(Venda venda) {
        criarArquivos();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_ITENS_VENDA, true))) {

            for (ItemVenda item : venda.getItens()) {
                Produto produto = item.getProduto();

                bw.write(venda.getIdVenda() + ";" + produto.getCodigoBarras() + ";" + produto.getNome() + ";" + item.getQuantidade() + ";" + produto.getPreco());

                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Erro ao salvas itens da venda.");
        }
    }

    // METODO P/ CONCLUSAO DE VENDA

    public static void concluirVenda(Venda venda) {
        salvarVenda(venda);
        salvarItensVenda(venda);
        atualizarEstoqueAposVenda(venda);
    }

    // METODO P/ ATT ESTOQUE POS-VENDA

    public static void atualizarEstoqueAposVenda(Venda venda) {
        List<Produto> produtos = listarProdutos();

        for (ItemVenda item : venda.getItens()) {
            String codigoVendido = item.getProduto().getCodigoBarras();

            for (Produto produto : produtos) {
                if (produto.getCodigoBarras().equals(codigoVendido)) {
                    int novoEstoque = produto.getQtdEstoque() - item.getQuantidade();

                    if (novoEstoque < 0) {
                        novoEstoque = 0;
                    }

                    produto.setQtdEstoque(novoEstoque);
                }
            }
        }

        salvarTodosProdutos(produtos);
    }

    // METODO P/ LISTAR VENDAS

    public static List<Venda> listarVendas() {
        criarArquivos();

        List<Venda> vendas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_VENDAS))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");

                if (dados.length == 5) {
                    Venda venda = new Venda();

                    venda.setIdVenda(Integer.parseInt(dados[0]));
                    venda.setCpfCliente(dados[1]);
                    venda.setFormaPagamento(dados[2]);
                    venda.setDataVenda(LocalDateTime.parse(dados[4]));

                    vendas.add(venda);
                }
            }

        } catch (IOException e) {
            System.out.println("Erro ao listar Vendas.");
        }

        return vendas;
    }

    // METODO P/ BUSCAR ITEM DE UMA VENDA

    public static List<ItemVenda> listarItensVenda(int idVenda) {
        criarArquivos();

        List<ItemVenda> itens = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_ITENS_VENDA))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");

                if (dados.length == 5) {
                    int id = Integer.parseInt(dados[0]);

                    if (id == idVenda) {
                        String codigoBarras =  dados[1];
                        String nome =  dados[2];
                        int quantidade = Integer.parseInt(dados[3]);
                        double preco = Double.parseDouble(dados[4]);

                        Produto produto = new Produto(codigoBarras, nome, preco, 0, "");

                        ItemVenda item = new ItemVenda(produto, quantidade);
                        itens.add(item);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Erro ao listar itens da venda.");
        }

        return itens;
    }


    // --------------------- LIMPAR PERSIST ---------------------

    public static void resetarDados() {
        limparArquivo("persist/produtos.csv");
        limparArquivo("persist/vendas.csv");
        limparArquivo("persist/itens_venda.csv");
    }

    private static void limparArquivo(String caminho) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
