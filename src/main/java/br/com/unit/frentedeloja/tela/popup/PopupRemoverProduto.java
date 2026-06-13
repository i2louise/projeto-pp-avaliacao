package br.com.unit.frentedeloja.tela.popup;

import br.com.unit.frentedeloja.controller.ProdutoController;
import br.com.unit.frentedeloja.model.Produto;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PopupRemoverProduto extends JDialog {

    private JComboBox<Produto> comboProdutos;

    private JButton botaoRemover;
    private JButton botaoCancelar;

    private ProdutoController produtoController;

    private final Color COR_PRINCIPAL = new Color(75, 0, 130);

    public PopupRemoverProduto(Frame owner) {
        super(owner, "Remover Produto", true);

        produtoController = new ProdutoController();

        configurarJanela();
        montarTela();
        carregarProdutosNoCombo();

        setVisible(true);
    }

    private void configurarJanela() {
        setSize(420, 170);
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout());
        setResizable(false);
    }

    private void montarTela() {
        add(montarFormulario(), BorderLayout.CENTER);
        add(montarBotoes(), BorderLayout.SOUTH);
    }

    private JPanel montarFormulario() {
        JPanel painelFormulario = new JPanel(new GridLayout(2, 1, 5, 5));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 25, 10, 25));

        JLabel labelProduto = new JLabel("Selecione o produto:");
        comboProdutos = new JComboBox<>();

        painelFormulario.add(labelProduto);
        painelFormulario.add(comboProdutos);

        return painelFormulario;
    }

    private JPanel montarBotoes() {
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        botaoRemover = new JButton("Remover");
        botaoCancelar = new JButton("Cancelar");

        configurarBotao(botaoRemover);
        configurarBotao(botaoCancelar);

        painelBotoes.add(botaoRemover);
        painelBotoes.add(botaoCancelar);

        botaoRemover.addActionListener(e -> removerProduto());
        botaoCancelar.addActionListener(e -> dispose());

        return painelBotoes;
    }

    private void configurarBotao(JButton botao) {
        botao.setFocusPainted(false);
        botao.setFont(new Font("Arial", Font.BOLD, 13));
        botao.setBackground(COR_PRINCIPAL);
        botao.setForeground(Color.WHITE);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void carregarProdutosNoCombo() {
        List<Produto> produtos = produtoController.listarProdutos();

        comboProdutos.removeAllItems();

        for (Produto produto : produtos) {
            comboProdutos.addItem(produto);
        }

        if (produtos.isEmpty()) {
            botaoRemover.setEnabled(false);

            JOptionPane.showMessageDialog(
                    this,
                    "Não há produtos cadastrados para remover.",
                    "Atenção",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void removerProduto() {
        Produto produtoSelecionado = (Produto) comboProdutos.getSelectedItem();

        if (produtoSelecionado == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um produto.",
                    "Atenção",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int resposta = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente excluir este produto?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION
        );

        if (resposta == JOptionPane.YES_OPTION) {
            produtoController.removerProduto(produtoSelecionado.getCodigoBarras());
            dispose();
        }
    }
}