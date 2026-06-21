package br.com.unit.frentedeloja.tela.principal;

import br.com.unit.frentedeloja.controller.ProdutoController;
import br.com.unit.frentedeloja.model.Produto;
import br.com.unit.frentedeloja.tela.popup.PopupAlterarProduto;
import br.com.unit.frentedeloja.tela.popup.PopupCadastrarProduto;
import br.com.unit.frentedeloja.tela.popup.PopupRemoverProduto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PainelProdutos extends JPanel {

    private ProdutoController produtoController;

    private JTable tabelaProdutos;
    private DefaultTableModel modeloTabela;

    private JButton botaoAdicionar;
    private JButton botaoAlterar;
    private JButton botaoRemover;
    private JButton botaoAtualizar;

    private JLabel labelTotalProdutos;

    public PainelProdutos() {
        produtoController = new ProdutoController();

        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        montarTopo();
        montarTabela();
        montarRodape();

        carregarProdutos();
    }

    private void montarTopo() {
        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBackground(new Color(75, 0, 130));
        painelTopo.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titulo = new JLabel("PRODUTOS / ESTOQUE");
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setForeground(Color.WHITE);

        labelTotalProdutos = new JLabel("Produtos cadastrados: 0");
        labelTotalProdutos.setFont(new Font("Arial", Font.BOLD, 14));
        labelTotalProdutos.setForeground(Color.WHITE);
        labelTotalProdutos.setHorizontalAlignment(SwingConstants.RIGHT);

        painelTopo.add(titulo, BorderLayout.WEST);
        painelTopo.add(labelTotalProdutos, BorderLayout.EAST);

        add(painelTopo, BorderLayout.NORTH);
    }

    private void montarTabela() {
        JPanel painelCentro = new JPanel(new BorderLayout());
        painelCentro.setBackground(Color.WHITE);
        painelCentro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        modeloTabela = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modeloTabela.addColumn("Código de Barras");
        modeloTabela.addColumn("Nome");
        modeloTabela.addColumn("Preço");
        modeloTabela.addColumn("Estoque");
        modeloTabela.addColumn("Imagem");

        tabelaProdutos = new JTable(modeloTabela);
        tabelaProdutos.setRowHeight(25);
        tabelaProdutos.setFont(new Font("Arial", Font.PLAIN, 14));
        tabelaProdutos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabelaProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(tabelaProdutos);

        painelCentro.add(scrollPane, BorderLayout.CENTER);

        add(painelCentro, BorderLayout.CENTER);
    }

    private void montarRodape() {
        JPanel painelRodape = new JPanel(new BorderLayout());
        painelRodape.setBackground(new Color(240, 240, 240));
        painelRodape.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JPanel painelBotoes = new JPanel(new GridLayout(1, 4, 10, 0));
        painelBotoes.setBackground(new Color(240, 240, 240));

        botaoAdicionar = new JButton("Adicionar Produto");
        botaoAlterar = new JButton("Alterar Produto");
        botaoRemover = new JButton("Remover Produto");
        botaoAtualizar = new JButton("Atualizar Estoque");

        configurarBotao(botaoAdicionar);
        configurarBotao(botaoAlterar);
        configurarBotao(botaoRemover);
        configurarBotao(botaoAtualizar);

        painelBotoes.add(botaoAdicionar);
        painelBotoes.add(botaoAlterar);
        painelBotoes.add(botaoRemover);
        painelBotoes.add(botaoAtualizar);

        painelRodape.add(painelBotoes, BorderLayout.EAST);

        add(painelRodape, BorderLayout.SOUTH);

        botaoAdicionar.addActionListener(e -> abrirPopupCadastrar());
        botaoAlterar.addActionListener(e -> abrirPopupAlterar());
        botaoRemover.addActionListener(e -> abrirPopupRemover());
        botaoAtualizar.addActionListener(e -> carregarProdutos());
    }

    private void configurarBotao(JButton botao) {
        botao.setFocusPainted(false);
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        botao.setBackground(new Color(75, 0, 130));
        botao.setForeground(Color.WHITE);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void carregarProdutos() {
        modeloTabela.setRowCount(0);

        List<Produto> produtos = produtoController.listarProdutos();

        for (Produto produto : produtos) {
            modeloTabela.addRow(new Object[]{
                    produto.getCodigoBarras(),
                    produto.getNome(),
                    "R$ " + String.format("%.2f", produto.getPreco()),
                    produto.getQtdEstoque(),
                    produto.getCaminhoImagem()
            });
        }

        labelTotalProdutos.setText("Produtos cadastrados: " + produtos.size());
    }

    private void abrirPopupCadastrar() {
        Frame janelaPrincipal = (Frame) SwingUtilities.getWindowAncestor(this);

        new PopupCadastrarProduto(janelaPrincipal);

        carregarProdutos();
    }

    private void abrirPopupAlterar() {
        Frame janelaPrincipal = (Frame) SwingUtilities.getWindowAncestor(this);

        new PopupAlterarProduto(janelaPrincipal);

        carregarProdutos();
    }

    private void abrirPopupRemover() {
        Frame janelaPrincipal = (Frame) SwingUtilities.getWindowAncestor(this);

        new PopupRemoverProduto(janelaPrincipal);

        carregarProdutos();
    }
}